package org.hospital.gateway.domain.service;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.hospital.core.domain.util.DateUtil;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.hospital.gateway.api.dto.user.UserCreatedResponseDTO;
import org.hospital.gateway.api.dto.user.UserResponseDTO;
import org.hospital.gateway.api.dto.user.UserCreateRequestDTO;
import org.hospital.user.UserByIdRequest;
import org.hospital.user.UserCreateRequest;
import org.hospital.user.UserCreatedResponse;
import org.hospital.user.UserProto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceGrpc {

    @GrpcClient("user-service")
    private org.hospital.user.UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public UserCreatedResponseDTO createUser(UserCreateRequestDTO userCreateRequestDTO) {
            UserCreateRequest request = UserCreateRequest.newBuilder()
                    .setName(userCreateRequestDTO.getName())
                    .setEmail(userCreateRequestDTO.getEmail())
                    .setPassword(userCreateRequestDTO.getPassword())
                    .setRole(userCreateRequestDTO.getRole())
                    .setLogin(userCreateRequestDTO.getLogin())
                    .setBirthDate(DateUtil.toTimestamp(userCreateRequestDTO.getBirthDate()))
                    .build();
            UserCreatedResponse userCreatedResponse = userServiceBlockingStub.createUser(request);
            return UserCreatedResponseDTO.builder().userId(userCreatedResponse.getId()).message("Sucesso").success(true).build();
    }
}
