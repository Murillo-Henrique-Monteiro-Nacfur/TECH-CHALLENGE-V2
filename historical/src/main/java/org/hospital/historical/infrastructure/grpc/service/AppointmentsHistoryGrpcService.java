package org.hospital.historical.infrastructure.grpc.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.hospital.appointmenthistory.AppointmentHistoryPagedRequest;
import org.hospital.appointmenthistory.AppointmentHistoryPagedResponse;
import org.hospital.appointmenthistory.AppointmentHistoryServiceGrpc;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.hospital.historical.domain.presenter.AppointmentHistoryPresenter;
import org.hospital.historical.domain.usecase.AppointmentHistoryUseCase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class AppointmentsHistoryGrpcService extends AppointmentHistoryServiceGrpc.AppointmentHistoryServiceImplBase {

    private static final String ERROR_SEARCHING_HISTORY_OF_APPOINTMENTS = "Error searching history of appointments";
    private final AppointmentHistoryUseCase appointmentHistoryUseCase;
    private final AppointmentHistoryPresenter appointmentHistoryPresenter;

    @Override
    public void getAppointmentsHistoryByFilter(AppointmentHistoryPagedRequest request, StreamObserver<AppointmentHistoryPagedResponse> responseObserver) {
        try {
            Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
            var appointmentsHistories = appointmentHistoryUseCase.execute(pageable);
            AppointmentHistoryPagedResponse.Builder responseBuilder = AppointmentHistoryPagedResponse.newBuilder()
                    .setPage(appointmentsHistories.getPageable().getPageNumber())
                    .setTotalPages(appointmentsHistories.getTotalPages())
                    .setTotalElements(appointmentsHistories.getTotalElements())
                    .addAllAppointmentHistory(appointmentsHistories.map(appointmentHistoryPresenter::toAppointmentHistoryProto).toList());
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (ApplicationException e) {
            log.error(ERROR_SEARCHING_HISTORY_OF_APPOINTMENTS, e);
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            log.error(ERROR_SEARCHING_HISTORY_OF_APPOINTMENTS, e);
            responseObserver.onError(Status.INTERNAL.withDescription(ERROR_SEARCHING_HISTORY_OF_APPOINTMENTS).asRuntimeException());
        }
    }
}