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

//Generated by flatc compiler (version 1.10.0)
//If you make any local changes, they will be lost
//source: kv.fbs

package com.webank.eggroll.rollsite.grpc.core.io;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.BidiStreamingMethod;
import static io.grpc.stub.ServerCalls.ClientStreamingMethod;
import static io.grpc.stub.ServerCalls.ServerStreamingMethod;
import static io.grpc.stub.ServerCalls.UnaryMethod;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

import com.google.flatbuffers.grpc.FlatbuffersUtils;
import java.nio.ByteBuffer;

/**
 *
 */
@javax.annotation.Generated(
        value = "by gRPC proto compiler",
        comments = "Source: kv.fbs")
public final class FBSKVServiceGrpc {

    public static final String SERVICE_NAME = "com.webank.ai.fdn.eggroll.common.io.FBSKVService";
    private static final int METHODID_PUT = 0;
    private static final int METHODID_PUT_IF_ABSENT = 1;
    private static final int METHODID_DELETE = 2;
    private static final int METHODID_GET = 3;
    private static final int METHODID_ITERATE = 4;
    private static final int METHODID_DESTROY = 5;
    private static final int METHODID_PUT_ALL = 6;
    private static volatile io.grpc.MethodDescriptor<Operand,
            Empty> getPutMethod;
    private static volatile FlatbuffersUtils.FBExtactor<Operand> extractorOfOperand;
    private static volatile FlatbuffersUtils.FBExtactor<Empty> extractorOfEmpty;
    // Static method descriptors that strictly reflect the proto.
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getPutMethod()} instead.
    public static final io.grpc.MethodDescriptor<Operand,
            Empty> METHOD_PUT = getPutMethod();
    private static volatile io.grpc.MethodDescriptor<Operand,
            Operand> getPutIfAbsentMethod;
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getPutIfAbsentMethod()} instead.
    public static final io.grpc.MethodDescriptor<Operand,
            Operand> METHOD_PUT_IF_ABSENT = getPutIfAbsentMethod();
    private static volatile io.grpc.MethodDescriptor<Operand,
            Empty> getPutAllMethod;
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getPutAllMethod()} instead.
    public static final io.grpc.MethodDescriptor<Operand,
            Empty> METHOD_PUT_ALL = getPutAllMethod();
    private static volatile io.grpc.MethodDescriptor<Operand,
            Operand> getDeleteMethod;
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getDeleteMethod()} instead.
    public static final io.grpc.MethodDescriptor<Operand,
            Operand> METHOD_DELETE = getDeleteMethod();
    private static volatile io.grpc.MethodDescriptor<Operand,
            Operand> getGetMethod;
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getGetMethod()} instead.
    public static final io.grpc.MethodDescriptor<Operand,
            Operand> METHOD_GET = getGetMethod();
    private static volatile io.grpc.MethodDescriptor<Range,
            Operand> getIterateMethod;
    private static volatile FlatbuffersUtils.FBExtactor<Range> extractorOfRange;
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getIterateMethod()} instead.
    public static final io.grpc.MethodDescriptor<Range,
            Operand> METHOD_ITERATE = getIterateMethod();
    private static volatile io.grpc.MethodDescriptor<Empty,
            Empty> getDestroyMethod;
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    @Deprecated // Use {@link #getDestroyMethod()} instead.
    public static final io.grpc.MethodDescriptor<Empty,
            Empty> METHOD_DESTROY = getDestroyMethod();
    private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

    private FBSKVServiceGrpc() {
    }

    private static FlatbuffersUtils.FBExtactor<Operand> getExtractorOfOperand() {
        if (extractorOfOperand != null) return extractorOfOperand;
        synchronized (FBSKVServiceGrpc.class) {
            if (extractorOfOperand != null) return extractorOfOperand;
            extractorOfOperand = new FlatbuffersUtils.FBExtactor<Operand>() {
                public Operand extract(ByteBuffer buffer) {
                    return Operand.getRootAsOperand(buffer);
                }
            };
            return extractorOfOperand;
        }
    }

    private static FlatbuffersUtils.FBExtactor<Empty> getExtractorOfEmpty() {
        if (extractorOfEmpty != null) return extractorOfEmpty;
        synchronized (FBSKVServiceGrpc.class) {
            if (extractorOfEmpty != null) return extractorOfEmpty;
            extractorOfEmpty = new FlatbuffersUtils.FBExtactor<Empty>() {
                public Empty extract(ByteBuffer buffer) {
                    return Empty.getRootAsEmpty(buffer);
                }
            };
            return extractorOfEmpty;
        }
    }

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<Operand,
            Empty> getPutMethod() {
        io.grpc.MethodDescriptor<Operand, Empty> getPutMethod;
        if ((getPutMethod = FBSKVServiceGrpc.getPutMethod) == null) {
            synchronized (FBSKVServiceGrpc.class) {
                if ((getPutMethod = FBSKVServiceGrpc.getPutMethod) == null) {
                    FBSKVServiceGrpc.getPutMethod = getPutMethod =
                            io.grpc.MethodDescriptor.<Operand, Empty>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "com.webank.ai.fdn.eggroll.common.io.FBSKVService", "put"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(FlatbuffersUtils.marshaller(
                                            Operand.class, getExtractorOfOperand()))
                                    .setResponseMarshaller(FlatbuffersUtils.marshaller(
                                            Empty.class, getExtractorOfEmpty()))
                                    .setSchemaDescriptor(null)
                                    .build();
                }
            }
        }
        return getPutMethod;
    }

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<Operand,
            Operand> getPutIfAbsentMethod() {
        io.grpc.MethodDescriptor<Operand, Operand> getPutIfAbsentMethod;
        if ((getPutIfAbsentMethod = FBSKVServiceGrpc.getPutIfAbsentMethod) == null) {
            synchronized (FBSKVServiceGrpc.class) {
                if ((getPutIfAbsentMethod = FBSKVServiceGrpc.getPutIfAbsentMethod) == null) {
                    FBSKVServiceGrpc.getPutIfAbsentMethod = getPutIfAbsentMethod =
                            io.grpc.MethodDescriptor.<Operand, Operand>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "com.webank.ai.fdn.eggroll.common.io.FBSKVService", "putIfAbsent"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(FlatbuffersUtils.marshaller(
                                            Operand.class, getExtractorOfOperand()))
                                    .setResponseMarshaller(FlatbuffersUtils.marshaller(
                                            Operand.class, getExtractorOfOperand()))
                                    .setSchemaDescriptor(null)
                                    .build();
                }
            }
        }
        return getPutIfAbsentMethod;
    }

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<Operand,
            Empty> getPutAllMethod() {
        io.grpc.MethodDescriptor<Operand, Empty> getPutAllMethod;
        if ((getPutAllMethod = FBSKVServiceGrpc.getPutAllMethod) == null) {
            synchronized (FBSKVServiceGrpc.class) {
                if ((getPutAllMethod = FBSKVServiceGrpc.getPutAllMethod) == null) {
                    FBSKVServiceGrpc.getPutAllMethod = getPutAllMethod =
                            io.grpc.MethodDescriptor.<Operand, Empty>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
                                    .setFullMethodName(generateFullMethodName(
                                            "com.webank.ai.fdn.eggroll.common.io.FBSKVService", "putAll"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(FlatbuffersUtils.marshaller(
                                            Operand.class, getExtractorOfOperand()))
                                    .setResponseMarshaller(FlatbuffersUtils.marshaller(
                                            Empty.class, getExtractorOfEmpty()))
                                    .setSchemaDescriptor(null)
                                    .build();
                }
            }
        }
        return getPutAllMethod;
    }

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<Operand,
            Operand> getDeleteMethod() {
        io.grpc.MethodDescriptor<Operand, Operand> getDeleteMethod;
        if ((getDeleteMethod = FBSKVServiceGrpc.getDeleteMethod) == null) {
            synchronized (FBSKVServiceGrpc.class) {
                if ((getDeleteMethod = FBSKVServiceGrpc.getDeleteMethod) == null) {
                    FBSKVServiceGrpc.getDeleteMethod = getDeleteMethod =
                            io.grpc.MethodDescriptor.<Operand, Operand>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "com.webank.ai.fdn.eggroll.common.io.FBSKVService", "delete"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(FlatbuffersUtils.marshaller(
                                            Operand.class, getExtractorOfOperand()))
                                    .setResponseMarshaller(FlatbuffersUtils.marshaller(
                                            Operand.class, getExtractorOfOperand()))
                                    .setSchemaDescriptor(null)
                                    .build();
                }
            }
        }
        return getDeleteMethod;
    }

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<Operand,
            Operand> getGetMethod() {
        io.grpc.MethodDescriptor<Operand, Operand> getGetMethod;
        if ((getGetMethod = FBSKVServiceGrpc.getGetMethod) == null) {
            synchronized (FBSKVServiceGrpc.class) {
                if ((getGetMethod = FBSKVServiceGrpc.getGetMethod) == null) {
                    FBSKVServiceGrpc.getGetMethod = getGetMethod =
                            io.grpc.MethodDescriptor.<Operand, Operand>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "com.webank.ai.fdn.eggroll.common.io.FBSKVService", "get"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(FlatbuffersUtils.marshaller(
                                            Operand.class, getExtractorOfOperand()))
                                    .setResponseMarshaller(FlatbuffersUtils.marshaller(
                                            Operand.class, getExtractorOfOperand()))
                                    .setSchemaDescriptor(null)
                                    .build();
                }
            }
        }
        return getGetMethod;
    }

    private static FlatbuffersUtils.FBExtactor<Range> getExtractorOfRange() {
        if (extractorOfRange != null) return extractorOfRange;
        synchronized (FBSKVServiceGrpc.class) {
            if (extractorOfRange != null) return extractorOfRange;
            extractorOfRange = new FlatbuffersUtils.FBExtactor<Range>() {
                public Range extract(ByteBuffer buffer) {
                    return Range.getRootAsRange(buffer);
                }
            };
            return extractorOfRange;
        }
    }

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<Range,
            Operand> getIterateMethod() {
        io.grpc.MethodDescriptor<Range, Operand> getIterateMethod;
        if ((getIterateMethod = FBSKVServiceGrpc.getIterateMethod) == null) {
            synchronized (FBSKVServiceGrpc.class) {
                if ((getIterateMethod = FBSKVServiceGrpc.getIterateMethod) == null) {
                    FBSKVServiceGrpc.getIterateMethod = getIterateMethod =
                            io.grpc.MethodDescriptor.<Range, Operand>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
                                    .setFullMethodName(generateFullMethodName(
                                            "com.webank.ai.fdn.eggroll.common.io.FBSKVService", "iterate"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(FlatbuffersUtils.marshaller(
                                            Range.class, getExtractorOfRange()))
                                    .setResponseMarshaller(FlatbuffersUtils.marshaller(
                                            Operand.class, getExtractorOfOperand()))
                                    .setSchemaDescriptor(null)
                                    .build();
                }
            }
        }
        return getIterateMethod;
    }

    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
    public static io.grpc.MethodDescriptor<Empty,
            Empty> getDestroyMethod() {
        io.grpc.MethodDescriptor<Empty, Empty> getDestroyMethod;
        if ((getDestroyMethod = FBSKVServiceGrpc.getDestroyMethod) == null) {
            synchronized (FBSKVServiceGrpc.class) {
                if ((getDestroyMethod = FBSKVServiceGrpc.getDestroyMethod) == null) {
                    FBSKVServiceGrpc.getDestroyMethod = getDestroyMethod =
                            io.grpc.MethodDescriptor.<Empty, Empty>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(
                                            "com.webank.ai.fdn.eggroll.common.io.FBSKVService", "destroy"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(FlatbuffersUtils.marshaller(
                                            Empty.class, getExtractorOfEmpty()))
                                    .setResponseMarshaller(FlatbuffersUtils.marshaller(
                                            Empty.class, getExtractorOfEmpty()))
                                    .setSchemaDescriptor(null)
                                    .build();
                }
            }
        }
        return getDestroyMethod;
    }

    /**
     * Creates a new async stub that supports all call types for the communication
     */
    public static FBSKVServiceStub newStub(io.grpc.Channel channel) {
        return new FBSKVServiceStub(channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the communication
     */
    public static FBSKVServiceBlockingStub newBlockingStub(
            io.grpc.Channel channel) {
        return new FBSKVServiceBlockingStub(channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary calls on the communication
     */
    public static FBSKVServiceFutureStub newFutureStub(
            io.grpc.Channel channel) {
        return new FBSKVServiceFutureStub(channel);
    }

    public static io.grpc.ServiceDescriptor getServiceDescriptor() {
        io.grpc.ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            synchronized (FBSKVServiceGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                            .setSchemaDescriptor(null)
                            .addMethod(getPutMethod())
                            .addMethod(getPutIfAbsentMethod())
                            .addMethod(getPutAllMethod())
                            .addMethod(getDeleteMethod())
                            .addMethod(getGetMethod())
                            .addMethod(getIterateMethod())
                            .addMethod(getDestroyMethod())
                            .build();
                }
            }
        }
        return result;
    }

    /**
     *
     */
    public static abstract class FBSKVServiceImplBase implements io.grpc.BindableService {

        /**
         *
         */
        public void put(Operand request,
                        io.grpc.stub.StreamObserver<Empty> responseObserver) {
            asyncUnimplementedUnaryCall(getPutMethod(), responseObserver);
        }

        /**
         *
         */
        public void putIfAbsent(Operand request,
                                io.grpc.stub.StreamObserver<Operand> responseObserver) {
            asyncUnimplementedUnaryCall(getPutIfAbsentMethod(), responseObserver);
        }

        /**
         *
         */
        public io.grpc.stub.StreamObserver<Operand> putAll(
                io.grpc.stub.StreamObserver<Empty> responseObserver) {
            return asyncUnimplementedStreamingCall(getPutAllMethod(), responseObserver);
        }

        /**
         *
         */
        public void delete(Operand request,
                           io.grpc.stub.StreamObserver<Operand> responseObserver) {
            asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
        }

        /**
         *
         */
        public void get(Operand request,
                        io.grpc.stub.StreamObserver<Operand> responseObserver) {
            asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
        }

        /**
         *
         */
        public void iterate(Range request,
                            io.grpc.stub.StreamObserver<Operand> responseObserver) {
            asyncUnimplementedUnaryCall(getIterateMethod(), responseObserver);
        }

        /**
         *
         */
        public void destroy(Empty request,
                            io.grpc.stub.StreamObserver<Empty> responseObserver) {
            asyncUnimplementedUnaryCall(getDestroyMethod(), responseObserver);
        }

        @Override
        public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            getPutMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            Operand,
                                            Empty>(
                                            this, METHODID_PUT)))
                    .addMethod(
                            getPutIfAbsentMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            Operand,
                                            Operand>(
                                            this, METHODID_PUT_IF_ABSENT)))
                    .addMethod(
                            getPutAllMethod(),
                            asyncClientStreamingCall(
                                    new MethodHandlers<
                                            Operand,
                                            Empty>(
                                            this, METHODID_PUT_ALL)))
                    .addMethod(
                            getDeleteMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            Operand,
                                            Operand>(
                                            this, METHODID_DELETE)))
                    .addMethod(
                            getGetMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            Operand,
                                            Operand>(
                                            this, METHODID_GET)))
                    .addMethod(
                            getIterateMethod(),
                            asyncServerStreamingCall(
                                    new MethodHandlers<
                                            Range,
                                            Operand>(
                                            this, METHODID_ITERATE)))
                    .addMethod(
                            getDestroyMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            Empty,
                                            Empty>(
                                            this, METHODID_DESTROY)))
                    .build();
        }
    }

    /**
     *
     */
    public static final class FBSKVServiceStub extends io.grpc.stub.AbstractStub<FBSKVServiceStub> {
        private FBSKVServiceStub(io.grpc.Channel channel) {
            super(channel);
        }

        private FBSKVServiceStub(io.grpc.Channel channel,
                                 io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected FBSKVServiceStub build(io.grpc.Channel channel,
                                         io.grpc.CallOptions callOptions) {
            return new FBSKVServiceStub(channel, callOptions);
        }

        /**
         *
         */
        public void put(Operand request,
                        io.grpc.stub.StreamObserver<Empty> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getPutMethod(), getCallOptions()), request, responseObserver);
        }

        /**
         *
         */
        public void putIfAbsent(Operand request,
                                io.grpc.stub.StreamObserver<Operand> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getPutIfAbsentMethod(), getCallOptions()), request, responseObserver);
        }

        /**
         *
         */
        public io.grpc.stub.StreamObserver<Operand> putAll(
                io.grpc.stub.StreamObserver<Empty> responseObserver) {
            return asyncClientStreamingCall(
                    getChannel().newCall(getPutAllMethod(), getCallOptions()), responseObserver);
        }

        /**
         *
         */
        public void delete(Operand request,
                           io.grpc.stub.StreamObserver<Operand> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
        }

        /**
         *
         */
        public void get(Operand request,
                        io.grpc.stub.StreamObserver<Operand> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
        }

        /**
         *
         */
        public void iterate(Range request,
                            io.grpc.stub.StreamObserver<Operand> responseObserver) {
            asyncServerStreamingCall(
                    getChannel().newCall(getIterateMethod(), getCallOptions()), request, responseObserver);
        }

        /**
         *
         */
        public void destroy(Empty request,
                            io.grpc.stub.StreamObserver<Empty> responseObserver) {
            asyncUnaryCall(
                    getChannel().newCall(getDestroyMethod(), getCallOptions()), request, responseObserver);
        }
    }

    /**
     *
     */
    public static final class FBSKVServiceBlockingStub extends io.grpc.stub.AbstractStub<FBSKVServiceBlockingStub> {
        private FBSKVServiceBlockingStub(io.grpc.Channel channel) {
            super(channel);
        }

        private FBSKVServiceBlockingStub(io.grpc.Channel channel,
                                         io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected FBSKVServiceBlockingStub build(io.grpc.Channel channel,
                                                 io.grpc.CallOptions callOptions) {
            return new FBSKVServiceBlockingStub(channel, callOptions);
        }

        /**
         *
         */
        public Empty put(Operand request) {
            return blockingUnaryCall(
                    getChannel(), getPutMethod(), getCallOptions(), request);
        }

        /**
         *
         */
        public Operand putIfAbsent(Operand request) {
            return blockingUnaryCall(
                    getChannel(), getPutIfAbsentMethod(), getCallOptions(), request);
        }

        /**
         *
         */
        public Operand delete(Operand request) {
            return blockingUnaryCall(
                    getChannel(), getDeleteMethod(), getCallOptions(), request);
        }

        /**
         *
         */
        public Operand get(Operand request) {
            return blockingUnaryCall(
                    getChannel(), getGetMethod(), getCallOptions(), request);
        }

        /**
         *
         */
        public java.util.Iterator<Operand> iterate(
                Range request) {
            return blockingServerStreamingCall(
                    getChannel(), getIterateMethod(), getCallOptions(), request);
        }

        /**
         *
         */
        public Empty destroy(Empty request) {
            return blockingUnaryCall(
                    getChannel(), getDestroyMethod(), getCallOptions(), request);
        }
    }

    /**
     *
     */
    public static final class FBSKVServiceFutureStub extends io.grpc.stub.AbstractStub<FBSKVServiceFutureStub> {
        private FBSKVServiceFutureStub(io.grpc.Channel channel) {
            super(channel);
        }

        private FBSKVServiceFutureStub(io.grpc.Channel channel,
                                       io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected FBSKVServiceFutureStub build(io.grpc.Channel channel,
                                               io.grpc.CallOptions callOptions) {
            return new FBSKVServiceFutureStub(channel, callOptions);
        }

        /**
         *
         */
        public com.google.common.util.concurrent.ListenableFuture<Empty> put(
                Operand request) {
            return futureUnaryCall(
                    getChannel().newCall(getPutMethod(), getCallOptions()), request);
        }

        /**
         *
         */
        public com.google.common.util.concurrent.ListenableFuture<Operand> putIfAbsent(
                Operand request) {
            return futureUnaryCall(
                    getChannel().newCall(getPutIfAbsentMethod(), getCallOptions()), request);
        }

        /**
         *
         */
        public com.google.common.util.concurrent.ListenableFuture<Operand> delete(
                Operand request) {
            return futureUnaryCall(
                    getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
        }

        /**
         *
         */
        public com.google.common.util.concurrent.ListenableFuture<Operand> get(
                Operand request) {
            return futureUnaryCall(
                    getChannel().newCall(getGetMethod(), getCallOptions()), request);
        }

        /**
         *
         */
        public com.google.common.util.concurrent.ListenableFuture<Empty> destroy(
                Empty request) {
            return futureUnaryCall(
                    getChannel().newCall(getDestroyMethod(), getCallOptions()), request);
        }
    }

    private static final class MethodHandlers<Req, Resp> implements
            UnaryMethod<Req, Resp>,
            ServerStreamingMethod<Req, Resp>,
            ClientStreamingMethod<Req, Resp>,
            BidiStreamingMethod<Req, Resp> {
        private final FBSKVServiceImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(FBSKVServiceImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_PUT:
                    serviceImpl.put((Operand) request,
                            (io.grpc.stub.StreamObserver<Empty>) responseObserver);
                    break;
                case METHODID_PUT_IF_ABSENT:
                    serviceImpl.putIfAbsent((Operand) request,
                            (io.grpc.stub.StreamObserver<Operand>) responseObserver);
                    break;
                case METHODID_DELETE:
                    serviceImpl.delete((Operand) request,
                            (io.grpc.stub.StreamObserver<Operand>) responseObserver);
                    break;
                case METHODID_GET:
                    serviceImpl.get((Operand) request,
                            (io.grpc.stub.StreamObserver<Operand>) responseObserver);
                    break;
                case METHODID_ITERATE:
                    serviceImpl.iterate((Range) request,
                            (io.grpc.stub.StreamObserver<Operand>) responseObserver);
                    break;
                case METHODID_DESTROY:
                    serviceImpl.destroy((Empty) request,
                            (io.grpc.stub.StreamObserver<Empty>) responseObserver);
                    break;
                default:
                    throw new AssertionError();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(
                io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_PUT_ALL:
                    return (io.grpc.stub.StreamObserver<Req>) serviceImpl.putAll(
                            (io.grpc.stub.StreamObserver<Empty>) responseObserver);
                default:
                    throw new AssertionError();
            }
        }
    }
}