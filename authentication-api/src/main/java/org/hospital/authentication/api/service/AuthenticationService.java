package org.hospital.authentication.api.service;

import lombok.RequiredArgsConstructor;
import org.hospital.authentication.infrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String authenticate(Authentication unauthenticated){
        // O manager valida o token de entrada e retorna um novo, totalmente autenticado.
        Authentication authenticated = authenticationManager.authenticate(unauthenticated);
        // O token JWT deve ser gerado a partir do objeto 'authenticated'.
        return jwtService.generateToken(authenticated);
    }
}
