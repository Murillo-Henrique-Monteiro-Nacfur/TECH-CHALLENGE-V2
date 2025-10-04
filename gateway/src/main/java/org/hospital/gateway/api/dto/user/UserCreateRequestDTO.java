package org.hospital.gateway.api.dto.user;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hospital.user.ROLES;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@SchemaMapping(typeName = "CreateUserRequest")
public class UserCreateRequestDTO {
    private String name;
    private String email;
    private String login;
    private String password;
    private LocalDateTime birthDate;
    private ROLES role;
}
