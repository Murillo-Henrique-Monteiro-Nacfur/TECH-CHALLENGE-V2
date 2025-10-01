package org.hospital.historical.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityConfigValues securityConfigValues;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for simplicity, not recommended for production
                .authorizeHttpRequests(auth -> auth.requestMatchers("/actuator/**").permitAll() // Allow public access to login endpoints)
                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(conf -> conf.jwt(Customizer.withDefaults())); // Require authentication for all other requests
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)); // Allow frames from the same origin, useful for H2 console
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(securityConfigValues.getPublicTokenSecretFile()).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
