package org.hospital.gateway.api.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

@Builder
@Getter
@Setter
@SchemaMapping(typeName = "AppointmentCreateRequestInput")
public class UserCreatedResponseDTO {
    private Long userId;
    private Boolean success;
    private String message;
}
