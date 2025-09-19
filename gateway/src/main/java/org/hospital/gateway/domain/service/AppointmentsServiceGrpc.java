package org.hospital.gateway.domain.service;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.hospital.appointment.AppointmentServiceGrpc;
import org.hospital.appointment.AppointmentCreateRequest;
import org.hospital.appointment.AppointmentCreateResponse;
import org.hospital.appointment.StatusAppointment;
import org.hospital.gateway.api.dto.AppointmentCreateRequestDTO;
import org.hospital.gateway.api.dto.AppointmentCreateResponseDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Slf4j
public class AppointmentsServiceGrpc {

    @GrpcClient("schedule-service")
    private AppointmentServiceGrpc.AppointmentServiceBlockingStub appointmentServiceBlockingStub;

    public AppointmentCreateResponseDTO createAppointments(AppointmentCreateRequestDTO appointmentCreateRequestDTO) {
        try {
            AppointmentCreateRequest request = AppointmentCreateRequest
                    .newBuilder()
                    .setDoctorUserId(appointmentCreateRequestDTO.getDoctorUserId())
                    .setPatientUserId(appointmentCreateRequestDTO.getPatientUserId())
                    .setDateHourScheduled(convertLocalDateTimeToGoogleTimestamp(appointmentCreateRequestDTO.getDateHourScheduled()))
                    .setDateHourStart(convertLocalDateTimeToGoogleTimestamp(appointmentCreateRequestDTO.getDateHourStart()))
                    .setDateHourEnd(convertLocalDateTimeToGoogleTimestamp(appointmentCreateRequestDTO.getDateHourEnd()))
                    .setStatusAppointment(StatusAppointment.SCHEDULED)
                    .setInformation(appointmentCreateRequestDTO.getInformation())
                    .build();
            AppointmentCreateResponse appointmentCreateResponse = appointmentServiceBlockingStub.createAppointment(request);
            return AppointmentCreateResponseDTO.builder().appointmentId(appointmentCreateResponse.getId()).message("Sucesso").success(true).build();
        } catch (StatusRuntimeException e) {
            log.error("Error calling gRPC service: {}", e.getStatus());
            throw new RuntimeException("Failed to create appointment: " + e.getStatus().getDescription(), e);
        }
    }

    protected com.google.protobuf.Timestamp convertLocalDateTimeToGoogleTimestamp(LocalDateTime localDateTime) {
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

        return com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}

