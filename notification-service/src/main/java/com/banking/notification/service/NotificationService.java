package com.banking.notification.service;

import com.banking.notification.model.Notification;
import com.banking.notification.model.NotificationType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationService {
    Notification createNotification(Notification notification);
    Notification sendNotification(Long notificationId);
    Optional<Notification> getNotificationById(Long id);
    List<Notification> getAllNotifications();
    List<Notification> getNotificationsByUserId(Long userId);
    List<Notification> getNotificationsByType(NotificationType type);
    List<Notification> getNotificationsByStatus(String status);
    List<Notification> getNotificationsByPriority(String priority);
    List<Notification> getNotificationsByDateRange(LocalDateTime start, LocalDateTime end);
    List<Notification> getUnreadNotificationsByUserId(Long userId);
    Notification markAsRead(Long notificationId);
    void deleteNotification(Long id);
    long getUnreadCount(Long userId);
    Notification sendEmailNotification(Long userId, String title, String message, String email);
    Notification sendSMSNotification(Long userId, String title, String message, String phoneNumber);
    Notification sendPushNotification(Long userId, String title, String message);
    Notification sendInAppNotification(Long userId, String title, String message);
}
