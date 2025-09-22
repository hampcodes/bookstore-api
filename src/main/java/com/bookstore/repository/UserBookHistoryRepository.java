package com.bookstore.repository;

import com.bookstore.model.UserBookHistory;
import com.bookstore.model.enums.InteractionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBookHistoryRepository extends JpaRepository<UserBookHistory, Long> {

    // Historial de un lector
    @Query("SELECT h FROM UserBookHistory h WHERE h.reader.id = :readerId ORDER BY h.timestamp DESC")
    List<UserBookHistory> findByReaderId(@Param("readerId") Long readerId);

    // Historial de un libro
    @Query("SELECT h FROM UserBookHistory h WHERE h.book.id = :bookId ORDER BY h.timestamp DESC")
    List<UserBookHistory> findByBookId(@Param("bookId") Long bookId);

    // Contar interacciones por libro y tipo
    @Query("SELECT COUNT(h) FROM UserBookHistory h WHERE h.book.id = :bookId AND h.action = :action")
    long countByBookIdAndAction(@Param("bookId") Long bookId, @Param("action") InteractionType action);

    // Verificar si un lector ya hizo cierta acción sobre un libro
    @Query("SELECT COUNT(h) > 0 FROM UserBookHistory h " +
            "WHERE h.reader.id = :readerId AND h.book.id = :bookId AND h.action = :action")
    boolean existsByReaderAndBookAndAction(@Param("readerId") Long readerId,
                                           @Param("bookId") Long bookId,
                                           @Param("action") InteractionType action);

    // Categorías más vistas por un lector (para recomendaciones personalizadas)
    @Query("""
           SELECT c.name
           FROM UserBookHistory h
           JOIN h.book b
           JOIN b.category c
           WHERE h.reader.id = :readerId
             AND h.action = com.bookstore.model.enums.InteractionType.VIEW
           GROUP BY c.name
           ORDER BY COUNT(h) DESC
           """)
    List<String> findTopCategoriesByReader(@Param("readerId") Long readerId);

    // Libros más vistos (para recomendaciones globales o fallback)
    @Query("""
           SELECT b.id, b.title, b.price, c.name, a.name, COUNT(h) as views
           FROM UserBookHistory h
           JOIN h.book b
           JOIN b.category c
           JOIN b.author a
           WHERE h.action = com.bookstore.model.enums.InteractionType.VIEW
           GROUP BY b.id, b.title, b.price, c.name, a.name
           ORDER BY views DESC
           """)
    List<Object[]> findMostViewedBooks();

    // 🔹 Vistas por categoría en los libros de un autor
    @Query("""
           SELECT c.name, COUNT(h) as views
           FROM UserBookHistory h
           JOIN h.book b
           JOIN b.category c
           WHERE b.author.id = :authorId
             AND h.action = com.bookstore.model.enums.InteractionType.VIEW
           GROUP BY c.name
           ORDER BY views DESC
           """)
    List<Object[]> countViewsByCategoryForAuthor(@Param("authorId") Long authorId);

    // 🔹 Likes por categoría en los libros de un autor
    @Query("""
           SELECT c.name, COUNT(h) as likes
           FROM UserBookHistory h
           JOIN h.book b
           JOIN b.category c
           WHERE b.author.id = :authorId
             AND h.action = com.bookstore.model.enums.InteractionType.LIKE
           GROUP BY c.name
           ORDER BY likes DESC
           """)
    List<Object[]> countLikesByCategoryForAuthor(@Param("authorId") Long authorId);

    // 🔹 Top libros por vistas (ranking global)
    @Query("""
           SELECT b.title, COUNT(h) as views
           FROM UserBookHistory h
           JOIN h.book b
           WHERE h.action = com.bookstore.model.enums.InteractionType.VIEW
           GROUP BY b.title
           ORDER BY views DESC
           """)
    List<Object[]> countViewsByBook();
}
