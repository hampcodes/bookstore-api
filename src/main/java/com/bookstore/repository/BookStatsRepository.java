package com.bookstore.repository;

import com.bookstore.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface BookStatsRepository extends JpaRepository<Book, Long> {

    // Libros más vistos
    @Query("SELECT b.id, b.title, b.price, c.name, a.name, COUNT(h) as views " +
            "FROM UserBookHistory h " +
            "JOIN h.book b " +
            "JOIN b.category c " +
            "JOIN b.author a " +
            "WHERE h.action = com.bookstore.model.enums.InteractionType.VIEW " +
            "GROUP BY b.id, b.title, b.price, c.name, a.name " +
            "ORDER BY views DESC")
    List<Object[]> findMostViewedBooks();

    // Libros por categorías
    @Query("SELECT b.id, b.title, b.price, c.name, a.name " +
            "FROM Book b " +
            "JOIN b.category c " +
            "JOIN b.author a " +
            "WHERE c.name IN :categories")
    List<Object[]> findBooksByCategoryNames(@Param("categories") List<String> categories);
}
