package com.project.admin.controller;

import com.project.admin.entity.User;
import com.project.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/block/{userId}")
    public String blockUser(@PathVariable("userId") Long userId) {
        userService.blockUser(userId);
        return "redirect:/admin/users";
    }

    @GetMapping("/unblock/{userId}")
    public String unblockUser(@PathVariable("userId") Long userId) {
        userService.unblockUser(userId);
        return "redirect:/admin/users";
    }
}
