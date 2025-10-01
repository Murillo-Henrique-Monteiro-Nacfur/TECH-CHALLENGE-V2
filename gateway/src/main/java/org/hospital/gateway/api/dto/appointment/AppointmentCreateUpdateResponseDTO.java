package org.hospital.gateway.api.dto.appointment;

import lombok.Builder;
import lombok.Getter;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

@Builder
@Getter
@SchemaMapping(typeName = "AppointmentCreateUpdateResponse")
public class AppointmentCreateUpdateResponseDTO {
    private Long appointmentId;
    private Boolean success;
    private String message;
}
