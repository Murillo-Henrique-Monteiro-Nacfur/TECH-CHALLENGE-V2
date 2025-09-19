package org.hospital.gateway.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

@Getter
@Setter
@Builder
@SchemaMapping(typeName = "LoginResponse")
public class LoginResponseDTO {
    private String token;
}
