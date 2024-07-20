package com.project.user.service;

import com.project.user.dao.BookOrderRepository;
import com.project.user.dao.OrderItemRepository;
import com.project.user.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CheckoutService {

    @Autowired
    private BookOrderRepository bookOrderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private CartService cartService;

    @Transactional
    public void createOrder(User user, Payment payment, List<CartItem> cartItems) {
        // Create a new order
        BookOrder order = new BookOrder();
        order.setUser(user);
        order.setOrderDate(payment.getPaymentDate());
        order.setTotalAmount(payment.getAmount());
        order.setStatus("Completed");
        order.setPayment(payment);

        // Save the order first to generate an ID
        bookOrderRepository.save(order);

        // Process each cart item and create corresponding order items
        for (CartItem cartItem : cartItems) {
            Book book = cartItem.getBook();
            int orderedQuantity = cartItem.getQuantity();

            // Check if there is enough stock
            if (book.getStock() < orderedQuantity) {
                throw new IllegalArgumentException("Not enough stock for book: " + book.getTitle());
            }

            // Reduce the stock quantity
            book.setStock(book.getStock() - orderedQuantity);
            bookService.save(book);  // Use the save method in BookService

            // Create an OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(book);
            orderItem.setQuantity(orderedQuantity);
            orderItem.setPrice(cartItem.getPrice());

            // Save the order item in the database
            orderItemRepository.save(orderItem);
        }

        System.out.println(order);
    }


    @Transactional
    public void createOrderItems(User user, Payment payment) {
        // Get the user's cart
        Cart cart = cartService.getCartByUser(user);

        // Get the newly created order
        BookOrder order = bookOrderRepository.findByPayment(payment);

        // Create order items from cart items
        List<CartItem> cartItems = cart.getItems();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItemRepository.save(orderItem);
        }
    }

    @Transactional
    public void clearCart(User user) {
        cartService.clearCart(user);
    }
}
