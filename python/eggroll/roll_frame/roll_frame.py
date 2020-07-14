# -*- coding: utf-8 -*-
#  Copyright (c) 2019 - now, Eggroll Authors. All Rights Reserved.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
import operator
import os
import uuid
from concurrent.futures import wait, FIRST_EXCEPTION
from queue import Queue
from threading import Thread
from typing import Iterable, Union

from eggroll.core.aspects import _method_profile_logger, _method_error_logger
from eggroll.core.client import CommandClient
from eggroll.core.command.command_model import CommandURI
from eggroll.core.conf_keys import SessionConfKeys, RollPairConfKeys
from eggroll.core.constants import StoreTypes, SerdesTypes, PartitionerTypes, \
    SessionStatus
from eggroll.core.datastructure.broker import FifoBroker
from eggroll.core.io.io_utils import get_db_path
from eggroll.core.meta_model import ErStoreLocator, ErJob, ErStore, ErFunctor, \
    ErTask, ErPair, ErPartition
from eggroll.core.serdes import cloudpickle
from eggroll.core.session import ErSession
from eggroll.core.utils import generate_job_id, generate_task_id
from eggroll.core.utils import string_to_bytes, hash_code
from eggroll.roll_pair import create_serdes
from eggroll.roll_pair.transfer_pair import TransferPair, BatchBroker
from eggroll.roll_pair.utils.gc_utils import GcRecorder
from eggroll.roll_pair.utils.pair_utils import partitioner
from eggroll.utils.log_utils import get_logger
import pandas as pd
import numpy as np
import pyarrow as pa
from eggroll.roll_frame import FrameBatch
from eggroll.roll_frame.frame_store import create_frame_adapter, create_adapter
from eggroll.roll_frame.transfer_frame import TransferFrame
from eggroll.core.transfer.transfer_service import TransferClient, TransferService
from eggroll.core.utils import generate_job_id, generate_task_id
from eggroll.core.serdes.eggroll_serdes import DefaultArrowSerdes


L = get_logger()


class RollFrameContext(object):

    def __init__(self, session: ErSession):
        if session.get_session_meta()._status != SessionStatus.ACTIVE:
            raise Exception(f"session:{session.get_session_id()} is not ACTIVE. current status={session.get_session_meta()._status}")
        self.__session = session
        self.session_id = session.get_session_id()
        self.default_store_type = StoreTypes.ROLLFRAME_FILE

        self.deploy_mode = session.get_option(SessionConfKeys.CONFKEY_SESSION_DEPLOY_MODE)
        self.__session_meta = session.get_session_meta()
        self.__command_client = CommandClient()

    def set_store_type(self, store_type: str):
        self.default_store_type = store_type

    def get_session(self):
        return self.__session

    def route_to_egg(self, partition: ErPartition):
        return self.__session.route_to_egg(partition)

    def populate_processor(self, store: ErStore):
        return self.__session.populate_processor(store)

    def load(self, namespace=None, name=None, options: dict = None):
        if options is None:
            options = {}
        store_type = options.get('store_type', self.default_store_type)
        total_partitions = options.get('total_partitions', None)
        no_partitions_param = False
        if total_partitions is None:
            no_partitions_param = True
            total_partitions = 1

        partitioner = options.get('partitioner', PartitionerTypes.BYTESTRING_HASH)
        create_if_missing = options.get('create_if_missing', True)
        # todo:1: add combine options to pass it through
        store_options = self.__session.get_all_options()
        store_options.update(options)
        final_options = store_options.copy()

        # TODO:0: add 'error_if_exist, persistent / default store type'
        L.info("final_options:{}".format(final_options))
        store = ErStore(
            store_locator=ErStoreLocator(
                store_type=store_type,
                namespace=namespace,
                name=name,
                total_partitions=total_partitions,
                partitioner=partitioner),
            options=final_options)

        if create_if_missing:
            result = self.__session._cluster_manager_client.get_or_create_store(store)
        else:
            result = self.__session._cluster_manager_client.get_store(store)
            if result is None:
                raise EnvironmentError(
                    "result is None, please check whether the store:{} has been created before".format(store))

        if False and not no_partitions_param \
                and result._store_locator._total_partitions != 0 \
                and total_partitions != result._store_locator._total_partitions:
            raise ValueError(f"store:{result._store_locator._name} input total_partitions:{total_partitions}, "
                             f"output total_partitions:{result._store_locator._total_partitions}, must be the same")

        return RollFrame(self.populate_processor(result), self)

    # TODO:1: separates load parameters and put all parameters
    def parallelize(self, data, options: dict = None):
        if options is None:
            options = {}
        namespace = options.get("namespace", self.session_id)
        name = options.get("name", f'rf_{uuid.uuid1()}')
        options['store_type'] = options.get("store_type", StoreTypes.ROLLFRAME_FILE)
        create_if_missing = options.get("create_if_missing", True)

        rf = self.load(namespace=namespace, name=name, options=options)
        return rf.put_all(data, options=options)

    '''store name only supports full name and reg: *, *abc ,abc* and a*c'''
    def cleanup(self, namespace, name, options: dict = None):
        if not namespace:
            raise ValueError('namespace cannot be blank')
        L.info(f'cleaning up namespace={namespace}, name={name}')
        if options is None:
            options = {}
        total_partitions = options.get('total_partitions', 1)
        partitioner = options.get('partitioner', PartitionerTypes.BYTESTRING_HASH)
        store_serdes = options.get('serdes', self.default_store_serdes)

        if name == '*':
            pass
        else:
            pass


