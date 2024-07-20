package com.project.user.service;

import com.project.user.dao.BookRepository;
import com.project.user.dao.CartItemRepository;
import com.project.user.dao.CartRepository;
import com.project.user.entity.Book;
import com.project.user.entity.Cart;
import com.project.user.entity.CartItem;
import com.project.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<CartItem> findCartItemsByUser(User user) {
        Cart cart = cartRepository.findByUser(user);
        if (cart != null) {
            return cartItemRepository.findByCart(cart);
        }
        return List.of(); // Return an empty list if no cart is found
    }

    public void updateCartItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void addToCart(User user, Long bookId) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setTotalAmount(0.0);
            cartRepository.save(cart);
        }

        Book book = bookRepository.findById(bookId).orElseThrow();
        CartItem existingCartItem = cartItemRepository.findByCartAndBook(cart, book);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setBook(book);
            cartItem.setQuantity(1);
            cartItem.setPrice(book.getPrice());
            cartItem.setCart(cart);
            cartItemRepository.save(cartItem);
        }

        // Update cart total amount
        double newTotal = cart.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        cart.setTotalAmount(newTotal);
        cartRepository.save(cart);
    }

    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Transactional
    public void clearCart(User user) {
        Cart cart = cartRepository.findByUser(user);
        if (cart != null) {
            // Iterate through each cart item and delete them by cartItemId
            List<CartItem> cartItems = cart.getItems();
            for (CartItem cartItem : cartItems) {
                cartItemRepository.deleteById(cartItem.getCartItemId());
            }
            // Clear the list of cart items
            cart.getItems().clear();
            cart.setTotalAmount(0.0);
            cartRepository.save(cart);
        }
    }
}
