package org.hospital.authentication.api.service.handler;

import lombok.RequiredArgsConstructor;
import org.hospital.authentication.api.mappers.UserProjection;
import org.hospital.core.domain.service.UserServiceCore;
import org.hospital.user.UserProto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceHandler {
    private final UserProjection userProjection;

    private final UserServiceCore userServiceCore;
    public UserProto getUserByName(String name){
        return userProjection.toProto(userServiceCore.getUserByLogin(name));
    }
    public UserProto getUserById(Long id){
        return userProjection.toProto(userServiceCore.getUserById(id));
    }

}
