package org.hospital.core.domain.dto;

import lombok.Builder;
import lombok.Getter;
import org.hospital.core.infrastructure.database.entitydb.UserRoles;

import java.util.List;

@Builder
public class SessionDTO {
    private String token;
    @Getter
    private Long userId;
    @Getter
    private String userName;
    @Getter
    private List<UserRoles> roles;

    public boolean isAdmin() {
        return roles.contains(UserRoles.ADMIN);
    }

    public boolean isDoctor() {
        return roles.contains(UserRoles.DOCTOR);
    }

    public boolean isNurse() {
        return roles.contains(UserRoles.NURSE);
    }
    public boolean isPatient() {
        return roles.contains(UserRoles.PATIENT);
    }
}
