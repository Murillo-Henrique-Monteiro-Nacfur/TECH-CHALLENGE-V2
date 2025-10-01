package org.hospital.gateway.api.dto.appointment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hospital.appointment.StatusAppointment;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@SchemaMapping(typeName = "AppointmentUpdateRequestInput")
public class AppointmentUpdateRequestDTO {
    private Long id;
    private LocalDateTime dateHourScheduled;
    private LocalDateTime dateHourStart;
    private LocalDateTime dateHourEnd;
    private StatusAppointment status;
    private String information;
    private String description;
}
