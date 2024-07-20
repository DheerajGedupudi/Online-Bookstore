package com.project.admin.service;

import com.project.admin.dao.BookOrderRepository;
import com.project.admin.entity.BookOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class BookOrderService {

    private static final Logger logger = Logger.getLogger(BookOrderService.class.getName());

    @Autowired
    private BookOrderRepository bookOrderRepository;

    public List<BookOrder> findAll() {
        List<BookOrder> orders = bookOrderRepository.findAll();
        logger.info("Fetched orders: " + orders);
        return orders;
    }

    public BookOrder findById(Long orderId) {
        Optional<BookOrder> order = bookOrderRepository.findById(orderId);
        return order.orElse(null);
    }

    public void cancelOrder(Long orderId) {
        Optional<BookOrder> orderOpt = bookOrderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            BookOrder order = orderOpt.get();
            if ("Completed".equals(order.getStatus())) {
                order.setStatus("Cancelled");
                bookOrderRepository.save(order);
                logger.info("Cancelled order: " + order);
            }
        } else {
            logger.warning("Order not found: " + orderId);
        }
    }
}
