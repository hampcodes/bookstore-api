package com.bookstore.controller;

import com.bookstore.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/author/{authorId}/views-by-category")
    public ResponseEntity<List<Object[]>> viewsByCategoryForAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(statsService.viewsByCategoryForAuthor(authorId));
    }

    @GetMapping("/author/{authorId}/likes-by-category")
    public ResponseEntity<List<Object[]>> likesByCategoryForAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(statsService.likesByCategoryForAuthor(authorId));
    }

    @GetMapping("/top-books")
    public ResponseEntity<List<Object[]>> topBooksByViews() {
        return ResponseEntity.ok(statsService.topBooksByViews());
    }
}
