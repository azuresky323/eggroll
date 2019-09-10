/*
 * Copyright 2019 The Eggroll Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webank.eggroll.rollsite.grpc.observer;

import com.webank.ai.eggroll.api.networking.proxy.Proxy;
import com.webank.ai.eggroll.api.networking.proxy.Proxy.Metadata;
import com.webank.eggroll.rollsite.grpc.core.api.grpc.observer.BaseCallerResponseStreamObserver;
import com.webank.eggroll.rollsite.grpc.core.utils.ToStringUtils;
import com.webank.eggroll.rollsite.infra.Pipe;
import com.webank.eggroll.rollsite.infra.impl.PacketQueueSingleResultPipe;
import java.util.concurrent.CountDownLatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PushClientResponseStreamObserver extends BaseCallerResponseStreamObserver<Proxy.Packet, Metadata> {
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ToStringUtils toStringUtils;
    private Pipe transferBroker;
    // private DelayedResult<Metadata> delayedResult;
    private Proxy.Metadata metadata;

    public PushClientResponseStreamObserver(CountDownLatch finishLatch, Pipe pipe) {
        super(finishLatch);
        this.transferBroker = pipe;
    }

    @Override
    public void onNext(Proxy.Metadata metadata) {
        //LOGGER.info("[PUSH][CLIENTOBSERVER][ONNEXT]SendClientResponseStreamObserver.onNext. metadata: {}", toStringUtils.toOneLineString(metadata));
        LOGGER.info("[PUSH][CLIENTOBSERVER][ONNEXT]PushClientResponseStreamObserver.onNext");
        //this.metadata = metadata;
        // this.delayedResult.setResult(metadata);
        this.metadata = metadata;
        //resultCallback.setResult(metadata);
        PacketQueueSingleResultPipe convertedPipe = (PacketQueueSingleResultPipe) transferBroker;
        convertedPipe.setResult(metadata);

        LOGGER.info("[PUSH][CLIENTOBSERVER][ONNEXT] PushClientResponseStreamObserver.onNext(), metadata: {}",
            toStringUtils.toOneLineString(metadata));

    }

    @Override
    public void onError(Throwable throwable) {
        //LOGGER.info("[PUSH][CLIENTOBSERVER][ONERROR]SendClientResponseStreamObserver.onError. metadata: {}", toStringUtils.toOneLineString(metadata));
        LOGGER.info("[PUSH][CLIENTOBSERVER][ONERROR]SendClientResponseStreamObserver.onError.");
        super.onError(throwable);
    }

    @Override
    public void onCompleted() {
        LOGGER.info("[PUSH][CLIENTOBSERVER][ONCOMPLETE]SendClientResponseStreamObserver.onComplete. metadata: {}", toStringUtils.toOneLineString(metadata));
        super.onCompleted();
        transferBroker.onComplete();
    }
}
