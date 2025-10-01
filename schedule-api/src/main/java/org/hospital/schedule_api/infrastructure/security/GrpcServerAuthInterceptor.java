package org.hospital.schedule_api.infrastructure.security;

import io.grpc.*;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.hospital.core.domain.dto.UserAuthenticatedDTO;
import org.hospital.core.domain.service.JwtDecoreServiceCore;
import org.hospital.core.infrastructure.ThreadLocalStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@GrpcGlobalServerInterceptor
public class GrpcServerAuthInterceptor implements ServerInterceptor {

    public static final String MAKE_LOGIN_METHOD = "MakeLogin";
    private final JwtDecoreServiceCore jwtDecoreServiceCore;

    private static final Logger log = LoggerFactory.getLogger(GrpcServerAuthInterceptor.class);

    public static final Metadata.Key<String> AUTH_HEADER_KEY =
            Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {

        if(MAKE_LOGIN_METHOD.equals(call.getMethodDescriptor().getBareMethodName())){
            return next.startCall(call, headers);
        }

        String authHeader = headers.get(AUTH_HEADER_KEY);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Authorization token is missing or does not start with Bearer.");
            call.close(Status.UNAUTHENTICATED.withDescription("Authorization token is missing or malformed"), new Metadata());
            return new ServerCall.Listener<>() {
            };
        }

        String token = authHeader.substring(7);

        try {
            UserAuthenticatedDTO userDetails = jwtDecoreServiceCore.validateToken(token);

            Context context = Context.current().withValue(ThreadLocalStorage.USER_AUTHENTICATION_DTO_CONTEXT_KEY, userDetails);

            return Contexts.interceptCall(context, call, headers, next);

        } catch (SecurityException e) {
            log.error("Authentication failed: {}", e.getMessage());
            call.close(Status.UNAUTHENTICATED.withDescription(e.getMessage()), new Metadata());
            return new ServerCall.Listener<>() {
            };
        }
    }


}