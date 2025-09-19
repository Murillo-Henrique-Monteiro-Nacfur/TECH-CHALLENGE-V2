package org.hospital.authentication.api.usecase;

import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.entity.User;
import org.hospital.core.domain.service.UserServiceCore;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFindByNameUseCase {

    private final UserServiceCore userServiceCore;

    public User findByLogin(String login) {
        return userServiceCore.getUserByLogin(login);
    }
}
