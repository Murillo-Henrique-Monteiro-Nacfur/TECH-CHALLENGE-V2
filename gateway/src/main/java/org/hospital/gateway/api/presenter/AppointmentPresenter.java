package org.hospital.gateway.api.presenter;

import org.hospital.appointment.AppointmentPagedResponse;
import org.hospital.appointment.AppointmentProto;
import org.hospital.gateway.api.dto.appointment.AppointmentPagedResponseDTO;
import org.hospital.gateway.api.dto.appointment.AppointmentResponseDTO;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Component
public class AppointmentPresenter {

    public AppointmentPagedResponseDTO toDTO(AppointmentPagedResponse proto) {
        return AppointmentPagedResponseDTO.builder()
                .page(proto.getPage())
                .totalPages(proto.getTotalPages())
                .totalElements(proto.getTotalElements())
                .size(proto.getAppointmentsList().size())
                .appointments(proto.getAppointmentsList().stream().map(this::toDTO).toList())
                .build();
    }

    public AppointmentResponseDTO toDTO(AppointmentProto proto) {
        return AppointmentResponseDTO.builder()
                .id(proto.getId())
                .doctorUserId(proto.getDoctorUserId())
                .patientUserId(proto.getPatientUserId())
                .dateHourScheduled(fromProto(proto.getDateHourScheduled()))
                .dateHourStart(proto.hasDateHourStart() ? fromProto(proto.getDateHourStart()) : null)
                .dateHourEnd(proto.hasDateHourEnd() ? fromProto(proto.getDateHourEnd()) : null)
                .status(proto.getStatusAppointment())
                .information(proto.getInformation())
                .description(proto.getDescription())
                .build();
    }

    public com.google.protobuf.Timestamp toProto(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return null;
        }
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        return com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    private LocalDateTime fromProto(com.google.protobuf.Timestamp proto) {
        if (proto == null || (proto.getSeconds() == 0 && proto.getNanos() == 0)) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(proto.getSeconds(), proto.getNanos()), ZoneOffset.UTC);
    }
}