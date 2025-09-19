package org.hospital.authentication.api.domain;

import lombok.RequiredArgsConstructor;
import org.hospital.authentication.api.usecase.UserFindByNameUseCase;
import org.hospital.core.domain.dto.UserAuthenticatedDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserFindByNameUseCase userFindByNameUseCase;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // Implement the logic to load user details from the database or any other source
        // For example, you can fetch user details from a UserRepository
        // and return a UserDetails object.
        var user = userFindByNameUseCase.findByLogin(login);
        // This is just a placeholder implementation.
        return new UserAuthenticatedDTO(user.getId(), user.getName(), user.getPassword(),user.getRoles().stream().map(Enum::name).toList());
    }
}
