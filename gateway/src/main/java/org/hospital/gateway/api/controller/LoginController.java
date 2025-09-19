package org.hospital.gateway.api.controller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.hospital.gateway.api.dto.LoginResponseDTO;
import org.hospital.gateway.api.dto.UserResponseDTO;
import org.hospital.gateway.domain.service.LoginService;
import org.hospital.gateway.domain.service.UserServiceGrpc;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final UserServiceGrpc userServiceGrpc;

    @PermitAll
    @MutationMapping
    public LoginResponseDTO login(@Argument String username,
                                  @Argument String password) {
        return loginService.makeLogin(username, password);
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public UserResponseDTO getUserById(@Argument Long userId) {
        return userServiceGrpc.findUserById(userId);
    }

}
