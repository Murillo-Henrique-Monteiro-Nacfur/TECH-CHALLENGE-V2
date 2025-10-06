package org.hospital.notification.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.notification.dto.request.NotificationRequest;
import org.hospital.notification.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.group.id}")
    public void consume(NotificationRequest request) {
        log.info("Consumed message Kafka sending email: {}", request.getRecipient());
        try {
            emailService.sendEmail(request);
        } catch (Exception e) {
            log.error("Failed to send email to {}. Reason: {}", request.getRecipient(), e.getMessage(), e);
        }
    }
}