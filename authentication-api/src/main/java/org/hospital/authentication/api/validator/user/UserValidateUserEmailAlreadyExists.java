package org.hospital.authentication.api.validator.user;

import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.entity.User;
import org.hospital.core.domain.service.UserServiceCore;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserValidateUserEmailAlreadyExists implements UserCreateValidator {
    private final UserServiceCore userServiceCore;

    @Override
    public void validate(User newUser) {
        Optional<User> user = userServiceCore.getUserByEmail(newUser.getEmail());
        if (user.isPresent()) {
            throw new ApplicationException("User email already exists");
        }
    }
}
