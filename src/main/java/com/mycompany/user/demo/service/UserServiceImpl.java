package com.mycompany.user.demo.service;

import com.mycompany.user.demo.model.UserProfile;
import com.mycompany.user.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserProfile getUserProfile(int userId) {
        return userRepository.findById(userId)
                .orElseGet(() -> new UserProfile(userId, "Guest User", "BASIC", "unknown"));
    }
}
