package com.bookstore.controller;

import com.bookstore.dto.response.UserProfileResponseDTO;
import com.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserProfileResponseDTO> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getProfile(userId));
    }

    @PutMapping("/{userId}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable Long userId,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        userService.changePassword(userId, currentPassword, newPassword);
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }
}
