package com.bookstore.service;

import com.bookstore.dto.response.BookResponseDTO;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.UserBookHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookRecommendationService {

    private final UserBookHistoryRepository historyRepository;
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<BookResponseDTO> recommendBooksForReader(Long readerId) {
        // 1) Categorías top del lector
        var topCategories = historyRepository.findTopCategoriesByReader(readerId);

        // 2) Si no hay historial → libros más vistos globales
        if (topCategories == null || topCategories.isEmpty()) {
            return historyRepository.findMostViewedBooks()
                    .stream()
                    .map(this::toResponseFromHistory)
                    .toList();
        }

        // 3) Si hay historial → libros de esas categorías
        return bookRepository.findBooksByCategoryNames(topCategories)
                .stream()
                .map(this::toResponseFromBooks)
                .toList();
    }

    // Conversión de Object[] -> DTO (caso historial)
    private BookResponseDTO toResponseFromHistory(Object[] row) {
        return BookResponseDTO.builder()
                .id(((Number) row[0]).longValue())
                .title((String) row[1])
                .price(row[2] != null ? new BigDecimal(row[2].toString()) : BigDecimal.ZERO)
                .categoryName((String) row[3])
                .authorName((String) row[4])
                .build();
    }


    // Conversión de Object[] -> DTO (caso libros por categorías)
    private BookResponseDTO toResponseFromBooks(Object[] row) {
        return BookResponseDTO.builder()
                .id(((Number) row[0]).longValue())
                .title((String) row[1])
                .price(row[2] != null ? new BigDecimal(row[2].toString()) : BigDecimal.ZERO)
                .categoryName((String) row[3])
                .authorName((String) row[4])
                .build();
    }
}
