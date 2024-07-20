package com.project.user.service;

import com.project.user.dao.OrderItemRepository;
import com.project.user.entity.Book;
import com.project.user.entity.OrderItem;
import com.project.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public boolean hasUserOrderedBook(User user, Book book) {
        List<OrderItem> orderItems = orderItemRepository.findByBook(book);
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getOrder().getUser().getUserId().equals(user.getUserId())) {
                return true;
            }
        }
        return false;
    }
}