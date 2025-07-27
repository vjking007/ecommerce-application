package com.example.notification_service.service;

import com.example.notification_service.dto.NotificationRequest;
import com.example.notification_service.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;

    public void sendNotification(NotificationRequest request) {
        if (request.getType() == NotificationType.EMAIL) {
            sendEmail(request);
        } else {
            sendSms(request);
        }
    }

    private void sendEmail(NotificationRequest request) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(request.getTo());
        mailMessage.setSubject(request.getSubject());
        mailMessage.setText(request.getMessage());
        mailSender.send(mailMessage);
    }

    private void sendSms(NotificationRequest request) {
        // Dummy implementation
        System.out.printf("Sending SMS to %s: %s%n", request.getTo(), request.getMessage());
    }
}

