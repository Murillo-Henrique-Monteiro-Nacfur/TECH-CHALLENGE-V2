package org.hospital.authentication.infrastructure.grpc.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.hospital.authentication.api.presenter.UserPresenters;
import org.hospital.authentication.api.service.UserService;
import org.hospital.authentication.api.usecase.user.UserCreateUsecase;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.hospital.user.*;

import java.util.NoSuchElementException;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    private static final String ERROR_DURING_SEARCH_FOR_USER_ID = "Error during search for user id";
    private static final String ERROR_CREATING_USER = "Error creating User";
    private final UserService userService;
    private final UserCreateUsecase userCreateUsecase;
    private final UserPresenters userPresenters;

    @Override
    public void getUserById(UserByIdRequest request, StreamObserver<UserProto> responseObserver) {
        try {
            var user = userService.getUserById(request.getUserId());
            var response = UserProto.newBuilder()
                    .setId(user.getId())
                    .setName(user.getName())
                    .setEmail(user.getEmail())
                    .setPassword(user.getPassword())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (ApplicationException e) {
            log.error(ERROR_DURING_SEARCH_FOR_USER_ID, e);
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            log.error(ERROR_DURING_SEARCH_FOR_USER_ID, e);
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
        } catch (ApplicationException e) {
            log.error(ERROR_CREATING_USER, e);
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }catch (NoSuchElementException e) {
            log.warn(ERROR_CREATING_USER, e);
            responseObserver.onError(Status.INTERNAL.withDescription(ERROR_CREATING_USER).asRuntimeException());
        }
    }
}
