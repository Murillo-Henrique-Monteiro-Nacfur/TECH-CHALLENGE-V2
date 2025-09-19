package org.hospital.gateway.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@SchemaMapping(typeName = "AppointmentCreateRequestInput")
public class AppointmentCreateRequestDTO{
    private Long doctorUserId;
    private Long patientUserId;
    private LocalDateTime dateHourScheduled;
    private LocalDateTime dateHourStart;
    private LocalDateTime dateHourEnd;
    private String information;
}
