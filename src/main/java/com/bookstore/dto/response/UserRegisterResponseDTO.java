package com.bookstore.dto.response;

import lombok.Builder;

@Builder
public record UserRegisterResponseDTO(

        // Datos del usuario creado
        Long userId,
        String email,
        String roleName,

        // Datos del perfil creado
        Long profileId,
        String profileType, // "AUTHOR" o "READER"
        String name
) {}
