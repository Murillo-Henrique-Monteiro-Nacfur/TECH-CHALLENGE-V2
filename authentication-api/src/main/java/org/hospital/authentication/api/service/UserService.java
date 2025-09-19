package org.hospital.authentication.api.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.hospital.authentication.api.service.handler.UserServiceHandler;
import org.hospital.user.UserByIdRequest;
import org.hospital.user.UserProto;
import org.hospital.user.UserServiceGrpc;

import java.util.NoSuchElementException;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private final UserServiceHandler userServiceHandler;

    @Override
    public void getUserById(UserByIdRequest request, StreamObserver<UserProto> responseObserver) {
        try {
            var user = userServiceHandler.getUserById(request.getUserId());
            var response = UserProto.newBuilder()
                    .setId(user.getId())
                    .setName(user.getName())
                    .setEmail(user.getEmail())
                    .setPassword(user.getPassword())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (NoSuchElementException e) {
            log.warn("User not found for ID: {}", request.getUserId());
            responseObserver.onError(Status.NOT_FOUND.withDescription("User not found with ID: " + request.getUserId()).asRuntimeException());
        } catch (Exception e) {
            log.error("Error fetching user by ID: {}", request.getUserId(), e);
            responseObserver.onError(Status.INTERNAL.withDescription("Internal server error").asRuntimeException());
        }
    }

}
