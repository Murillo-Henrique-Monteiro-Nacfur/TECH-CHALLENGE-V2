package org.hospital.authentication.api.presenter;

import org.hospital.core.domain.entity.User;
import org.hospital.core.domain.util.DateUtil;
import org.hospital.core.infrastructure.database.entitydb.UserEntity;
import org.hospital.core.infrastructure.database.entitydb.UserRoleEntity;
import org.hospital.core.infrastructure.database.entitydb.UserRoles;
import org.hospital.user.UserCreateRequest;
import org.hospital.user.UserProto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserPresenters {

    public UserProto toProto(User user) {
        return UserProto.newBuilder()
                .setId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .build();
    }

    public User toDomain(UserCreateRequest userCreateRequest) {
        return User.builder()
                .name(userCreateRequest.getName())
                .email(userCreateRequest.getEmail())
                .password(userCreateRequest.getPassword())
                .login(userCreateRequest.getLogin())
                .birthDate(userCreateRequest.hasBirthDate() ? DateUtil.toLocalDate(userCreateRequest.getBirthDate()) : null)
                .roles(List.of(UserRoles.valueOf(userCreateRequest.getRole().name())))
                .build();
    }

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .login(user.getLogin())
                .birthDate(user.getBirthDate())
                .roles(user.getRoles().stream().map(e -> UserRoleEntity.builder().role(e).build()).toList())
                .build();
    }
}
