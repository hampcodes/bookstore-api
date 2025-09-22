package com.bookstore.dto.response;

import com.bookstore.model.enums.RoleType;
import lombok.Builder;

@Builder
public record LoginResponseDTO(
        Long userId,
        String email,
        RoleType role, // Enum tipado
        String token
) {}
