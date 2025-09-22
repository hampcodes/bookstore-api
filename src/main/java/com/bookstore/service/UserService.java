package com.bookstore.service;

import com.bookstore.dto.response.UserProfileResponseDTO;
import com.bookstore.exception.BusinessRuleException;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.User;
import com.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //private final EmailService emailService; // implementar envío de correos

    @Transactional(readOnly = true)
    public UserProfileResponseDTO getProfile(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return new UserProfileResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getRole().getName().name()
        );
    }

    @Transactional
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BusinessRuleException("La contraseña actual no es válida");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Enviar correo de confirmación
        //emailService.sendPasswordChangedEmail(user.getEmail());
    }
}
