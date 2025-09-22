package com.bookstore.service;

import com.bookstore.dto.request.CategoryRequestDTO;
import com.bookstore.dto.response.CategoryResponseDTO;
import com.bookstore.exception.BusinessRuleException;
import com.bookstore.model.Category;
import com.bookstore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    // Crear categoría
    @Transactional
    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        if (categoryRepository.findByName(dto.name()).isPresent()) {
            throw new BusinessRuleException("La categoría ya existe.");
        }

        Category category = new Category();
        category.setName(dto.name());

        Category saved = categoryRepository.save(category);
        return toDto(saved);
    }

    // Listar todas las categorías
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Conversión
    private CategoryResponseDTO toDto(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName()
        );
    }
}
