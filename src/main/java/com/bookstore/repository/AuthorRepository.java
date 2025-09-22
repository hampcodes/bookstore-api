package com.bookstore.repository;

import com.bookstore.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // JPQL: Buscar autores cuyo nombre contenga un texto (case insensitive)
    @Query("SELECT a FROM Author a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Author> findByNameContainingIgnoreCase(@Param("name") String name);

    // Verifica si un usuario ya tiene un perfil de autor
    @Query("SELECT COUNT(a) > 0 FROM Author a WHERE a.user.id = :userId")
    boolean existsByUserId(@Param("userId") Long userId);
}
