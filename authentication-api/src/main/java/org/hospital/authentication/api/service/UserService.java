package org.hospital.authentication.api.service;

import lombok.RequiredArgsConstructor;
import org.hospital.authentication.api.presenter.UserPresenters;
import org.hospital.core.domain.service.UserServiceCore;
import org.hospital.user.UserProto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserPresenters userPresenters;

    private final UserServiceCore userServiceCore;

    public UserProto getUserById(Long id){
        return userPresenters.toProto(userServiceCore.getUserByIdOrFail(id));
    }

}
