package org.hospital.gateway.api.controller;

import lombok.RequiredArgsConstructor;
import org.hospital.gateway.api.dto.user.UserCreateRequestDTO;
import org.hospital.gateway.api.dto.user.UserCreatedResponseDTO;
import org.hospital.gateway.domain.service.UserServiceGrpc;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceGrpc userServiceGrpc;

    @MutationMapping
    public UserCreatedResponseDTO createUser(@Argument("input") UserCreateRequestDTO userCreateRequestDTO) {
        return userServiceGrpc.createUser(userCreateRequestDTO);
    }
}
