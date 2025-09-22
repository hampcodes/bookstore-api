package com.bookstore.dto.request;

import com.bookstore.model.enums.RoleType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RoleRequestDTO(

        @NotNull(message = "El tipo de rol es obligatorio")
        RoleType name
) {}
