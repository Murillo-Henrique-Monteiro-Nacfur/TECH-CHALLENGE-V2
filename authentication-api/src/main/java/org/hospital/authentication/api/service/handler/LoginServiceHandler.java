package org.hospital.authentication.api.service.handler;

import lombok.RequiredArgsConstructor;
import org.hospital.authentication.api.service.AuthenticationService;
import org.hospital.login.LoginResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceHandler {

    private final AuthenticationService authenticationService;

    public LoginResponse makeLogin(String name, String password) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(name, password);
        var token = authenticationService.authenticate(authenticationToken);
        return LoginResponse.newBuilder().setToken(token).build();
    }

}
