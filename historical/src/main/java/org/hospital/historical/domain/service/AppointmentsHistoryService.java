package org.hospital.historical.domain.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.hospital.appointmenthistory.AppointmentHistoryPagedRequest;
import org.hospital.appointmenthistory.AppointmentHistoryPagedResponse;
import org.hospital.appointmenthistory.AppointmentHistoryServiceGrpc;
import org.hospital.historical.domain.presenter.AppointmentHistoryPresenter;
import org.hospital.historical.domain.usecase.AppointmentHistoryUseCase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class AppointmentsHistoryService extends AppointmentHistoryServiceGrpc.AppointmentHistoryServiceImplBase {

    private final AppointmentHistoryUseCase appointmentHistoryUseCase;
    public final AppointmentHistoryPresenter appointmentHistoryPresenter;

    @Override
    public void getAppointmentsHistoryByFilter(AppointmentHistoryPagedRequest request, StreamObserver<AppointmentHistoryPagedResponse> responseObserver) {
        try {
            Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
            var appointmentsHistories = appointmentHistoryUseCase.execute(pageable);
            AppointmentHistoryPagedResponse.Builder responseBuilder = AppointmentHistoryPagedResponse.newBuilder()
                    .setPage(appointmentsHistories.getPageable().getPageNumber())
                    .setTotalPages(appointmentsHistories.getTotalPages())
                    .setTotalElements(appointmentsHistories.getTotalElements())
                    .addAllAppointments(appointmentsHistories.map(appointmentHistoryPresenter::toAppointmentHistoryProto).toList());
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error creating appointment", e);
            responseObserver.onError(Status.INTERNAL.withDescription("Internal server error").asRuntimeException());
        }
    }
}