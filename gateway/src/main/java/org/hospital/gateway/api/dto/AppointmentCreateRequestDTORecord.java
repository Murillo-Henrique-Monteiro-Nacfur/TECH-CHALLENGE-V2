package org.hospital.gateway.api.dto;

import java.time.LocalDateTime;


public record AppointmentCreateRequestDTORecord(
        Long doctorUserId,
        Long patientUserId,
        LocalDateTime dateHourScheduled,
        LocalDateTime dateHourStart,
        LocalDateTime dateHourEnd,
        String information) {
}
