package com.project.user.controller;

import com.project.user.entity.Book;
import com.project.user.entity.Genre;
import com.project.user.entity.Review;
import com.project.user.entity.User;
import com.project.user.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class UserHomeController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private GenreService genreService;

    @GetMapping("/user/home")
    public String showUserHomePage(@AuthenticationPrincipal UserDetails loggedUser,
                                   @RequestParam(required = false) String searchTitle,
                                   @RequestParam(required = false) Long genreId,
                                   @RequestParam(required = false) String sortField,
                                   @RequestParam(required = false) String sortDirection,
                                   Model model) {
        User user = userService.findByEmail(loggedUser.getUsername());
        List<Book> books;
        List<Genre> genres = genreService.findAll();

        // Handle search, filter, and sort
        if (searchTitle != null && genreId != null) {
            books = bookService.searchAndFilter(searchTitle, genreId);
        } else if (searchTitle != null) {
            books = bookService.searchByTitle(searchTitle);
        } else if (genreId != null) {
            books = bookService.filterByGenre(genreId);
        } else if ("title".equals(sortField) && "asc".equals(sortDirection)) {
            books = bookService.sortByTitleAsc();
        } else if ("title".equals(sortField) && "desc".equals(sortDirection)) {
            books = bookService.sortByTitleDesc();
        } else if ("stock".equals(sortField) && "asc".equals(sortDirection)) {
            books = bookService.sortByStockAsc();
        } else if ("stock".equals(sortField) && "desc".equals(sortDirection)) {
            books = bookService.sortByStockDesc();
        } else {
            books = bookService.findAll();
        }

        model.addAttribute("user", user);
        model.addAttribute("books", books);
        model.addAttribute("genres", genres);
        return "user-home";
    }

    @GetMapping("/view")
    public String viewBookDetails(@RequestParam Long bookId, @AuthenticationPrincipal UserDetails loggedUser, Model model) {
        Book book = bookService.findById(bookId).orElseThrow();
        User user = userService.findByEmail(loggedUser.getUsername());
        List<Review> reviews = reviewService.findByBookId(bookId);
        boolean hasOrdered = orderItemService.hasUserOrderedBook(user, book);

        model.addAttribute("book", book);
        model.addAttribute("reviews", reviews);
        model.addAttribute("hasOrdered", hasOrdered);
        return "view-book";
    }

    @GetMapping("/addReview")
    public String showAddReviewPage(@RequestParam Long bookId, @AuthenticationPrincipal UserDetails loggedUser, Model model) {
        Book book = bookService.findById(bookId).orElseThrow();
        User user = userService.findByEmail(loggedUser.getUsername());
        boolean hasOrdered = orderItemService.hasUserOrderedBook(user, book);

        if (!hasOrdered) {
            return "redirect:/view?bookId=" + bookId;
        }

        model.addAttribute("book", book);
        model.addAttribute("user", user);
        model.addAttribute("review", new Review());
        return "add-review";
    }

    @PostMapping("/addReview")
    public String addReview(@RequestParam Long bookId, @RequestParam String comment, @RequestParam int rating, @AuthenticationPrincipal UserDetails loggedUser) {
        Book book = bookService.findById(bookId).orElseThrow();
        User user = userService.findByEmail(loggedUser.getUsername());
        Review review = new Review();
        review.setBook(book);
        review.setUser(user);
        review.setComment(comment);
        review.setRating(rating);
        review.setReviewDate(new Date()); // Set review date to current date
        reviewService.save(review);
        return "redirect:/view?bookId=" + bookId;
    }

    @GetMapping("/settings")
    public String showSettingsPage(@AuthenticationPrincipal UserDetails loggedUser, Model model) {
        User user = userService.findByEmail(loggedUser.getUsername());
        model.addAttribute("user", user);
        return "settings";
    }

    @PostMapping("/settings")
    public String updateSettings(User user, @AuthenticationPrincipal UserDetails loggedUser) {
        User currentUser = userService.findByEmail(loggedUser.getUsername());
        currentUser.setName(user.getName());
        currentUser.setAddress(user.getAddress());
        currentUser.setPhone(user.getPhone());
        userService.save(currentUser);
        return "redirect:/user/home";
    }
}
