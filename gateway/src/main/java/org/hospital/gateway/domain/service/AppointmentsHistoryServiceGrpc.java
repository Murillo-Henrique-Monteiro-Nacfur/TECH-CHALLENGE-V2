package org.hospital.gateway.domain.service;

import com.google.protobuf.UInt64Value;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.hospital.appointment.*;
import org.hospital.appointmenthistory.AppointmentHistoryPagedRequest;
import org.hospital.appointmenthistory.AppointmentHistoryPagedResponse;
import org.hospital.appointmenthistory.AppointmentHistoryServiceGrpc;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.hospital.gateway.api.dto.appointment.*;
import org.hospital.gateway.api.dto.appointmenthistory.AppointmentHistoryPagedResponseDTO;
import org.hospital.gateway.api.dto.appointmenthistory.AppointmentHistoryResponseDTO;
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
public class AppointmentsHistoryServiceGrpc {

    @GrpcClient("history-service")
    private AppointmentHistoryServiceGrpc.AppointmentHistoryServiceBlockingStub appointmentHistoryServiceBlockingStub;

    public AppointmentHistoryPagedResponseDTO findAllAppointmentsHistory(PageableDTO pageable) {

        AppointmentHistoryPagedRequest request = AppointmentHistoryPagedRequest
                .newBuilder()
                .setPage(ofNullable(pageable).map(PageableDTO::getPage).orElse(0))
                .setSize(ofNullable(pageable).map(PageableDTO::getSize).orElse(10))
                .build();

        AppointmentHistoryPagedResponse appointmentHistoryPagedResponse;

            appointmentHistoryPagedResponse = appointmentHistoryServiceBlockingStub.getAppointmentsHistoryByFilter(request);

        return AppointmentHistoryPagedResponseDTO.builder()
                .page(appointmentHistoryPagedResponse.getPage())
                .totalPages(appointmentHistoryPagedResponse.getTotalPages())
                .totalElements(appointmentHistoryPagedResponse.getTotalElements())
                .size(appointmentHistoryPagedResponse.getAppointmentHistoryCount())
                .appointments(appointmentHistoryPagedResponse.getAppointmentHistoryList().stream().map(appointment ->
                        AppointmentHistoryResponseDTO.builder()
                                .id(appointment.getId())
                                .idAppointment(appointment.getIdAppointment())
                                .doctorName(appointment.getDoctorName())
                                .patientName(appointment.getPatientName())
                                .dateHourStart(appointment.hasDateHourStart() ? LocalDateTime.ofInstant(Instant.ofEpochSecond(appointment.getDateHourStart().getSeconds(), appointment.getDateHourStart().getNanos()), ZoneOffset.UTC) : null)
                                .dateHourEnd(appointment.hasDateHourEnd() ? LocalDateTime.ofInstant(Instant.ofEpochSecond(appointment.getDateHourEnd().getSeconds(), appointment.getDateHourEnd().getNanos()), ZoneOffset.UTC) : null)
                                .statusAppointment(appointment.getStatusAppointment())
                                .information(appointment.getInformation())
                                .description(appointment.getDescription())
                                .build()).toList()).build();
    }

}

