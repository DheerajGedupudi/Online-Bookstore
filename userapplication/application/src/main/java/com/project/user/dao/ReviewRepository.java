package com.project.user.dao;

import com.project.user.entity.Book;
import com.project.user.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBook_BookId(Long bookId);

    List<Review> findByBook(Book book);
}
