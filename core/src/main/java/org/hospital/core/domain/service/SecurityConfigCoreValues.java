package org.hospital.core.domain.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPublicKey;

@Configuration
@Getter
public class SecurityConfigCoreValues {

    @Value("classpath:security/app.pub")
    private RSAPublicKey publicTokenSecretFile;
}
