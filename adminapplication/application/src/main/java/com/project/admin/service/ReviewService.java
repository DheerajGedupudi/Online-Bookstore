package com.project.admin.service;

import com.project.admin.entity.Review;
import com.project.admin.dao.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findByBookId(Long bookId) {
        return reviewRepository.findByBook_BookId(bookId);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }
}
