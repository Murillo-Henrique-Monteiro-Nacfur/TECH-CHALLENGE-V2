package org.hospital.authentication.api.usecase.user;

import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.entity.User;
import org.hospital.core.domain.service.UserServiceCore;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFindByLoginUseCase {

    private final UserServiceCore userServiceCore;

    public User findByLogin(String login) {
        return userServiceCore.getUserByLoginOrFail(login);
    }
}
