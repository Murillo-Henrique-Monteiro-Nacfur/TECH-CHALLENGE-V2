package org.hospital.core.domain.service;


import org.hospital.core.domain.dto.UserAuthenticatedDTO;
import org.hospital.core.domain.dto.SessionDTO;
import org.hospital.core.infrastructure.ThreadLocalStorage;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    public SessionDTO getSessionDTO() {
        UserAuthenticatedDTO userAuthenticatedDTO = ThreadLocalStorage.USER_AUTHENTICATION_DTO_CONTEXT_KEY.get();
        return SessionDTO.builder()
                .userName(userAuthenticatedDTO.getUsername())
                .roles(userAuthenticatedDTO.getRoles())
                .userId(userAuthenticatedDTO.getId())
                .build();
    }

}
