package com.bookstore.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BookResponseDTO(
        Long id,
        String title,
        BigDecimal price,
        String categoryName,
        String authorName
) {}
