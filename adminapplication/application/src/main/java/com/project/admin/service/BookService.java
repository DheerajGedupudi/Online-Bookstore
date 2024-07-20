package com.project.admin.service;

import com.project.admin.dao.BookRepository;
import com.project.admin.entity.Book;
import com.project.admin.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findBooks(String search, Long genreId, String sort) {
        // Implement logic to search, filter, and sort books
        return bookRepository.findAll(); // Simplified for this example
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void save(Book book) {
        if (book.getBookId() != null && bookRepository.existsById(book.getBookId())) {
            // If the book already exists, update it
            Book existingBook = bookRepository.findById(book.getBookId()).orElse(null);
            if (existingBook != null) {
                existingBook.setTitle(book.getTitle());
                existingBook.setAuthor(book.getAuthor());
                existingBook.setPrice(book.getPrice());
                existingBook.setStock(book.getStock());
                existingBook.setThumbnailUrl(book.getThumbnailUrl());
                existingBook.setGenre(book.getGenre());
                bookRepository.save(existingBook);
                return;
            }
        }
        // Otherwise, save the new book
        bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findByTitleContainingIgnoreCase(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> findByGenreId(Long genreId) {
        return bookRepository.findByGenre_GenreId(genreId);
    }

    public List<Book> findAllByOrderByStockAsc() {
        return bookRepository.findAllByOrderByStockAsc();
    }

    public List<Book> findAllByOrderByStockDesc() {
        return bookRepository.findAllByOrderByStockDesc();
    }

    public List<Book> findAllByOrderByTitleAsc() {
        return bookRepository.findAllByOrderByTitleAsc();
    }

    public List<Book> findAllByOrderByTitleDesc() {
        return bookRepository.findAllByOrderByTitleDesc();
    }

    public Long countByGenreId(Long genreId) {
        return bookRepository.countByGenre_GenreId(genreId);
    }
}
