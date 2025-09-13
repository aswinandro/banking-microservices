package com.banking.notification.service;

import com.banking.notification.model.Notification;
import com.banking.notification.model.NotificationType;
import com.banking.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        notification.setStatus("PENDING");
        return notificationRepository.save(notification);
    }

    @Override
    public Notification sendNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));

        if (!"PENDING".equals(notification.getStatus())) {
            throw new RuntimeException("Notification already processed");
        }

        try {
            // Simulate sending notification based on type
            switch (notification.getType()) {
                case EMAIL:
                    simulateEmailSending(notification);
                    break;
                case SMS:
                    simulateSMSSending(notification);
                    break;
                case PUSH:
                    simulatePushNotificationSending(notification);
                    break;
                case IN_APP:
                    // In-app notifications are immediately available
                    break;
                default:
                    throw new RuntimeException("Unsupported notification type");
            }
            notification.markAsSent();
            System.out.println("Sent " + notification.getType() + " notification: " + notification.getTitle());
        } catch (Exception e) {
            notification.markAsFailed();
            System.err.println("Failed to send notification: " + e.getMessage());
        } finally {
            notificationRepository.save(notification);
        }

        return notification;
    }

    @Override
    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getNotificationsByType(NotificationType type) {
        return notificationRepository.findByType(type);
    }

    @Override
    public List<Notification> getNotificationsByStatus(String status) {
        return notificationRepository.findByStatus(status);
    }

    @Override
    public List<Notification> getNotificationsByPriority(String priority) {
        return notificationRepository.findByPriority(priority);
    }

    @Override
    public List<Notification> getNotificationsByDateRange(LocalDateTime start, LocalDateTime end) {
        return notificationRepository.findByDateRange(start, end);
    }

    @Override
    public List<Notification> getUnreadNotificationsByUserId(Long userId) {
        return notificationRepository.findUnreadByUserId(userId);
    }

    @Override
    public Notification markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));

        notification.markAsRead();
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }

    @Override
    public long getUnreadCount(Long userId) {
        return notificationRepository.findUnreadByUserId(userId).size();
    }

    @Override
    public Notification sendEmailNotification(Long userId, String title, String message, String email) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setType(NotificationType.EMAIL);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setRecipient(email);

        Notification created = createNotification(notification);
        return sendNotification(created.getId());
    }

    @Override
    public Notification sendSMSNotification(Long userId, String title, String message, String phoneNumber) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setType(NotificationType.SMS);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setRecipient(phoneNumber);

        Notification created = createNotification(notification);
        return sendNotification(created.getId());
    }

    @Override
    public Notification sendPushNotification(Long userId, String title, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setType(NotificationType.PUSH);
        notification.setTitle(title);
        notification.setMessage(message);

        Notification created = createNotification(notification);
        return sendNotification(created.getId());
    }

    @Override
    public Notification sendInAppNotification(Long userId, String title, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setType(NotificationType.IN_APP);
        notification.setTitle(title);
        notification.setMessage(message);

        Notification created = createNotification(notification);
        return sendNotification(created.getId());
    }

    // =================== Helper methods ===================
    private void simulateEmailSending(Notification notification) {
        System.out.println("Simulating sending EMAIL to " + notification.getRecipient());
    }

    private void simulateSMSSending(Notification notification) {
        System.out.println("Simulating sending SMS to " + notification.getRecipient());
    }

    private void simulatePushNotificationSending(Notification notification) {
        System.out.println("Simulating sending PUSH notification to user " + notification.getUserId());
    }

} // end of class
