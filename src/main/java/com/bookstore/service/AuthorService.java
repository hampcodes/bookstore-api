package com.bookstore.service;

import com.bookstore.dto.request.AuthorRequestDTO;
import com.bookstore.dto.response.AuthorResponseDTO;
import com.bookstore.exception.BusinessRuleException;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Author;
import com.bookstore.repository.AuthorRepository;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    public AuthorResponseDTO create(AuthorRequestDTO dto) {
        var user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Regla: un usuario no puede tener más de un perfil de autor
        if (authorRepository.existsByUserId(dto.userId())) {
            throw new BusinessRuleException("El usuario ya tiene un perfil de autor");
        }

        var author = Author.builder()
                .name(dto.name().trim())
                .bio(dto.bio())
                .website(dto.website())
                .user(user)
                .build();

        return toResponse(authorRepository.save(author));
    }

    @Transactional(readOnly = true)
    public List<AuthorResponseDTO> findAll() {
        return authorRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AuthorResponseDTO findById(Long id) {
        return authorRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado"));
    }

    @Transactional
    public void delete(Long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado"));

        // Regla: no se puede eliminar si el autor tiene libros asociados
        if (!bookRepository.findByAuthorId(id).isEmpty()) {
            throw new BusinessRuleException("No se puede eliminar el autor porque tiene libros registrados");
        }

        authorRepository.delete(author);
    }

    private AuthorResponseDTO toResponse(Author author) {
        return AuthorResponseDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .bio(author.getBio())
                .website(author.getWebsite())
                .userId(author.getUser().getId())
                .build();
    }
}
