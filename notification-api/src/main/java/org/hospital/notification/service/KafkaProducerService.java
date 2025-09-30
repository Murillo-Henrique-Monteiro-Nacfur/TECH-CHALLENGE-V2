package org.hospital.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.notification.dto.request.NotificationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, NotificationRequest> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    public void sendNotification(NotificationRequest request) {
        log.info("Sending notification to Kafka topic: {}", topicName);
        kafkaTemplate.send(topicName, request);
        log.info("Notification sent successfully to Kafka.");
    }
}