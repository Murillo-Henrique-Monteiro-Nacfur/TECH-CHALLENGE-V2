package org.hospital.core.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hospital.core.infrastructure.database.entitydb.UserRoles;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;
    private LocalDate birthDate;
    private List<UserRoles> roles;
}
