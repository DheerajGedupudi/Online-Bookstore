package com.project.user.controller;

import com.project.user.entity.BookOrder;
import com.project.user.entity.User;
import com.project.user.service.OrderService;
import com.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String showOrdersPage(@AuthenticationPrincipal UserDetails loggedUser, Model model) {
        User user = userService.findByEmail(loggedUser.getUsername());
        List<BookOrder> orders = orderService.findOrdersByUser(user);
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/orders/{orderId}")
    public String showOrderDetails(@PathVariable Long orderId, Model model, @AuthenticationPrincipal UserDetails loggedUser) {
        User user = userService.findByEmail(loggedUser.getUsername());
        BookOrder order = orderService.findOrderByIdAndUser(orderId, user);
        model.addAttribute("order", order);
        return "order-details";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId, @AuthenticationPrincipal UserDetails loggedUser) {
        User user = userService.findByEmail(loggedUser.getUsername());
        orderService.cancelOrder(orderId, user);
        return "redirect:/orders";
    }
}
