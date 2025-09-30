package org.hospital.notification.controller;

import lombok.RequiredArgsConstructor;
import org.hospital.notification.dto.request.NotificationRequest;
import org.hospital.notification.dto.response.NotificationResponse;
import org.hospital.notification.usecase.ProcessNotificationUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final ProcessNotificationUseCase processNotificationUseCase;

    @PostMapping
    public Mono<ResponseEntity<NotificationResponse>> sendNotification(
            @Valid @RequestBody NotificationRequest request) {

        return processNotificationUseCase.execute(request)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    @GetMapping("/health")
    public Mono<ResponseEntity<String>> health() {
        return Mono.just(ResponseEntity.ok("Notification API is running"));
    }
}