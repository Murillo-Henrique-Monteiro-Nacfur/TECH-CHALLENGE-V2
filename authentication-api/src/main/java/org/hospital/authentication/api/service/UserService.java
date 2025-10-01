package org.hospital.authentication.api.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.hospital.authentication.api.presenter.UserPresenters;
import org.hospital.authentication.api.service.handler.UserServiceHandler;
import org.hospital.authentication.api.usecase.user.UserCreateUsecase;
import org.hospital.user.*;

import java.util.NoSuchElementException;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private final UserServiceHandler userServiceHandler;
    private final UserCreateUsecase userCreateUsecase;
    private final UserPresenters userPresenters;

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

    @Override
    public void createUser(UserCreateRequest request, StreamObserver<UserCreatedResponse> responseObserver) {
        try {
            var user = userCreateUsecase.execute(userPresenters.toDomain(request));
            var response = UserCreatedResponse.newBuilder().setId(user.getId()).setMessage("Sucessfully created user").setSuccess(true).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (NoSuchElementException e) {
            log.warn("Can't create User ID: {}", request.getName());
            responseObserver.onError(Status.INTERNAL.withDescription("Internal server error").asRuntimeException());
        }
    }
}
