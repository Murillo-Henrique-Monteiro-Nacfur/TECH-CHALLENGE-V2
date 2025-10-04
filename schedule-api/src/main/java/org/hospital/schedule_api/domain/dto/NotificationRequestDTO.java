package org.hospital.schedule_api.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDTO {

    private String recipient;

    private String subject;

    private String message;

    private NotificationType type;

    private String templateName;

    public enum NotificationType {
        EMAIL, SMS, PUSH
    }
}