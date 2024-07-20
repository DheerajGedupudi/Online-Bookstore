package com.project.user.dao;

import com.project.user.entity.Book;
import com.project.user.entity.Cart;
import com.project.user.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
    CartItem findByCartAndBook(Cart cart, Book book);
}
