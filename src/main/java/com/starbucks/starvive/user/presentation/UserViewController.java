package com.starbucks.starvive.user.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
} 