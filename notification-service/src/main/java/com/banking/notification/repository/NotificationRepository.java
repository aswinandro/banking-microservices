package com.banking.notification.repository;

import com.banking.notification.model.Notification;
import com.banking.notification.model.NotificationType;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class NotificationRepository {
    private final Map<Long, Notification> notifications = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Notification save(Notification notification) {
        if (notification.getId() == null) {
            notification.setId(idGenerator.getAndIncrement());
        }
        notifications.put(notification.getId(), notification);
        return notification;
    }

    public Optional<Notification> findById(Long id) {
        return Optional.ofNullable(notifications.get(id));
    }

    public List<Notification> findAll() {
        return new ArrayList<>(notifications.values());
    }

    public List<Notification> findByUserId(Long userId) {
        return notifications.values().stream()
                .filter(notification -> notification.getUserId().equals(userId))
                .sorted((n1, n2) -> n2.getCreatedAt().compareTo(n1.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public List<Notification> findByType(NotificationType type) {
        return notifications.values().stream()
                .filter(notification -> notification.getType() == type)
                .collect(Collectors.toList());
    }

    public List<Notification> findByStatus(String status) {
        return notifications.values().stream()
                .filter(notification -> notification.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    public List<Notification> findByPriority(String priority) {
        return notifications.values().stream()
                .filter(notification -> notification.getPriority().equals(priority))
                .collect(Collectors.toList());
    }

    public List<Notification> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return notifications.values().stream()
                .filter(notification -> notification.getCreatedAt().isAfter(start) && 
                                      notification.getCreatedAt().isBefore(end))
                .collect(Collectors.toList());
    }

    public List<Notification> findUnreadByUserId(Long userId) {
        return notifications.values().stream()
                .filter(notification -> notification.getUserId().equals(userId) && 
                                      !"READ".equals(notification.getStatus()))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        notifications.remove(id);
    }

    public boolean existsById(Long id) {
        return notifications.containsKey(id);
    }

    public long count() {
        return notifications.size();
    }

    public long countByUserIdAndStatus(Long userId, String status) {
        return notifications.values().stream()
                .filter(notification -> notification.getUserId().equals(userId) && 
                                      notification.getStatus().equals(status))
                .count();
    }
}
