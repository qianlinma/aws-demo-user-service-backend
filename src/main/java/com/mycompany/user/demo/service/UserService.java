package com.mycompany.user.demo.service;

import com.mycompany.user.demo.model.UserProfile;
import com.mycompany.user.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserProfile getUserProfile(int userId) {
        return userRepository.findById(userId)
                .orElseGet(() -> new UserProfile(userId, "Guest User", "BASIC", "unknown"));
    }
}
