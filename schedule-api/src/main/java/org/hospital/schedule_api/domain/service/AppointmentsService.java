package org.hospital.schedule_api.domain.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.hospital.appointment.AppointmentCreateRequest;
import org.hospital.appointment.AppointmentCreateResponse;
import org.hospital.appointment.AppointmentServiceGrpc;
import org.hospital.schedule_api.domain.mapper.AppointmentMapper;
import org.hospital.schedule_api.domain.usecase.AppointmentCreateUseCase;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class AppointmentsService extends AppointmentServiceGrpc.AppointmentServiceImplBase {

    private final AppointmentCreateUseCase appointmentCreateUseCase;
    public final AppointmentMapper appointmentMapper;

    @Override
    public void createAppointment(AppointmentCreateRequest request, StreamObserver<AppointmentCreateResponse> responseObserver) {
        try {
            var appointment = appointmentCreateUseCase.createAppointment(appointmentMapper.toDomain(request));
            var response = AppointmentCreateResponse.newBuilder()
                    .setId(appointment.getId())
                    .setSuccess(true)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error creating appointment", e);
            responseObserver.onError(Status.INTERNAL.withDescription("Internal server error").asRuntimeException());
        }
    }
}