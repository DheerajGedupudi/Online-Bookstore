package com.project.user.dao;

import com.project.user.entity.BookOrder;
import com.project.user.entity.Payment;
import com.project.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {
    BookOrder findByPayment(Payment payment);
    List<BookOrder> findByUser(User user);
    BookOrder findByOrderIdAndUser(Long orderId, User user);
}
