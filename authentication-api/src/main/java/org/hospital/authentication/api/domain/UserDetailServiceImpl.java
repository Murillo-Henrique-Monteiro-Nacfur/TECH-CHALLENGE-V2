package org.hospital.authentication.api.domain;

import lombok.RequiredArgsConstructor;
import org.hospital.authentication.api.usecase.user.UserFindByLoginUseCase;
import org.hospital.core.domain.dto.UserAuthenticatedDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserFindByLoginUseCase userFindByLoginUseCase;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        var user = userFindByLoginUseCase.findByLogin(login);
        return new UserAuthenticatedDTO(user.getId(), user.getName(), user.getPassword(),user.getRoles());
    }
}