# def _create_store_adapter(er_partition, options: dict = None):
#     if options is None:
#             options = {}
#     store_locator = er_partition._store_locator
#     options['store_type'] = er_partition._store_locator._store_type
#     options['path'] = "/".join([os.environ["EGGROLL_HOME"], store_locator._store_type,
#                                 store_locator._namespace, store_locator._name, str(er_partition._id)])
#     options['er_partition'] = er_partition
#     return FrameStore.init(options)


class RollFrame(object):
    EGG_FRAME_URI_PREFIX = 'v1/egg-frame'
    RUN_TASK = 'runTask'
    RUN_TASK_URI = CommandURI(f'{EGG_FRAME_URI_PREFIX}/{RUN_TASK}')

    GET_ALL = 'getAll'
    PUT_ALL = 'putAll'
    WITH_STORES = 'withStores'

    SERIALIZED_NONE = cloudpickle.dumps(None)

    def __setstate__(self, state):
        pass

    def __getstate__(self):
        pass

    def __init__(self, er_store: ErStore, rf_ctx: RollFrameContext):
        if not rf_ctx:
            raise ValueError('rp_ctx cannot be None')
        self.__store = er_store
        self.ctx = rf_ctx
        self.__command_client = CommandClient()
        self.functor_serdes = create_serdes(SerdesTypes.CLOUD_PICKLE)
        self.partitioner = partitioner(hash_code, self.__store._store_locator._total_partitions)
        self.__session_id = self.ctx.session_id
        self.destroyed = False

    def get_partitions(self):
        return self.__store._store_locator._total_partitions

    def get_name(self):
        return self.__store._store_locator._name

    def get_namespace(self):
        return self.__store._store_locator._namespace

    def get_store(self):
        return self.__store

    def get_store_type(self):
        return self.__store._store_locator._store_type

    def _submit_job(self,
                    job: ErJob,
                    command_uri: CommandURI = RUN_TASK_URI,
                    create_output_if_missing: bool = True):
        futures = self.ctx.get_session().submit_job(
                job=job,
                output_types=[ErPair],
                command_uri=command_uri,
                create_output_if_missing=create_output_if_missing)

        return futures

    def _wait_job_finished(self, futures: list):
        results = list()
        for future in futures:
            results.append(future.result())

        return results

    def _run_job(self,
                 job: ErJob,
                 command_uri: CommandURI = RUN_TASK_URI,
                 create_output_if_missing: bool = True):
        futures = self._submit_job(
            job=job,
            command_uri=command_uri,
            create_output_if_missing=create_output_if_missing)

        return self._wait_job_finished(futures)

    @_method_profile_logger
    @_method_error_logger
    def put_all(self, data: Union[pd.DataFrame, Iterable], options: dict = None):
        if options is None:
            options = {}
        def _func(task: ErTask):
            tag = task._id
            broker = TransferService.get_or_create_broker(tag, write_signals=1)
            with create_adapter(task._outputs[0]) as output_adapter:
                #pa_serdes_context = pa.default_serialization_context()
                for batch in broker:
                    b = DefaultArrowSerdes.deserialize(batch.data)
                    output_adapter.write_all([b])
                TransferService.remove_broker(tag)

        total_partitions = self.get_partitions()

        #serialization_context = pa.default_serialization_context()
        functor = ErFunctor(name=RollFrame.PUT_ALL, serdes=SerdesTypes.CLOUD_PICKLE, body=cloudpickle.dumps(_func))

        job = ErJob(id=generate_job_id(self.__session_id),
                    name=RollFrame.PUT_ALL,
                    inputs=[self.__store],
                    outputs=[self.__store],
                    functors=[functor])

        futures = self._submit_job(job=job)

        send_futures = []
        if isinstance(data, pd.DataFrame):
            transfer_client = TransferClient()
            rows = data.shape[0]
            rows_per_partition = rows // total_partitions + 1
            rows_extra = rows % rows_per_partition
            start = 0
            for i in range(total_partitions):
                if i >= rows:
                    break
                end = min(start + rows_per_partition + (1 if i < rows_extra else 0), rows)
                splitted = data[start:end]
                start = end
                target_egg = self.ctx.route_to_egg(self.get_store()._partitions[i])

                # memory copied
                # TODO:0: does RDMA need memory copy here?
                serialized_bytes = DefaultArrowSerdes.serialize(splitted)#.to_buffer().to_pybytes()
                broker = FifoBroker()
                broker.put(serialized_bytes)
                broker.signal_write_finish()
                send_future = transfer_client.send(broker, target_egg._transfer_endpoint, generate_task_id(job_id=job._id, partition_id=i))
                send_futures.append(send_future)
        elif isinstance(data, Iterable):
            brokers = [FifoBroker() for i in range(total_partitions)]

            for i in range(total_partitions):
                transfer_client = TransferClient()
                send_future = transfer_client.send(broker=brokers[i],
                                                   endpoint=self.get_store()._partitions[i]._processor._transfer_endpoint,
                                                   tag=generate_task_id(job_id=job._id, partition_id=i))
                send_futures.append(send_future)

            batch_count = 0
            for batch in data:
                serialized_bytes = DefaultArrowSerdes.serialize(batch._data)#.to_buffer().to_pybytes()
                brokers[batch_count % total_partitions].put(serialized_bytes)
                batch_count += 1

            for b in brokers:
                b.signal_write_finish()

            for f in send_futures:
                f.result()

        self._wait_job_finished(futures)

        return RollFrame(self.__store, self.ctx)

    @_method_profile_logger
    def get_all(self, options: dict = None):
        if options is None:
            options = {}
        def _func(task: ErTask):
            tag = task._id
            broker = TransferService.get_or_create_broker(tag)
            serialization_context = pa.default_serialization_context()
            with create_adapter(task._inputs[0]) as input_adapter:
                for batch in input_adapter.read_all():
                    broker.put(DefaultArrowSerdes.serialize(batch._data))#.to_buffer().to_pybytes())
                broker.signal_write_finish()

        functor = ErFunctor(name=RollFrame.GET_ALL, serdes=SerdesTypes.CLOUD_PICKLE, body=cloudpickle.dumps(_func))
        job = ErJob(id=generate_job_id(self.__session_id),
                    name=RollFrame.GET_ALL,
                    inputs=[self.__store],
                    outputs=[self.__store],
                    functors=[functor])

        results = self._submit_job(job)
        #serialization_context = pa.default_serialization_context()
        frames = list()
        transfer_client = TransferClient()
        for p in self.__store._partitions:
            tag = generate_task_id(job_id=job._id, partition_id=p._id)
            target_endpoint = p._processor._transfer_endpoint
            transfer_batch_iter = transfer_client.recv(endpoint=target_endpoint, tag=tag, broker=None)
            for b in transfer_batch_iter:
                fb = DefaultArrowSerdes.deserialize(b.data)
                frames.append(FrameBatch(fb))

        if len(frames) <= 0:
            return FrameBatch.empty()

        first = frames[0]
        if b"eggroll.rollframe.tensor.shape" in first._schema.metadata:
            result = frames
        else:
            result = FrameBatch.concat(frames)

        return result

    @_method_profile_logger
    def with_stores(self, func, merge_func=None, others=None, options: dict = None):
        if options is None:
            options = {}

        if others is None:
            others = []
        total_partitions = self.get_partitions()
        for other in others:
            if other.get_partitions() != total_partitions:
                raise ValueError(f"diff partitions: expected:{total_partitions}, actual:{other.get_partitions()}")
        job_id = generate_job_id(self.__session_id, tag=RollFrame.WITH_STORES)
        job = ErJob(id=job_id,
                    name=RollFrame.WITH_STORES,
                    inputs=[self.ctx.populate_processor(s.get_store()) for s in [self] + others],
                    functors=[ErFunctor(name=RollFrame.WITH_STORES, serdes=SerdesTypes.CLOUD_PICKLE, body=cloudpickle.dumps(func))],
                    options=options)

        futures = self._submit_job(job=job)
        result = None
        if merge_func is None:
            result = list()
            for future in futures:
                ret_pair = future.result()[0]
                result.append((self.functor_serdes.deserialize(ret_pair._key),
                               self.functor_serdes.deserialize(ret_pair._value)))
        else:
            results = Queue()
            for f in futures:
                f.add_done_callback(
                        lambda r: results.put(
                                self.functor_serdes.deserialize(
                                        r.result()[0]._value)))
            result = merge_func(results.get() for _ in range(len(futures)))
        return result


    # def shuffle(self):
    #     def _inner_shuffle(parts):
    #         in_part, out_part = parts[0], parts[1]
    #         with _create_store_adapter(in_part) as in_store, _create_store_adapter(out_part) as out_store:
    #             # TODO: merge batch
    #             input_store.write_all(TransferFrame.get(tag))
    #             for frame in in_store.read_all():
    #                 for batch in frame.split(by_column_value=True):
    #                     route_to_egg = xx
    #                     get_egg(route_to_egg).send(batch)
    #     return self.with_stores(_inner_shuffle)


# class RollTensor:
#     def __init__(self):
#         self.rf = None
#
#     def _bin_op(self, other, op):
#         def _inner_bin_func(parts):
#             in_part, out_part = parts[0], parts[1]
#             with _create_store_adapter(in_part) as in_store, _create_store_adapter(out_part) as out_store:
#                 x = x_store... numpy
#                 y = y_store.. numpy
#                 return op(x, y)
#         self.rf.with_store(other.rf.store, func=xx)
#
#     def _add(self, other):
#         return self._bin_op(other, operator.add)