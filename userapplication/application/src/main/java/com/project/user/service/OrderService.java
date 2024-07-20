package com.project.user.service;

import com.project.user.dao.BookOrderRepository;
import com.project.user.entity.BookOrder;
import com.project.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private BookOrderRepository bookOrderRepository;

    public List<BookOrder> findOrdersByUser(User user) {
        return bookOrderRepository.findByUser(user);
    }

    public BookOrder findOrderByIdAndUser(Long orderId, User user) {
        return bookOrderRepository.findByOrderIdAndUser(orderId, user);
    }

    public void cancelOrder(Long orderId, User user) {
        BookOrder order = bookOrderRepository.findByOrderIdAndUser(orderId, user);
        if (order != null && !order.getStatus().equals("Cancelled")) {
            order.setStatus("Cancelled");
            bookOrderRepository.save(order);
        }
    }
}
