package org.hospital.schedule_api.domain.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.hospital.appointment.*;
import org.hospital.schedule_api.domain.presenter.AppointmentPresenter;
import org.hospital.schedule_api.domain.usecase.AppointmentCreateUseCase;
import org.hospital.schedule_api.domain.usecase.AppointmentReadUseCase;
import org.hospital.schedule_api.domain.usecase.AppointmentUpdateUseCase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class AppointmentsService extends AppointmentServiceGrpc.AppointmentServiceImplBase {

    private final AppointmentCreateUseCase appointmentCreateUseCase;
    private final AppointmentReadUseCase appointmentReadUseCase;
    public final AppointmentPresenter appointmentPresenter;
    private final AppointmentUpdateUseCase appointmentUpdateUseCase;

    @Override
    public void createAppointment(AppointmentCreateRequest request, StreamObserver<AppointmentCreateUpdateResponse> responseObserver) {
        try {
            var appointment = appointmentCreateUseCase.execute(appointmentPresenter.toDomain(request));
            var response = AppointmentCreateUpdateResponse.newBuilder()
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

    @Override
    public void getAppointmentsByFilter(AppointmentPagedRequestFiltered appointmentPagedRequestFiltered, StreamObserver<AppointmentPagedResponse> responseObserver) {
        try {
            Pageable pageable = PageRequest.of(appointmentPagedRequestFiltered.getPage(), appointmentPagedRequestFiltered.getSize());
            var appointment = appointmentReadUseCase.getAppointmentsByFilter(appointmentPresenter.toFilterDTO(appointmentPagedRequestFiltered.getAppointmentFilterRequest()), pageable);
            var response = AppointmentPagedResponse.newBuilder()
                    .setPage(appointment.getPageable().getPageNumber())
                    .setTotalPages(appointment.getTotalPages())
                    .setTotalElements(appointment.getTotalElements())
                    .addAllAppointments(appointment.get().map(appointmentPresenter::toAppointmentProto).toList())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error creating appointment", e);
            responseObserver.onError(Status.INTERNAL.withDescription("Internal server error").asRuntimeException());
        }
    }

    @Override
    public void updateAppointment(AppointmentUpdateRequest request, StreamObserver<AppointmentCreateUpdateResponse> responseObserver) {
        try {
            var appointment = appointmentUpdateUseCase.execute(appointmentPresenter.toUpdateDomain(request));
            var response = AppointmentCreateUpdateResponse.newBuilder()
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