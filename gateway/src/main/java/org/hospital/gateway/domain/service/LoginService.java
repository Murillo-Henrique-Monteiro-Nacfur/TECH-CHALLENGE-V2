package org.hospital.gateway.domain.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.hospital.gateway.api.dto.LoginResponseDTO;
import org.hospital.login.LoginResponse;
import org.hospital.login.LoginResquest;
import org.hospital.login.LoginServiceGrpc;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @GrpcClient("login-service")
    private LoginServiceGrpc.LoginServiceBlockingStub loginServiceBlockingStub;

    public LoginResponseDTO makeLogin(String login, String password) {
        var loginResquest = LoginResquest.newBuilder()
                .setName(login)
                .setPassword(password)
                .build();
        LoginResponse loginResponse = loginServiceBlockingStub.makeLogin(loginResquest);

        return LoginResponseDTO.builder().token(loginResponse.getToken()).build();
    }

}
