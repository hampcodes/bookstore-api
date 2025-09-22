package com.bookstore.service;

import com.bookstore.dto.request.BookRequestDTO;
import com.bookstore.dto.response.BookResponseDTO;
import com.bookstore.exception.BusinessRuleException;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.repository.AuthorRepository;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public BookResponseDTO create(BookRequestDTO dto) {
        var author = authorRepository.findById(dto.authorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado"));
        var category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        // 🚨 Regla: no permitir libros duplicados (título + autor)
        if (bookRepository.findByTitleAndAuthor(dto.title(), dto.authorId()).isPresent()) {
            throw new BusinessRuleException("El autor ya tiene un libro con ese título");
        }

        var book = Book.builder()
                .title(dto.title())
                .price(dto.price())
                .author(author)
                .category(category)
                .build();

        return toDto(bookRepository.save(book));
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookResponseDTO findById(Long id) {
        return bookRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Libro no encontrado");
        }
        bookRepository.deleteById(id);
    }

    private BookResponseDTO toDto(Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .price(book.getPrice())
                .categoryName(book.getCategory().getName())
                .authorName(book.getAuthor().getName())
                .build();
    }
}
