package org.hospital.gateway.api.controller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.hospital.gateway.api.dto.LoginResponseDTO;
import org.hospital.gateway.domain.service.LoginServiceGrpc;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginServiceGrpc loginServiceGrpc;

    @PermitAll
    @MutationMapping
    public LoginResponseDTO login(@Argument String username,
                                  @Argument String password) {
        return loginServiceGrpc.makeLogin(username, password);
    }

}
