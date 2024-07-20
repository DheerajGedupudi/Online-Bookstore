package com.project.admin.controller;

import com.project.admin.entity.Book;
import com.project.admin.entity.Genre;
import com.project.admin.entity.Review;
import com.project.admin.service.BookService;
import com.project.admin.service.GenreService;
import com.project.admin.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public String listBooks(Model model,
                            @RequestParam(required = false) String search,
                            @RequestParam(required = false) Long genre,
                            @RequestParam(required = false) String sort) {

        List<Book> books = bookService.findBooks(search, genre, sort);
        List<Genre> genres = genreService.findAll();

        model.addAttribute("books", books);
        model.addAttribute("genres", genres);
        model.addAttribute("search", search);
        model.addAttribute("genre", genre);
        model.addAttribute("sort", sort);

        return "books";
    }

    @GetMapping("/view/{id}")
    public String viewBook(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findById(id);
        List<Review> reviews = reviewService.findByBookId(id);
        model.addAttribute("book", book);
        model.addAttribute("reviews", reviews);
        return "view-book";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("genres", genreService.findAll());
        return "edit-book";
    }

    @PostMapping("/edit")
    public String updateBook(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/admin/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return "redirect:/admin/books";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam("title") String title, Model model) {
        List<Book> books = bookService.findByTitleContainingIgnoreCase(title);
        model.addAttribute("books", books);
        model.addAttribute("genres", genreService.findAll());
        return "books";
    }

    @GetMapping("/filter")
    public String filterBooks(@RequestParam("genreId") Long genreId, Model model) {
        if (genreId == null) {
            return "redirect:/admin/books";
        }
        List<Book> books = bookService.findByGenreId(genreId);
        model.addAttribute("books", books);
        model.addAttribute("genres", genreService.findAll());
        return "books";
    }


    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("genres", genreService.findAll());
        return "add-book";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/admin/books";
    }

    @GetMapping("/sort")
    public String sortBooks(@RequestParam("sortField") String sortField, @RequestParam("sortDirection") String sortDirection, Model model) {
        List<Book> books;
        if ("stock".equals(sortField) && "asc".equals(sortDirection)) {
            books = bookService.findAllByOrderByStockAsc();
        } else if ("stock".equals(sortField) && "desc".equals(sortDirection)) {
            books = bookService.findAllByOrderByStockDesc();
        } else if ("title".equals(sortField) && "asc".equals(sortDirection)) {
            books = bookService.findAllByOrderByTitleAsc();
        } else {
            books = bookService.findAllByOrderByTitleDesc();
        }
        model.addAttribute("books", books);
        model.addAttribute("genres", genreService.findAll());
        return "books";
    }
}
