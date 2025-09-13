package com.banking.notification.controller;

import com.banking.notification.model.Notification;
import com.banking.notification.model.NotificationType;
import com.banking.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> createNotification(@Valid @RequestBody Notification notification) {
        try {
            Notification createdNotification = notificationService.createNotification(notification);
            return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<Notification> sendNotification(@PathVariable Long id) {
        try {
            Notification sentNotification = notificationService.sendNotification(id);
            return new ResponseEntity<>(sentNotification, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = notificationService.getNotificationById(id);
        return notification.map(n -> new ResponseEntity<>(n, HttpStatus.OK))
                          .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotificationsByUserId(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getUnreadNotificationsByUserId(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/unread-count")
    public ResponseEntity<Long> getUnreadCount(@PathVariable Long userId) {
        long count = notificationService.getUnreadCount(userId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Notification>> getNotificationsByType(@PathVariable NotificationType type) {
        List<Notification> notifications = notificationService.getNotificationsByType(type);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Notification>> getNotificationsByStatus(@PathVariable String status) {
        List<Notification> notifications = notificationService.getNotificationsByStatus(status);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Notification>> getNotificationsByPriority(@PathVariable String priority) {
        List<Notification> notifications = notificationService.getNotificationsByPriority(priority);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Notification>> getNotificationsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<Notification> notifications = notificationService.getNotificationsByDateRange(start, end);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        try {
            Notification notification = notificationService.markAsRead(id);
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        try {
            notificationService.deleteNotification(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/send-email")
    public ResponseEntity<Notification> sendEmailNotification(
            @RequestParam Long userId,
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam String email) {
        try {
            Notification notification = notificationService.sendEmailNotification(userId, title, message, email);
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/send-sms")
    public ResponseEntity<Notification> sendSMSNotification(
            @RequestParam Long userId,
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam String phoneNumber) {
        try {
            Notification notification = notificationService.sendSMSNotification(userId, title, message, phoneNumber);
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/send-push")
    public ResponseEntity<Notification> sendPushNotification(
            @RequestParam Long userId,
            @RequestParam String title,
            @RequestParam String message) {
        try {
            Notification notification = notificationService.sendPushNotification(userId, title, message);
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/send-in-app")
    public ResponseEntity<Notification> sendInAppNotification(
            @RequestParam Long userId,
            @RequestParam String title,
            @RequestParam String message) {
        try {
            Notification notification = notificationService.sendInAppNotification(userId, title, message);
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}