package org.hospital.core.domain.service;

import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.entity.User;
import org.hospital.core.infrastructure.database.UserMapper;
import org.hospital.core.infrastructure.database.entitydb.UserEntity;
import org.hospital.core.infrastructure.database.repository.UserRepository;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceCore {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public User getUserByIdOrFail(Long id) {
        return getUserById(id).orElseThrow(() -> new ApplicationException("User not found"));
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        var userEntity = userRepository.findById(id);
        return userEntity.map(userMapper::toDomain);
    }

    @Transactional(readOnly = true)
    public User getUserByLoginOrFail(String login) {
        var userEntity = userRepository.findByLogin(login).orElseThrow(() -> new ApplicationException("User not found"));
        return userMapper.toDomain(userEntity);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByLogin(String login) {
        var userEntity = userRepository.findByLogin(login);
        return userEntity.map(userMapper::toDomain);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        var userEntity = userRepository.findByEmail(email);
        return userEntity.map(userMapper::toDomain);
    }

    public User create(UserEntity user) {
        user.getRoles().forEach(userRole -> userRole.setUserEntity(user));
        var userEntity = userRepository.save(user);
        return userMapper.toDomain(userEntity);
    }
}
