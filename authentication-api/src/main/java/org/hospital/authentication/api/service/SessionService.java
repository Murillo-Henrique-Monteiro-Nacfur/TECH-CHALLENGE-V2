package org.hospital.authentication.api.service;

import org.hospital.authentication.infrastructure.security.GrpcServerAuthInterceptor;
import org.hospital.core.domain.entity.SessionDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    public SessionDTO getSessionDTO() {
        UserDetails userDetails = GrpcServerAuthInterceptor.USER_DETAILS_CONTEXT_KEY.get();
        return  SessionDTO.builder().userName(userDetails.getUsername()).build();
    }
}
