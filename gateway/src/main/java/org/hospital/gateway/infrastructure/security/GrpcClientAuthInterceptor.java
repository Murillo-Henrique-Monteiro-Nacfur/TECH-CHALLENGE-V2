package org.hospital.gateway.infrastructure.security;

import io.grpc.*;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.hospital.core.infrastructure.ThreadLocalStorage;
import org.springframework.stereotype.Component;

@GrpcGlobalClientInterceptor
@Component
public class GrpcClientAuthInterceptor implements ClientInterceptor {

    public static final Metadata.Key<String> AUTH_HEADER_KEY =
            Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {

        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                String token = ThreadLocalStorage.getToken();
                if (token != null) {
                    // Adiciona o cabeçalho "Authorization: Bearer <token>"
                    headers.put(AUTH_HEADER_KEY, "Bearer " + token);
                }
                super.start(responseListener, headers);
            }
        };
    }
}