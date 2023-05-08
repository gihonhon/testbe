package com.binar.finalproject.flightticket.service.impl;

import com.binar.finalproject.flightticket.dto.NotificationDetailResponse;
import com.binar.finalproject.flightticket.dto.NotificationRequest;
import com.binar.finalproject.flightticket.dto.NotificationResponse;
import com.binar.finalproject.flightticket.model.Notification;
import com.binar.finalproject.flightticket.model.Users;
import com.binar.finalproject.flightticket.repository.NotificationRepository;
import com.binar.finalproject.flightticket.repository.UserRepository;
import com.binar.finalproject.flightticket.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class NotificationServiceImpl implements NotificationService {


    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public NotificationDetailResponse getAllNotificationByUserId(UUID userId) {
        boolean isExists = userRepository.existsById(userId);
        if (!isExists) {
            throw new RuntimeException("User not found");
        }
        List<Notification> notifications = notificationRepository.getAllNotificationByUserId(userId);
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        if (notifications.isEmpty()) {
            return NotificationDetailResponse.builder()
                    .unreadCount(0)
                    .content(notificationResponses)
                    .build();
        }
        Integer unreadNotification = notificationRepository.getUnreadNotification();
        for (Notification notification : notifications){
            NotificationResponse notificationResponse = NotificationResponse.build(notification);
            notificationResponses.add(notificationResponse);
        }
        return NotificationDetailResponse.builder()
                .unreadCount(unreadNotification)
                .content(notificationResponses)
                .build();
    }

    @Override
    public NotificationResponse updateIsRead(UUID userId, UUID notificationId) {
        Optional<Notification> isNotification = notificationRepository.findById(notificationId);
        if (isNotification.isPresent()) {
            Notification notification = isNotification.get();
            notification.setRead(true);
            notification.setModifiedAt(LocalDateTime.now());
            notificationRepository.save(notification);
            return NotificationResponse.build(notification);
        } else {
            return null;
        }
    }

    @Override
    public NotificationResponse addNotification(NotificationRequest notificationRequest, UUID userId) {
        try {
            Users users = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User account not found"));
            Notification notification = Notification.builder()
                    .notificationId(userId)
                    .user(users)
                    .title(notificationRequest.getTitle())
                    .content(notificationRequest.getContent())
                    .createdAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(notification);
        }
        catch (Exception ignore){
            return null;
        }
        return null;
    }

    @Override
    public NotificationResponse updateNotification(NotificationRequest notificationRequest, UUID notificationId, UUID userId) {
        Optional<Notification> isNotification = notificationRepository.findById(notificationId);
        String message = null;
        if (isNotification.isPresent()) {
            Notification notification = isNotification.get();
            notification.setTitle(notificationRequest.getTitle());
            notification.setContent(notification.getContent());
            notification.setRead(false);
            Optional<Users> users = userRepository.findById(userId);
            if (users.isPresent())
                notification.setUser(users.get());
            else
                message = "Users with this id doesnt exist";

            if (message != null)
                return null;
            else {
                notificationRepository.saveAndFlush(notification);
                return NotificationResponse.build(notification);
            }
        }
        else {
            return null;
        }
    }
}
