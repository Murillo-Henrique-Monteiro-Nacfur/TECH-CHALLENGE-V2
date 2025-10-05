package org.hospital.schedule_api.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.schedule_api.domain.dto.NotificationRequestDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerNotificationService {

    private final KafkaTemplate<String, NotificationRequestDTO> kafkaTemplate;

    public void sendNotification(NotificationRequestDTO request) {
        log.info("Sending notification to Kafka topic: {}", "notification-topic");
        kafkaTemplate.send("notification-topic", request);
        log.info("Notification sent successfully to Kafka.");
    }
}