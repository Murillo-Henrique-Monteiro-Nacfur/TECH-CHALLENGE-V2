package org.hospital.authentication.infrastructure.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.core.domain.dto.UserAuthenticatedDTO;
import org.hospital.core.infrastructure.database.entitydb.UserRoles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtEncoder jwtEncoder;

    public String generateToken(Authentication authentication) {
        Instant instant = Instant.now();
        long expirationTime = 3600L;
        long userId = 0L;
        String scopes = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        if(authentication.getPrincipal() instanceof UserAuthenticatedDTO userAuthenticatedDTO){
            userId = userAuthenticatedDTO.getId();
        }
        var claims = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuer("authentication-api")
                .issuedAt(instant)
                .expiresAt(instant.plusSeconds(expirationTime))
                .claim("scopes", scopes)
                .claim("userId", userId)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
