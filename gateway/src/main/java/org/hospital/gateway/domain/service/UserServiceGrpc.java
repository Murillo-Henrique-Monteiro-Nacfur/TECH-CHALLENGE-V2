package org.hospital.gateway.domain.service;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.hospital.gateway.api.dto.UserResponseDTO;
import org.hospital.user.UserByIdRequest;
import org.hospital.user.UserProto;
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
            throw new RuntimeException("Error fetching user data", e);
        }
    }
}
