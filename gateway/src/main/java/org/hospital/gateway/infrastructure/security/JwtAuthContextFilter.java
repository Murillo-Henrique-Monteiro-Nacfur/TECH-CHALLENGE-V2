package org.hospital.gateway.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.service.JwtDecoreServiceCore;
import org.hospital.core.infrastructure.ThreadLocalStorage;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthContextFilter extends OncePerRequestFilter {

    private final JwtDecoreServiceCore jwtDecoreServiceCore;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                // Armazena apenas o token, sem o prefixo "Bearer "
                String jwt = bearerToken.substring(7);
                ThreadLocalStorage.build(jwtDecoreServiceCore.validateToken(jwt) , jwt);
            }
            filterChain.doFilter(request, response);
        } finally {
            // Essencial para limpar o ThreadLocal e evitar memory leaks
            ThreadLocalStorage.clear();
        }
    }
}