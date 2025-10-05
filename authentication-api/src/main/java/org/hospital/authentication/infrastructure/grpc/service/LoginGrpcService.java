package org.hospital.authentication.infrastructure.grpc.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.hospital.authentication.api.service.LoginService;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.hospital.login.LoginResponse;
import org.hospital.login.LoginResquest;
import org.hospital.login.LoginServiceGrpc;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class LoginGrpcService extends LoginServiceGrpc.LoginServiceImplBase {

    private static final String ERROR_DURING_LOGIN = "Error during login";
    private final LoginService loginService;

    @Override
    public void makeLogin(LoginResquest request, StreamObserver<LoginResponse> responseObserver) {
        try {
            var response = loginService.makeLogin(request.getName(), request.getPassword());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (
                ApplicationException e) {
            log.error(ERROR_DURING_LOGIN, e);
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            log.error(ERROR_DURING_LOGIN, e);
            responseObserver.onError(Status.INTERNAL.withDescription(ERROR_DURING_LOGIN).asRuntimeException());
        }
    }
}
