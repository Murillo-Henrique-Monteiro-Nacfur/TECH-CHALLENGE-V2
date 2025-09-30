package org.hospital.notification.usecase;

import lombok.RequiredArgsConstructor;
import org.hospital.notification.dto.request.NotificationRequest;
import org.hospital.notification.dto.response.NotificationResponse;
import org.hospital.notification.service.NotificationService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProcessNotificationUseCase {

    private final NotificationService notificationService;

    public Mono<NotificationResponse> execute(NotificationRequest request) {
        return notificationService.processNotification(request);
    }
}
