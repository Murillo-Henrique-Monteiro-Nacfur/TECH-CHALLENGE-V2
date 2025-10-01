package org.hospital.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import reactor.core.publisher.Hooks;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class NotificationApiApplication {
    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(NotificationApiApplication.class, args);
    }
}