package org.hospital.authentication.api.validator.user;

import org.hospital.core.domain.entity.User;

public interface UserCreateValidator {

    public void validate(User newUser);
}
