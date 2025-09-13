package com.banking.notification.config;

import com.banking.notification.model.Notification;
import com.banking.notification.model.NotificationType;
import com.banking.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize dummy notifications
        Notification notif1 = new Notification(1L, 1L, NotificationType.EMAIL, 
                                             "Welcome to Online Banking", 
                                             "Your account has been successfully created. Welcome to our banking service!",
                                             "john.doe@email.com");
        notif1.setStatus("SENT");

        Notification notif2 = new Notification(2L, 1L, NotificationType.SMS, 
                                             "Transaction Alert", 
                                             "Your account was debited $500.00 for transfer to savings account.",
                                             "+1234567890");
        notif2.setStatus("SENT");

        Notification notif3 = new Notification(3L, 2L, NotificationType.PUSH, 
                                             "Payment Received", 
                                             "You have received a payment of $750.00 in your checking account.",
                                             null);
        notif3.setStatus("SENT");

        Notification notif4 = new Notification(4L, 3L, NotificationType.IN_APP, 
                                             "Low Balance Warning", 
                                             "Your checking account balance is below $500. Consider transferring funds.",
                                             null);
        notif4.setStatus("SENT");
        notif4.setPriority("HIGH");

        Notification notif5 = new Notification(5L, 4L, NotificationType.EMAIL, 
                                             "Monthly Statement", 
                                             "Your monthly account statement is now available for download.",
                                             "alice.brown@email.com");
        notif5.setStatus("PENDING");

        Notification notif6 = new Notification(6L, 5L, NotificationType.SMS, 
                                             "Security Alert", 
                                             "New device login detected. If this wasn't you, please contact us immediately.",
                                             "+1999888777");
        notif6.setStatus("SENT");
        notif6.setPriority("URGENT");

        Notification notif7 = new Notification(7L, 2L, NotificationType.IN_APP, 
                                             "Card Expires Soon", 
                                             "Your debit card expires in 30 days. Your new card will arrive soon.",
                                             null);
        notif7.setStatus("SENT");

        Notification notif8 = new Notification(8L, 1L, NotificationType.PUSH, 
                                             "Deposit Confirmation", 
                                             "Your deposit of $1,000.00 has been successfully processed.",
                                             null);
        notif8.setStatus("SENT");

        notificationRepository.save(notif1);
        notificationRepository.save(notif2);
        notificationRepository.save(notif3);
        notificationRepository.save(notif4);
        notificationRepository.save(notif5);
        notificationRepository.save(notif6);
        notificationRepository.save(notif7);
        notificationRepository.save(notif8);

        System.out.println("Notification service initialized with " + notificationRepository.count() + " notifications");
    }
}