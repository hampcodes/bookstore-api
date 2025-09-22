package com.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AuthorRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 255, message = "El nombre no debe exceder los 255 caracteres")
        String name,

        @Size(max = 1000, message = "La biografía no debe exceder los 1000 caracteres")
        String bio,

        @Size(max = 255, message = "El sitio web no debe exceder los 255 caracteres")
        String website,

        @NotNull(message = "El id de usuario es obligatorio")
        Long userId
) {}
