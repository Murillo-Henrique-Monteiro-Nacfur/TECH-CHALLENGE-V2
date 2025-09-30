package org.hospital.notification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private String id;
    private String status;
    private String message;
    private LocalDateTime timestamp;

    public static NotificationResponse success(String id, String message) {
        return new NotificationResponse(id, "SUCCESS", message, LocalDateTime.now());
    }

    public static NotificationResponse error(String id, String message) {
        return new NotificationResponse(id, "ERROR", message, LocalDateTime.now());
    }
}
