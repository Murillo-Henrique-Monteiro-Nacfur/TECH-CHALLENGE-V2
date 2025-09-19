package org.hospital.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("org.hospital.core")
@EnableJpaRepositories("org.hospital.core.infrastructure.database.repository")
@EntityScan("org.hospital.core.infrastructure.database.entitydb")
public class CoreAutoConfiguration {
}
