package com.banking.user.config;

import com.banking.user.model.User;
import com.banking.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize dummy users
        User user1 = new User(1L, "John", "Doe", "john.doe@email.com", "+1234567890", "123 Main St, New York, NY");
        User user2 = new User(2L, "Jane", "Smith", "jane.smith@email.com", "+1987654321", "456 Oak Ave, Los Angeles, CA");
        User user3 = new User(3L, "Bob", "Johnson", "bob.johnson@email.com", "+1122334455", "789 Pine Rd, Chicago, IL");
        User user4 = new User(4L, "Alice", "Brown", "alice.brown@email.com", "+1555666777", "321 Elm St, Houston, TX");
        User user5 = new User(5L, "Charlie", "Wilson", "charlie.wilson@email.com", "+1999888777", "654 Maple Dr, Phoenix, AZ");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);

        System.out.println("User service initialized with " + userRepository.count() + " users");
    }
}