package com.example.notification_service.dto;

import com.example.notification_service.enums.NotificationType;
import lombok.Data;

@Data
public class NotificationRequest {
    private String to;
    private String subject;
    private String message;
    private NotificationType type; // EMAIL or SMS
}

