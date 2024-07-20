package com.project.admin.dao;

import com.project.admin.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByGenre_GenreId(Long genreId);
    List<Book> findAllByOrderByStockAsc();
    List<Book> findAllByOrderByStockDesc();
    List<Book> findByTitleContainingIgnoreCaseAndGenre_GenreId(String title, Long genreId);
    long countByGenre_GenreId(Long genreId); // Ensure this method is included
    List<Book> findAllByOrderByTitleAsc();
    List<Book> findAllByOrderByTitleDesc();
}
