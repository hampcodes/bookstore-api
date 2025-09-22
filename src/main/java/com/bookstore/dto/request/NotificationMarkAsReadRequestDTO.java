package com.bookstore.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record NotificationMarkAsReadRequestDTO(

        @NotNull(message = "El id de la notificación es obligatorio")
        Long notificationId
) {}
