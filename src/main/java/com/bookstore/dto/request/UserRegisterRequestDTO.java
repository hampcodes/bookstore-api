package com.bookstore.dto.request;

import com.bookstore.model.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserRegisterRequestDTO(

        // Credenciales del usuario
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo debe tener un formato válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        String password,

        @NotNull(message = "El rol es obligatorio")
        RoleType roleType, // ✅ Ahora usamos enum en lugar de Long

        // Datos comunes del perfil
        @NotBlank(message = "El nombre es obligatorio")
        String name,

        // Datos específicos para Author
        String bio,
        String website,

        // Datos específicos para Reader
        String preferences
) {}
