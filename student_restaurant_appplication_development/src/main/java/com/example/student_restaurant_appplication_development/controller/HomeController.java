package com.example.student_restaurant_appplication_development.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    //start app
    @GetMapping("/Home")
    public String loginPage() {
        return "redirect:/Home/login";
    }
}
