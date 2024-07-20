package com.project.user.service;

import com.project.user.dao.ReviewRepository;
import com.project.user.entity.Book;
import com.project.user.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findByBook(Book book) {
        return reviewRepository.findByBook(book);
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> findByBookId(Long bookId) {
        return reviewRepository.findByBook_BookId(bookId);
    }
}

