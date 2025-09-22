package com.bookstore.controller;

import com.bookstore.dto.request.ReaderRequestDTO;
import com.bookstore.dto.response.ReaderResponseDTO;
import com.bookstore.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/readers")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;

    @PostMapping
    public ResponseEntity<ReaderResponseDTO> create(@Valid @RequestBody ReaderRequestDTO dto) {
        return ResponseEntity.ok(readerService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ReaderResponseDTO>> findAll() {
        return ResponseEntity.ok(readerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReaderResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(readerService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        readerService.delete(id);
        return ResponseEntity.ok("Lector eliminado correctamente");
    }
}
