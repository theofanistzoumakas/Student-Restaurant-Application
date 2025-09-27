package com.example.student_restaurant_appplication_development.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController {


    //log out
    @PostMapping("/logout")
    public String logout(HttpSession session) {

        if(session != null) {
            session.invalidate();
        }

        return "redirect:/Home/login";
    }
}
