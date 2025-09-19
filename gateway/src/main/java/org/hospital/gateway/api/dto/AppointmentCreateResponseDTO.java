package org.hospital.gateway.api.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

@Builder
@Getter
@SchemaMapping(typeName = "AppointmentCreatedResponse")
public class AppointmentCreateResponseDTO {
    private Long appointmentId;
    private Boolean success;
    private String message;
}
