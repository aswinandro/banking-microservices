package com.banking.user.service;

import com.banking.user.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    Optional<User> getUserByEmail(String email);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}