package com.mycompany.user.demo.controller;

import com.mycompany.user.demo.model.UserProfile;
import com.mycompany.user.demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserProfile getUser(@PathVariable int userId) {
        return userService.getUserProfile(userId);
    }
}
