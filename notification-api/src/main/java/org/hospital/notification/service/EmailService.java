package org.hospital.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.notification.dto.request.NotificationRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(NotificationRequest request) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.getRecipient());
            message.setSubject(request.getSubject());
            message.setText(request.getMessage());

            mailSender.send(message);
            log.info("Email sent successfully to: {}", request.getRecipient());
        } catch (Exception e) {
            log.error("Error sending email to: {}", request.getRecipient(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
