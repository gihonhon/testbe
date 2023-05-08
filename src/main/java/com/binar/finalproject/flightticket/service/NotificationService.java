package com.binar.finalproject.flightticket.service;

import com.binar.finalproject.flightticket.dto.NotificationDetailResponse;
import com.binar.finalproject.flightticket.dto.NotificationRequest;
import com.binar.finalproject.flightticket.dto.NotificationResponse;

import java.util.UUID;

public interface NotificationService {

    NotificationDetailResponse getAllNotificationByUserId(UUID userId);

    NotificationResponse updateIsRead(UUID userId, UUID notificationId);

    NotificationResponse addNotification(NotificationRequest notificationRequest, UUID userId);

    NotificationResponse updateNotification(NotificationRequest notificationRequest, UUID notificationId, UUID userId);

}
