package com.bookstore.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NotificationResponseDTO(
        Long id,
        Long userId,
        String userEmail,
        String message,
        boolean read,
        LocalDateTime createdAt
) {}
