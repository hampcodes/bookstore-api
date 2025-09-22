package com.bookstore.dto.response;

import lombok.Builder;

@Builder
public record UserProfileResponseDTO(
        Long id,
        String email,
        String role
) {}
