package com.bookstore.model;

import com.bookstore.model.enums.InteractionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_book_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBookHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El lector que realizó la acción
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "reader_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_history_reader")
    )
    private Reader reader;

    // El libro con el que interactuó
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "book_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_history_book")
    )
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InteractionType action; // VIEW o LIKE

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
