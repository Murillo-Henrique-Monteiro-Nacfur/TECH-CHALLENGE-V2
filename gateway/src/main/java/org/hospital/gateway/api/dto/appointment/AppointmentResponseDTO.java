package org.hospital.gateway.api.dto.appointment;

import lombok.Builder;
import lombok.Getter;
import org.hospital.appointment.StatusAppointment;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.time.LocalDateTime;

@Builder
@Getter
@SchemaMapping(typeName = "AppointmentResponseDTO")
public class AppointmentResponseDTO {
    private Long id;
    private Long doctorUserId;
    private Long patientUserId;
    private LocalDateTime dateHourScheduled;
    private LocalDateTime dateHourStart;
    private LocalDateTime dateHourEnd;
    private StatusAppointment status;
    private String information;
    private String description;
}
