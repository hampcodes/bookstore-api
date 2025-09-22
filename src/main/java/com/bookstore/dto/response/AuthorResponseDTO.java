package com.bookstore.dto.response;

import lombok.Builder;

@Builder
public record AuthorResponseDTO(
        Long id,
        String name,
        String bio,
        String website,
        Long userId,
        String userEmail
) {}
