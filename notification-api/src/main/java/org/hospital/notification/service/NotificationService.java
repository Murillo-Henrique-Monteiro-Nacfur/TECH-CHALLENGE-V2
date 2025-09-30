package org.hospital.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.notification.dto.request.NotificationRequest;
import org.hospital.notification.dto.response.NotificationResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final KafkaProducerService kafkaProducerService;

    public Mono<NotificationResponse> processNotification(NotificationRequest request) {
        String notificationId = UUID.randomUUID().toString();

        log.info("Processing notification: {} for recipient: {}", notificationId, request.getRecipient());

        // Envia a notificação para o Kafka e retorna uma resposta imediata.
        kafkaProducerService.sendNotification(request);

        return Mono.just(NotificationResponse.success(notificationId, "Notification request received and is being processed."));
    }
}