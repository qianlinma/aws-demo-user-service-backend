package com.mycompany.user.demo.controller;

public record ApiErrorResponse(
        String error,
        String message
) {
}
