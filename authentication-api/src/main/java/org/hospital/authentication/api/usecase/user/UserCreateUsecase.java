package org.hospital.authentication.api.usecase.user;


import lombok.RequiredArgsConstructor;
import org.hospital.authentication.api.presenter.UserPresenters;
import org.hospital.authentication.api.validator.user.UserCreateValidator;
import org.hospital.core.domain.entity.User;
import org.hospital.core.domain.service.UserServiceCore;
import org.hospital.core.infrastructure.database.entitydb.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCreateUsecase {

    private final UserServiceCore userServiceCore;
    private final UserPresenters userPresenters;
    private final List<UserCreateValidator> validators;

    public User execute(User user) {
        validate(user);
        UserEntity userEntity = userPresenters.toEntity(user);
        return userServiceCore.create(userEntity);
    }

    private void validate(User user) {
        validators.forEach(validator -> validator.validate(user));
    }
}
