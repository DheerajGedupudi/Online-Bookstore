package com.project.user.controller;

import com.project.user.entity.CartItem;
import com.project.user.entity.Payment;
import com.project.user.entity.User;
import com.project.user.service.CheckoutService;
import com.project.user.service.CartService;
import com.project.user.service.PaymentService;
import com.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
public class CheckoutController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CheckoutService checkoutService;

    @GetMapping("/checkout")
    public String showCheckoutPage(@AuthenticationPrincipal UserDetails loggedUser, Model model) {
        User user = userService.findByEmail(loggedUser.getUsername());
        List<CartItem> cartItems = cartService.findCartItemsByUser(user);
        double cartTotal = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        model.addAttribute("cartTotal", cartTotal);
        return "checkout";
    }

    @PostMapping("/checkout/complete")
    public String completeCheckout(@RequestParam String paymentMethod, @AuthenticationPrincipal UserDetails loggedUser) {
        User user = userService.findByEmail(loggedUser.getUsername());
        List<CartItem> cartItems = cartService.findCartItemsByUser(user);

        // Check if there is enough stock for each cart item
//        for (CartItem cartItem : cartItems) {
//            if (cartItem.getQuantity() > cartItem.getBook().getStock()) {
//                return "redirect:/out-of-stock";
//            }
//        }

        double cartTotal = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();

        // Create a new Payment entity
        Payment payment = new Payment();
        payment.setAmount(cartTotal);
        payment.setModeOfPayment(paymentMethod);
        payment.setPaymentDate(new Date());
        payment.setUser(user);

        // Save the payment
        paymentService.savePayment(payment);

        // Perform checkout
        checkoutService.createOrder(user, payment, cartItems);
        checkoutService.clearCart(user);

        return "redirect:/user/home";
    }
}
