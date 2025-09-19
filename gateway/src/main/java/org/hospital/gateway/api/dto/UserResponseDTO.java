package org.hospital.gateway.api.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

@Getter
@Setter
@Builder
@SchemaMapping(typeName = "User")
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
}
