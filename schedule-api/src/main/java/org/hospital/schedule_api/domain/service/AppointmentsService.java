package org.hospital.schedule_api.domain.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.hospital.appointment.*;
import org.hospital.schedule_api.domain.dto.NotificationRequestDTO;
import org.hospital.schedule_api.domain.presenter.AppointmentPresenter;
import org.hospital.schedule_api.domain.usecase.AppointmentCreateUseCase;
import org.hospital.schedule_api.domain.usecase.AppointmentReadUseCase;
import org.hospital.schedule_api.domain.usecase.AppointmentUpdateUseCase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.format.DateTimeFormatter;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class AppointmentsService extends AppointmentServiceGrpc.AppointmentServiceImplBase {

    private static final String APPOINTMENT_CREATED_TITLE = "Agendamento de Consulta";
    private static final String APPOINTMENT_CREATED_MESSAGE = "Sua consulta foi agendada com sucesso para %s";
    private static final String APPOINTMENT_UPDATED_TITLE = "Alteração de Consulta";
    private static final String APPOINTMENT_UPDATED_MESSAGE = "Sua consulta sofreu uma alteração para %s";

    private final AppointmentCreateUseCase appointmentCreateUseCase;
    private final AppointmentReadUseCase appointmentReadUseCase;
    public final AppointmentPresenter appointmentPresenter;
    private final AppointmentUpdateUseCase appointmentUpdateUseCase;
    private final KafkaProducerNotificationService kafkaProducerNotificationService;
    private final org.hospital.core.domain.service.UserServiceCore userServiceCore;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void createAppointment(AppointmentCreateRequest request, StreamObserver<AppointmentCreateUpdateResponse> responseObserver) {
        try {
            var appointment = appointmentCreateUseCase.execute(appointmentPresenter.toDomain(request));
            var response = AppointmentCreateUpdateResponse.newBuilder()
                    .setId(appointment.getId())
                    .setSuccess(true)
                    .build();

            sendAppointmentNotification(
                    request.getPatientUserId(),
                    appointment.getDateHourScheduled(),
                    APPOINTMENT_CREATED_TITLE,
                    APPOINTMENT_CREATED_MESSAGE
            );

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

            sendAppointmentNotification(
                    appointment.getPatient().getId(),
                    appointment.getDateHourScheduled(),
                    APPOINTMENT_UPDATED_TITLE,
                    APPOINTMENT_UPDATED_MESSAGE
            );

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error creating appointment", e);
            responseObserver.onError(Status.INTERNAL.withDescription("Internal server error").asRuntimeException());
        }
    }

    private void sendAppointmentNotification(Long patientId, java.time.LocalDateTime appointmentDateTime, String title, String messageTemplate) {
        var patient = userServiceCore.getUserByIdOrFail(patientId);

        NotificationRequestDTO notificationRequest = new NotificationRequestDTO(
                patient.getEmail(),
                title,
                String.format(messageTemplate, appointmentDateTime.format(dateFormatter)),
                NotificationRequestDTO.NotificationType.EMAIL,
                "appointment-confirmation"
        );

        kafkaProducerNotificationService.sendNotification(notificationRequest);
    }
}