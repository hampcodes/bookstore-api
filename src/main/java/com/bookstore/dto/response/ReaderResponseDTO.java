package com.bookstore.dto.response;

import lombok.Builder;

@Builder
public record ReaderResponseDTO(
        Long id,
        String name,
        String preferences,
        Long userId,
        String userEmail
) {}
