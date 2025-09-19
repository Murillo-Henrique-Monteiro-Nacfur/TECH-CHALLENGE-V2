package org.hospital.core.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtDecoreServiceCore {

    private final JwtDecoder jwtDecoder;

    public UserDetails validateToken(String token) {
        if (token == null) {
            return null;
        }
        try {
            Jwt jwt = jwtDecoder.decode(token);
            if (jwt == null) {
                throw new SecurityException("Invalid token");
            }

            return buildUserDetailsFromJwt(jwt);
        } catch (JwtException ex) {
            // Log da exceção pode ser útil aqui. ex: log.error("Falha ao validar token: {}", ex.getMessage());
            log.error("Falha ao validar token: {}", ex.getMessage());
            throw new SecurityException("Invalid token");
        }
    }

    private UserDetails buildUserDetailsFromJwt(Jwt jwt) {
        String username = jwt.getSubject();
        String scopeClaim = jwt.getClaimAsString("scopes");

        List<SimpleGrantedAuthority> authorities = scopeClaim != null ?
                Arrays.stream(scopeClaim.split(" "))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()) : Collections.emptyList();

        return new User(username, "", authorities);
    }
}
