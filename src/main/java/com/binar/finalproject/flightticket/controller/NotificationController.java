package com.binar.finalproject.flightticket.controller;

import com.binar.finalproject.flightticket.dto.MessageModel;
import com.binar.finalproject.flightticket.dto.NotificationDetailResponse;
import com.binar.finalproject.flightticket.dto.NotificationRequest;
import com.binar.finalproject.flightticket.dto.NotificationResponse;
import com.binar.finalproject.flightticket.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Add Notification",
                            description = "Menambahkan Notification Baru",
                            value = """
                                      {
                                      "status": 200,
                                      "message": "Success update read user notification",
                                      "data": {
                                        "notificationId": "b0c1fac1-cf9d-4d51-937a-6fb839eb0d3a",
                                        "title": "Pembayaran",
                                        "content": "Pembayaran Success",
                                        "read": false
                                      }
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @ResponseBody
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> addNotification(@RequestBody NotificationRequest notificationRequest, @RequestParam UUID userId) {
        MessageModel messageModel =  new MessageModel();
        NotificationResponse notificationResponse = notificationService.addNotification(notificationRequest, userId);
        if (notificationResponse == null) {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("success create new notification");
            messageModel.setData(notificationRequest);
            return ResponseEntity.ok().body(messageModel);
        }else {
            messageModel.setMessage("Failed create notification user");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Get All Notification By userId",
                            description = "Menampilkan Notification dengan userId",
                            value = """
                                    {
                                      "status": 200,
                                      "message": "Success get All Notification By userId",
                                      "data": {
                                        "unreadCount": 2,
                                        "content": [
                                          {
                                            "notificationId": "b0c1fac1-cf9d-4d51-937a-6fb839eb0d3a",
                                            "title": "Pembayaran",
                                            "content": "Pembayaran Success",
                                            "read": false
                                          }
                                        ]
                                      }
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @ResponseBody
    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BUYER')")
    public ResponseEntity<MessageModel> getAll(@RequestParam UUID userId) {
        MessageModel messageModel = new MessageModel();
        NotificationDetailResponse notificationDetailResponse = notificationService.getAllNotificationByUserId(userId);
        if (notificationDetailResponse == null) {
            messageModel.setMessage("Failed get All notification user By userId");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        } else {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Success get All Notification By userId");
            messageModel.setData(notificationDetailResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Update Read Status Notification",
                            description = "Mengubah Status Notification Baru",
                            value = """
                                    {
                                      "status": 200,
                                      "message": "Success update read user notification",
                                      "data": {
                                        "notificationId": "b0c1fac1-cf9d-4d51-937a-6fb839eb0d3a",
                                        "title": "Pembayaran",
                                        "content": "Pembayaran Success",
                                        "read": true
                                      }
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @ResponseBody
    @PutMapping("/update-status")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BUYER')")
    public ResponseEntity<MessageModel> updateReadStatus(@RequestParam UUID userId, @RequestParam UUID notificationId) {
        MessageModel messageModel = new MessageModel();
        NotificationResponse notificationDetailResponse = notificationService.updateIsRead(userId, notificationId);
        if (notificationDetailResponse == null) {
            messageModel.setMessage("Failed update read user notification");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        } else {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Success update read user notification");
            messageModel.setData(notificationDetailResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Update Read Status Notification",
                            description = "Mengubah Notification",
                            value = """
                                    {
                                       "status": 200,
                                       "message": "Update notification with title : Pembayaran",
                                       "data": {
                                         "notificationId": "b0c1fac1-cf9d-4d51-937a-6fb839eb0d3a",
                                         "title": "Pembayaran",
                                         "content": "Pembayaran  Belum Dibayarkan",
                                         "read": false
                                       }
                                     }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BUYER')")
    public ResponseEntity<MessageModel> updateNotification(@RequestBody NotificationRequest notificationRequest, @RequestParam UUID notificationId, @RequestParam UUID userId)
    {
        MessageModel messageModel = new MessageModel();
        NotificationResponse notificationResponse = notificationService.updateNotification(notificationRequest, notificationId, userId);
        if(notificationResponse == null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed to update notification");
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Update notification with title : " + notificationResponse.getTitle());
            messageModel.setData(notificationResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }
}
