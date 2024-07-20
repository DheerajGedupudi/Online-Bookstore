package com.project.admin.controller;

import com.project.admin.entity.BookOrder;
import com.project.admin.service.BookOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
public class BookOrderController {

    @Autowired
    private BookOrderService bookOrderService;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", bookOrderService.findAll());
        return "orders";
    }

    @GetMapping("/{id}")
    public String getOrderDetails(@PathVariable("id") Long orderId, Model model) {
        BookOrder order = bookOrderService.findById(orderId);
        model.addAttribute("order", order);
        return "order-details";
    }

    @PostMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable("id") Long orderId) {
        bookOrderService.cancelOrder(orderId);
        return "redirect:/admin/orders";
    }
}
