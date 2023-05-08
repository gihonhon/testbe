package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Notification;
import lombok.*;

import java.util.UUID;

@Data
@Builder
public class NotificationResponse {

    private UUID notificationId;

    private String title;

    private String content;

    private boolean isRead;

    public static NotificationResponse build(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .isRead(notification.isRead())
                .build();
    }
}
