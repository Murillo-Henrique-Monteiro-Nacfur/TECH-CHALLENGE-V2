package org.hospital.notification.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.notification.dto.request.NotificationRequest;
import org.hospital.notification.usecase.ProcessNotificationUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer implements CommandLineRunner {

    private final KafkaReceiver<String, Object> kafkaReceiver;
    private final ProcessNotificationUseCase processNotificationUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) {
        kafkaReceiver.receive()
                .doOnNext(this::logReceivedMessage)
                .flatMap(this::processMessage)
                .doOnError(error -> log.error("Error processing message", error))
                .retry()
                .subscribe();

        log.info("Notification consumer started and listening to Kafka topics");
    }

    private void logReceivedMessage(ReceiverRecord<String, Object> record) {
        log.info("Received message: key={}, value={}, topic={}, partition={}, offset={}",
                record.key(), record.value(), record.topic(), record.partition(), record.offset());
    }

    private reactor.core.publisher.Mono<Void> processMessage(ReceiverRecord<String, Object> record) {
        try {
            NotificationRequest request = objectMapper.convertValue(record.value(), NotificationRequest.class);

            return processNotificationUseCase.execute(request)
                    .doOnSuccess(response -> {
                        log.info("Notification processed successfully: {}", response);
                        record.receiverOffset().acknowledge();
                    })
                    .doOnError(error -> log.error("Failed to process notification", error))
                    .then();

        } catch (Exception e) {
            log.error("Failed to parse message: {}", record.value(), e);
            record.receiverOffset().acknowledge();
            return reactor.core.publisher.Mono.empty();
        }
    }
}
