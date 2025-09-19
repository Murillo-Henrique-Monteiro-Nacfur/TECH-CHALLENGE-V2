package org.hospital.core.infrastructure.database;

import org.hospital.core.domain.entity.User;
import org.hospital.core.infrastructure.database.entitydb.UserEntity;
import org.hospital.core.infrastructure.database.entitydb.UserRoleEntity;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class UserMapper {

    public User toDomain(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles(isNull(userEntity.getRoles()) ? null : userEntity.getRoles().stream().map(UserRoleEntity::getRole).toList())
                .build();
    }

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(isNull(user.getRoles()) ? null : user.getRoles().stream().map(userRoles -> UserRoleEntity.builder().role(userRoles).build()).toList())
                .build();
    }
}
