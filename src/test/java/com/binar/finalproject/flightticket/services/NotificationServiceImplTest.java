package com.binar.finalproject.flightticket.services;

import com.binar.finalproject.flightticket.dto.NotificationDetailResponse;
import com.binar.finalproject.flightticket.dto.NotificationRequest;
import com.binar.finalproject.flightticket.dto.NotificationResponse;
import com.binar.finalproject.flightticket.model.Notification;
import com.binar.finalproject.flightticket.model.Users;
import com.binar.finalproject.flightticket.repository.NotificationRepository;
import com.binar.finalproject.flightticket.repository.UserRepository;
import com.binar.finalproject.flightticket.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class NotificationServiceImplTest {

    @InjectMocks
    NotificationServiceImpl notificationService;

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    UserRepository userRepository;

    private Notification notification;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test [Positive] Add Notification")
    void testPositiveAddNotification() {
        NotificationRequest notificationRequest = new NotificationRequest("Pembayaran", "Pembayaran Berhasil");
        Notification notification = Notification.builder()
                .notificationId(UUID.randomUUID())
                .title(notificationRequest.getTitle())
                .content(notificationRequest.getContent())
                .build();

        Users users = Users.builder()
                .userId(UUID.randomUUID())
                .fullName("JOKO")
                .email("joko@gmail.com")
                .password("12345678")
                .telephone("009987755")
                .birthDate(LocalDate.of(2001, 5, 1))
                .gender(true)
                .statusActive(true)
                .build();

        UUID userId = UUID.randomUUID();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(users));
        Mockito.when(notificationRepository.save(ArgumentMatchers.any(Notification.class)))
                .thenReturn(notification);

        notificationService.addNotification(notificationRequest,userId);
        Mockito.verify(userRepository).findById(userId);
        Mockito.verify(notificationRepository).save(ArgumentMatchers.any(Notification.class));

    }

    @Test
    @DisplayName("Test [Negative] Add Notification")
    void testAddNotificationNotFound() {
        UUID userId = UUID.randomUUID();
        NotificationRequest notificationRequest = new NotificationRequest("Pembayaran", "Pembayaran Berhasil");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        var actualValue = notificationService.addNotification(notificationRequest, userId);

        Assertions.assertNull(actualValue);
    }

    @Test
    @DisplayName("Test Get All Notification By UserId")
    void testGetAllNotificationByUserId() {
        UUID userId = UUID.randomUUID();

        Notification notification = Notification.builder()
                .notificationId(UUID.randomUUID())
                .title("Pembayaran")
                .content("Pembayaran Berhasil")
                .build();

        List<Notification> notifications = new ArrayList<>();
        notifications.add(notification);

        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(notificationRepository.getAllNotificationByUserId(userId))
                .thenReturn(notifications);

        NotificationDetailResponse notificationResponse = notificationService.getAllNotificationByUserId(userId);
        Assertions.assertNotNull(notificationResponse);

        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(notificationRepository).getAllNotificationByUserId(userId);
    }


    @Test
    @DisplayName("Test Get All Notification Id But Empty Success")
    void testGetAllNotificationByUserIdButEmptySuccess() {
        UUID userId = UUID.randomUUID();
        List<Notification> notifications= new ArrayList<>();
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(notificationRepository.getAllNotificationByUserId(userId))
                .thenReturn(notifications);

        NotificationDetailResponse notificationDetailResponse = notificationService.getAllNotificationByUserId(userId);
        Assertions.assertNotNull(notificationDetailResponse);
        Assertions.assertEquals(0, notificationDetailResponse.getUnreadCount());

        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(notificationRepository).getAllNotificationByUserId(userId);
    }

    @Test
    @DisplayName("Test Get All Notification By User Id Not Found")
    void testGetAllNotificationByUserIdNotFound() {
        UUID userId = UUID.randomUUID();
        List<Notification> notifications = new ArrayList<>();
        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        Assertions.assertThrows(RuntimeException.class, () -> {
            notificationService.getAllNotificationByUserId(userId);
        });

        Mockito.verify(userRepository).existsById(userId);
    }

    @Test
    @DisplayName("Test Update Notification Is Read Success")
    void testUpdateIsReadSuccess() {
        UUID userId = UUID.randomUUID();
        UUID notificationId = UUID.randomUUID();


        Notification notification = Notification.builder()
                .notificationId(notificationId)
                .title("Pembayaran")
                .content("Pembayaran Success")
                .isRead(true)
                .createdAt(LocalDateTime.now())
                .build();
        Mockito.when(notificationRepository.findById(notificationId))
                .thenReturn(Optional.of(notification));
        Mockito.when(notificationRepository.save(ArgumentMatchers.any(Notification.class)))
                .thenReturn(notification);
        NotificationResponse notificationResponse = notificationService.updateIsRead(userId, notificationId);
        Assertions.assertNotNull(notificationResponse);
        Assertions.assertTrue(notification.isRead());
        Mockito.verify(notificationRepository).findById(notificationId);
        Mockito.verify(notificationRepository).save(ArgumentMatchers.any(Notification.class));
    }

    @Test
    @DisplayName("Test [Positive] Update Notification")
    void testPositiveUpdateNotification() {
        UUID userId = UUID.randomUUID();
        UUID notificationId = UUID.randomUUID();

        NotificationRequest notificationRequest = new NotificationRequest("Pembayaran", "Pembayaran Berhasil");
        Notification notification = Notification.builder()
                .notificationId(notificationId)
                .title("Pembayaran")
                .content("Pembayaran Success")
                .isRead(true)
                .createdAt(LocalDateTime.now())
                .build();

        Users users = Users.builder()
                .userId(UUID.randomUUID())
                .fullName("JOKO")
                .email("joko@gmail.com")
                .password("12345678")
                .telephone("009987755")
                .birthDate(LocalDate.of(2001, 5, 1))
                .gender(true)
                .statusActive(true)
                .build();

        Mockito.when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(users));
        Mockito.when(notificationRepository.saveAndFlush(ArgumentMatchers.any(Notification.class)))
                .thenReturn(notification);

        notificationService.updateNotification(notificationRequest,notificationId, userId);
        Mockito.verify(userRepository).findById(userId);
        Mockito.verify(notificationRepository).findById(notificationId);
        Mockito.verify(notificationRepository).saveAndFlush(ArgumentMatchers.any(Notification.class));
    }

    @Test
    @DisplayName("Test [Negative] Update Notification")
    void testNegativeUpdateNotification() {
        UUID userId = UUID.randomUUID();
        UUID notificationId = UUID.randomUUID();

        NotificationRequest notificationRequest = new NotificationRequest("Pembayaran", "Pembayaran Berhasil");

        Mockito.when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        var actualValue = notificationService.updateNotification(notificationRequest, notificationId, userId);

        Assertions.assertNull(actualValue);

    }
}
