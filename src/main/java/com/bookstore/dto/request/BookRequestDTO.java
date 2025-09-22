package com.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BookRequestDTO(

        @NotBlank(message = "El título es obligatorio")
        @Size(max = 255, message = "El título no debe exceder los 255 caracteres")
        String title,

        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor a 0")
        BigDecimal price,

        @NotNull(message = "La categoría es obligatoria")
        Long categoryId,

        @NotNull(message = "El autor es obligatorio")
        Long authorId
) {}
