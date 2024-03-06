package com.samoonpride.backend.dto.request;

public record ChangePasswordRequest(
    String username,
    String currentPassword,
    String newPassword
) { }
