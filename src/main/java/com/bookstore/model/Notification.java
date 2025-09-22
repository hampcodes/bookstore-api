package com.bookstore.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_notification_user")
    )
    private User user;

    @Column(nullable = false, length = 255)
    private String message;

    @Column(nullable = false)
    private boolean read = false; // si ya fue leída

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
