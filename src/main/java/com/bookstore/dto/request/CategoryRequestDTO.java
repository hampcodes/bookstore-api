package com.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CategoryRequestDTO(

        @NotBlank(message = "El nombre de la categoría es obligatorio")
        @Size(max = 255, message = "El nombre no debe exceder los 255 caracteres")
        String name
) {}
