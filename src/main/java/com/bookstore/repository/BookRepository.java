package com.bookstore.repository;

import com.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) = LOWER(:title) AND b.author.id = :authorId")
    Optional<Book> findByTitleAndAuthor(@Param("title") String title, @Param("authorId") Long authorId);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    java.util.List<Book> findByTitleContainingIgnoreCase(@Param("title") String title);

    // Obtener todos los libros de un autor
    @Query("SELECT b FROM Book b WHERE b.author.id = :authorId")
    List<Book> findByAuthorId(@Param("authorId") Long authorId);

    // Traer libros (id, título, precio, categoría, autor) por nombre de categorías
    @Query("""
           SELECT b.id, b.title, b.price, c.name, a.name
           FROM Book b
           JOIN b.category c
           JOIN b.author a
           WHERE c.name IN :categories
           ORDER BY b.title ASC
           """)
    List<Object[]> findBooksByCategoryNames(@Param("categories") List<String> categories);
}
