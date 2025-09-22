package com.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ReaderRequestDTO(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 255, message = "El nombre no debe exceder los 255 caracteres")
        String name,

        @Size(max = 500, message = "Las preferencias no deben exceder los 500 caracteres")
        String preferences,

        @NotNull(message = "El id de usuario es obligatorio")
        Long userId
) {}
