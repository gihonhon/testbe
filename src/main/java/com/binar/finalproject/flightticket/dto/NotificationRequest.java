package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Notification;
import com.binar.finalproject.flightticket.model.Users;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationRequest {

    private String title;

    private String content;

    public Notification toNotification(Users users) {
        Notification notification =  new Notification();
        notification.setTitle(this.title);
        notification.setContent(this.content);
        notification.setUser(users);
        return notification;
    }
}
