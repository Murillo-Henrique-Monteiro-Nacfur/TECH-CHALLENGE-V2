package org.hospital.authentication.api.mappers;

import org.hospital.core.domain.entity.User;
import org.hospital.user.UserProto;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    public UserProto toProto(User user){
        return UserProto.newBuilder()
                .setId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .build();
    }

}
