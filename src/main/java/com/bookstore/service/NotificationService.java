package com.bookstore.service;

import com.bookstore.dto.response.NotificationResponseDTO;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Notification;
import com.bookstore.model.User;
import com.bookstore.repository.NotificationRepository;
import com.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Transactional
    public NotificationResponseDTO create(Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Notification notification = Notification.builder()
                .user(user)
                .message(message)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();

        return toResponse(notificationRepository.save(notification));
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> getNotifications(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void markAsRead(Long notificationId) {
        var notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    private NotificationResponseDTO toResponse(Notification n) {
        return NotificationResponseDTO.builder()
                .id(n.getId())
                .message(n.getMessage())
                .read(n.isRead())
                .createdAt(n.getCreatedAt())
                .userId(n.getUser().getId())
                .build();
    }
}
