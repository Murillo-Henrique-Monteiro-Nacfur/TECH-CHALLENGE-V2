package org.hospital.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.WebApplicationType;

@SpringBootApplication
public class NotificationApiApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(NotificationApiApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }
}