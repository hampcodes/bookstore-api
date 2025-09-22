package com.bookstore.repository;

import com.bookstore.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

    // JPQL: Buscar un lector por el id de usuario
    @Query("SELECT r FROM Reader r WHERE r.user.id = :userId")
    Optional<Reader> findByUserId(@Param("userId") Long userId);

    // JPQL: Buscar lectores cuyo nombre contenga un texto (case insensitive)
    @Query("SELECT r FROM Reader r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Reader> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT COUNT(r) > 0 FROM Reader r WHERE r.user.id = :userId")
    boolean existsByUserId(@Param("userId") Long userId);
}
