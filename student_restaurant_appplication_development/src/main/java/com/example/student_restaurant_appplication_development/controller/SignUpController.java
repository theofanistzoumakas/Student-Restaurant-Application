package com.example.student_restaurant_appplication_development.controller;


import com.example.student_restaurant_appplication_development.model.RestaurantUser;
import com.example.student_restaurant_appplication_development.service.SignUpInterface;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignUpController {

    SignUpInterface signUpInterface;

    public SignUpController (SignUpInterface signUpInterface) {
        this.signUpInterface = signUpInterface;
    }

    @GetMapping("/Home/signUp")
    public String signUp(Model model) {// view sign up page

        model.addAttribute("signUpError", false);
        return "/Home/restaurantMenuSignUp";

    }

    @GetMapping("/Home/signUpError")
    public String signUpError(Model model) {//sign up failed

        model.addAttribute("signUpError", true);
        return "/Home/restaurantMenuSignUp";

    }

    @PostMapping("/signUpRestaurantCustomer")
    public String signUpRestaurantCustomer(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {

        if(!username.isEmpty() && !password.isEmpty()) {
            RestaurantUser sign_up_result = signUpInterface.signUp(username, password); //check sign up

            if (sign_up_result != null) {// if success sign up
                session.setAttribute("restaurantCustomer", sign_up_result.getUsername());
                return "redirect:/RestaurantMenuCustomer/viewRestaurantMenu/" + username;
            }
        }
        return "redirect:/Home/signUpError";

    }



}
