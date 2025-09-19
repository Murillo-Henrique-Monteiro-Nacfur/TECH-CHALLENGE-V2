package org.hospital.core.domain.service;

import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.entity.User;
import org.hospital.core.infrastructure.database.UserMapper;
import org.hospital.core.infrastructure.database.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceCore {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDomain(userEntity);
    }

    @Transactional(readOnly = true)
    public User getUserByLogin(String login) {
        var userEntity = userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDomain(userEntity);
    }
}
