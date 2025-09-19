package org.hospital.authentication.api.service;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.hospital.authentication.api.service.handler.LoginServiceHandler;
import org.hospital.login.LoginResponse;
import org.hospital.login.LoginResquest;
import org.hospital.login.LoginServiceGrpc;
import org.springframework.stereotype.Service;

@GrpcService
@RequiredArgsConstructor
public class LoginService extends LoginServiceGrpc.LoginServiceImplBase {

    private final LoginServiceHandler loginServiceHandler;

    @Override
    public void makeLogin(LoginResquest request, StreamObserver<LoginResponse> responseObserver){
        var response = loginServiceHandler.makeLogin(request.getName(), request.getPassword());
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
