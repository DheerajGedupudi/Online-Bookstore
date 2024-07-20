package com.project.user.service;

import com.project.user.dao.BookRepository;
import com.project.user.entity.Book;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> filterByGenre(Long genreId) {
        return bookRepository.findByGenre_GenreId(genreId);
    }

    public List<Book> sortByStockAsc() {
        return bookRepository.findAllByOrderByStockAsc();
    }

    public List<Book> sortByStockDesc() {
        return bookRepository.findAllByOrderByStockDesc();
    }

    public List<Book> sortByTitleAsc() {
        return bookRepository.findAllByOrderByTitleAsc();
    }

    public List<Book> sortByTitleDesc() {
        return bookRepository.findAllByOrderByTitleDesc();
    }

    public List<Book> searchAndFilter(String title, Long genreId) {
        return bookRepository.findByTitleContainingIgnoreCaseAndGenre_GenreId(title, genreId);
    }
}
