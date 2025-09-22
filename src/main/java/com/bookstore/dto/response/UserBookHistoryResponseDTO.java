package com.bookstore.dto.response;

import com.bookstore.model.enums.InteractionType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserBookHistoryResponseDTO(
        Long id,
        Long readerId,
        String readerName,
        Long bookId,
        String bookTitle,
        InteractionType action,
        LocalDateTime timestamp
) {}
