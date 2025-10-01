package org.hospital.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.notification.dto.request.NotificationRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.group.id}")
    public void consumeNotification(NotificationRequest request) {
        log.info("Consumed message from Kafka");
        log.info("Received Notification: {}", request);
    }
}