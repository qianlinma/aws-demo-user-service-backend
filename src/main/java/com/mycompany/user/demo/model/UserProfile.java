package com.mycompany.user.demo.model;

public record UserProfile(
        int id,
        String name,
        String membershipLevel,
        String region
) {
}
