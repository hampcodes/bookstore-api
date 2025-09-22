package com.bookstore.dto.response;

import com.bookstore.model.enums.RoleType;
import lombok.Builder;

@Builder
public record RoleResponseDTO(
        Long id,
        RoleType name
) {}
