package com.bookstore.service;

import com.bookstore.dto.request.ReaderRequestDTO;
import com.bookstore.dto.response.ReaderResponseDTO;
import com.bookstore.exception.BusinessRuleException;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Reader;
import com.bookstore.repository.ReaderRepository;
import com.bookstore.repository.UserBookHistoryRepository;
import com.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final UserRepository userRepository;
    private final UserBookHistoryRepository historyRepository;

    @Transactional
    public ReaderResponseDTO create(ReaderRequestDTO dto) {
        var user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Regla: un usuario no puede tener más de un perfil de lector
        if (readerRepository.existsByUserId(dto.userId())) {
            throw new BusinessRuleException("El usuario ya tiene un perfil de lector");
        }

        var reader = Reader.builder()
                .name(dto.name().trim())
                .preferences(dto.preferences())
                .user(user)
                .build();

        return toResponse(readerRepository.save(reader));
    }

    @Transactional(readOnly = true)
    public List<ReaderResponseDTO> findAll() {
        return readerRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReaderResponseDTO findById(Long id) {
        return readerRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Lector no encontrado"));
    }

    @Transactional
    public void delete(Long id) {
        var reader = readerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lector no encontrado"));

        // Regla: no se puede eliminar un lector con historial de interacciones
        if (!historyRepository.findByReaderId(id).isEmpty()) {
            throw new BusinessRuleException("No se puede eliminar el lector porque tiene historial de interacciones");
        }

        readerRepository.delete(reader);
    }

    private ReaderResponseDTO toResponse(Reader reader) {
        return ReaderResponseDTO.builder()
                .id(reader.getId())
                .name(reader.getName())
                .preferences(reader.getPreferences())
                .userId(reader.getUser().getId())
                .build();
    }
}
