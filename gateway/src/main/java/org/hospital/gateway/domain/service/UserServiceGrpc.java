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

    public UserResponseDTO findUserById(Long userId) {
        try {
            UserByIdRequest request = UserByIdRequest.newBuilder()
                    .setUserId(userId)
                    .build();
            UserProto userProto = userServiceBlockingStub.getUserById(request);
            return UserResponseDTO.builder().id(userProto.getId()).email(userProto.getEmail()).name(userProto.getName()).build();
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                log.warn("User not found via gRPC for ID: {}", userId);
                return null;
            }
            log.error("gRPC error while finding user by ID: {}", userId, e);
            throw new ApplicationException("Error fetching user data", HttpStatus.BAD_REQUEST);
        }
    }

    public UserCreatedResponseDTO createUser(UserCreateRequestDTO userCreateRequestDTO) {
        try {
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

        } catch (StatusRuntimeException e) {
            log.error("Error Creating user: {}", userCreateRequestDTO.getName(), e);
            throw new ApplicationException("Error Creating user", HttpStatus.BAD_REQUEST);
        }
    }
}
