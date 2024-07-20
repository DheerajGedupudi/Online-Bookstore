package com.project.user.controller;

import com.project.user.entity.CartItem;
import com.project.user.entity.User;
import com.project.user.service.CartService;
import com.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/cart")
    public String showCartPage(@AuthenticationPrincipal UserDetails loggedUser, Model model) {
        User user = userService.findByEmail(loggedUser.getUsername());
        List<CartItem> cartItems = cartService.findCartItemsByUser(user);
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

    @PostMapping("/cart/update")
    public String updateCartItem(@RequestParam Long cartItemId, @RequestParam int quantity) {
        cartService.updateCartItemQuantity(cartItemId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeCartItem(@RequestParam Long cartItemId) {
        cartService.removeCartItem(cartItemId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long bookId, @AuthenticationPrincipal UserDetails loggedUser) {
        User user = userService.findByEmail(loggedUser.getUsername());
        cartService.addToCart(user, bookId);
        return "redirect:/user/home";
    }
}
