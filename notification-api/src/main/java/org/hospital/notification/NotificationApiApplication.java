package org.hospital.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class NotificationApiApplication {
    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(NotificationApiApplication.class, args);
    }
}