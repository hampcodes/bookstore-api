package com.bookstore.controller;

import com.bookstore.dto.response.BookResponseDTO;
import com.bookstore.service.BookRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final BookRecommendationService recommendationService;

    @GetMapping("/reader/{readerId}")
    public ResponseEntity<List<BookResponseDTO>> recommendBooksForReader(@PathVariable Long readerId) {
        return ResponseEntity.ok(recommendationService.recommendBooksForReader(readerId));
    }
}
