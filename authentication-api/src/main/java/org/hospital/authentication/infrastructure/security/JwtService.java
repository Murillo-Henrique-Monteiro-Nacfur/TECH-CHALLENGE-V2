package org.hospital.authentication.infrastructure.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.core.domain.dto.UserAuthenticatedDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

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

    public UserAuthenticatedDTO validateToken(String token) {
        if (token == null) {
            return null;
        }
        try {
            var jwt = jwtDecoder.decode(token);
            if (jwt == null) {
                throw new SecurityException("Invalid token");
            }

            return buildUserDetailsFromJwt(jwt);
        } catch (JwtException ex) {
            log.error("Falha ao validar token: {}", ex.getMessage());
            throw new SecurityException("Invalid token");
        }
    }

    private UserAuthenticatedDTO buildUserDetailsFromJwt(Jwt jwt) {
        String username = jwt.getSubject();
        String scopeClaim = jwt.getClaimAsString("scopes");
        String idString = jwt.getClaimAsString("userId");

        Long userId = Long.parseLong(idString);

        List<String> authorities = scopeClaim != null ?
                Arrays.stream(scopeClaim.split(" ")).toList() : Collections.emptyList();

        return new UserAuthenticatedDTO(userId  , username, "", authorities);
    }
}
