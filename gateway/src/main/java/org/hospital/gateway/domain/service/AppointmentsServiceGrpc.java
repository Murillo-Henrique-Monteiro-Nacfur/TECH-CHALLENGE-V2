package org.hospital.gateway.domain.service;

import com.google.protobuf.UInt64Value;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.hospital.appointment.*;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.hospital.gateway.api.dto.appointment.*;
import org.hospital.gateway.api.dto.common.PageableDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Service
@Slf4j
public class AppointmentsServiceGrpc {

    @GrpcClient("schedule-service")
    private AppointmentServiceGrpc.AppointmentServiceBlockingStub appointmentServiceBlockingStub;

    public AppointmentCreateUpdateResponseDTO createAppointments(AppointmentCreateRequestDTO appointmentCreateRequestDTO) {

        AppointmentCreateRequest.Builder request = AppointmentCreateRequest
                .newBuilder()
                .setDoctorUserId(appointmentCreateRequestDTO.getDoctorUserId())
                .setPatientUserId(appointmentCreateRequestDTO.getPatientUserId())
                .setDateHourScheduled(convertLocalDateTimeToGoogleTimestamp(appointmentCreateRequestDTO.getDateHourScheduled()))
                .setStatusAppointment(StatusAppointment.SCHEDULED)
                .setInformation(appointmentCreateRequestDTO.getInformation());
        ofNullable(appointmentCreateRequestDTO.getDateHourStart()).map(this::convertLocalDateTimeToGoogleTimestamp).ifPresent(request::setDateHourStart);
        ofNullable(appointmentCreateRequestDTO.getDateHourEnd()).map(this::convertLocalDateTimeToGoogleTimestamp).ifPresent(request::setDateHourEnd);

        AppointmentCreateUpdateResponse appointmentCreateResponse;
        try {
            appointmentCreateResponse = appointmentServiceBlockingStub.createAppointment(request.build());
        } catch (Exception e) {
            log.error("Error calling gRPC service: {}", e.getMessage());
            throw new ApplicationException("Failed to create appointment");
        }
        return AppointmentCreateUpdateResponseDTO.builder().appointmentId(appointmentCreateResponse.getId()).message("Sucesso").success(true).build();
    }

    protected com.google.protobuf.Timestamp convertLocalDateTimeToGoogleTimestamp(LocalDateTime localDateTime) {
        if (isNull(localDateTime)) {
            return null;
        }
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

        return com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    public AppointmentPagedResponseDTO findAllAppointments(AppointmentFindAllFilterRequestDTO appointmentFindAllFilterRequestDTO, PageableDTO pageable) {

        AppointmentFilterRequest.Builder builder = AppointmentFilterRequest.newBuilder();
        if (!isNull(appointmentFindAllFilterRequestDTO)) {
            ofNullable(appointmentFindAllFilterRequestDTO.getDoctorId()).map(UInt64Value::of).ifPresent(builder::setDoctorUserId);
            ofNullable(appointmentFindAllFilterRequestDTO.getPatientId()).map(UInt64Value::of).ifPresent(builder::setPatientUserId);

            List<StatusAppointment> statuses = appointmentFindAllFilterRequestDTO.getStatusAppointments();
            if (statuses != null && !statuses.isEmpty()) {
                builder.addAllStatusAppointment(statuses);
            }
            builder.setUpcoming(appointmentFindAllFilterRequestDTO.isUpcoming());
        }
        AppointmentPagedRequestFiltered request = AppointmentPagedRequestFiltered
                .newBuilder()
                .setPage(ofNullable(pageable).map(PageableDTO::getPage).orElse(0))
                .setSize(ofNullable(pageable).map(PageableDTO::getSize).orElse(10))
                .setAppointmentFilterRequest(builder.build())
                .build();

        AppointmentPagedResponse appointmentCreateResponse;
        try {
            appointmentCreateResponse = appointmentServiceBlockingStub.getAppointmentsByFilter(request);
        } catch (Exception e) {
            log.error("Error calling gRPC service: {}", e.getMessage());
            throw new ApplicationException("Failed to findAllAppointments");
        }

        return AppointmentPagedResponseDTO.builder()
                .page(appointmentCreateResponse.getPage())
                .totalPages(appointmentCreateResponse.getTotalPages())
                .totalElements(appointmentCreateResponse.getTotalElements())
                .size(appointmentCreateResponse.getAppointmentsList().size())
                .appointments(appointmentCreateResponse.getAppointmentsList().stream().map(appointment ->
                        AppointmentResponseDTO.builder()
                                .id(appointment.getId())
                                .doctorUserId(appointment.getDoctorUserId())
                                .patientUserId(appointment.getPatientUserId())
                                .dateHourScheduled(LocalDateTime.ofInstant(Instant.ofEpochSecond(appointment.getDateHourScheduled().getSeconds(), appointment.getDateHourScheduled().getNanos()), ZoneOffset.UTC))
                                .dateHourStart(appointment.hasDateHourStart() ? LocalDateTime.ofInstant(Instant.ofEpochSecond(appointment.getDateHourStart().getSeconds(), appointment.getDateHourStart().getNanos()), ZoneOffset.UTC) : null)
                                .dateHourEnd(appointment.hasDateHourEnd() ? LocalDateTime.ofInstant(Instant.ofEpochSecond(appointment.getDateHourEnd().getSeconds(), appointment.getDateHourEnd().getNanos()), ZoneOffset.UTC) : null)
                                .status(appointment.getStatusAppointment())
                                .information(appointment.getInformation())
                                .description(appointment.getDescription())
                                .build()).toList()).build();
    }

    public AppointmentCreateUpdateResponseDTO updateAppointment(AppointmentUpdateRequestDTO appointmentUpdateRequestDTO) {

        AppointmentUpdateRequest.Builder builder = AppointmentUpdateRequest.newBuilder()
                .setId(appointmentUpdateRequestDTO.getId());

        ofNullable(appointmentUpdateRequestDTO.getDateHourScheduled()).map(this::convertLocalDateTimeToGoogleTimestamp).ifPresent(builder::setDateHourScheduled);
        ofNullable(appointmentUpdateRequestDTO.getDateHourStart()).map(this::convertLocalDateTimeToGoogleTimestamp).ifPresent(builder::setDateHourStart);
        ofNullable(appointmentUpdateRequestDTO.getDateHourEnd()).map(this::convertLocalDateTimeToGoogleTimestamp).ifPresent(builder::setDateHourEnd);
        ofNullable(appointmentUpdateRequestDTO.getStatus()).ifPresent(builder::setStatusAppointment);
        ofNullable(appointmentUpdateRequestDTO.getInformation()).ifPresent(builder::setInformation);
        ofNullable(appointmentUpdateRequestDTO.getDescription()).ifPresent(builder::setDescription);

        AppointmentCreateUpdateResponse appointmentUpdateResponse;
        try {
            appointmentUpdateResponse = appointmentServiceBlockingStub.updateAppointment(builder.build());
        } catch (Exception e) {
            log.error("Error calling gRPC service: {}", e.getMessage());
            throw new ApplicationException("Failed to findAllAppointments");
        }
        return AppointmentCreateUpdateResponseDTO.builder().appointmentId(appointmentUpdateResponse.getId()).message("Sucesso").success(true).build();
    }
}

