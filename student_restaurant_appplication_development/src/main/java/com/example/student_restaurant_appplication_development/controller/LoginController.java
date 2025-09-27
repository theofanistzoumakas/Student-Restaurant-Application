package com.example.student_restaurant_appplication_development.controller;

import com.example.student_restaurant_appplication_development.model.RestaurantAdmin;
import com.example.student_restaurant_appplication_development.model.RestaurantUser;
import com.example.student_restaurant_appplication_development.repository.RestaurantAdminRepository;
import com.example.student_restaurant_appplication_development.repository.RestaurantUserRepository;
import com.example.student_restaurant_appplication_development.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {

    LoginInterface loginInterface;
    LoginCustomer loginCustomer;
    SignUpAdmin signUpAdmin;

    public LoginController(LoginInterface loginInterface, LoginCustomer loginCustomer, SignUpAdmin signUpAdmin) {
        this.loginInterface = loginInterface;
        this.loginCustomer = loginCustomer;
        this.signUpAdmin = signUpAdmin;

    }

    //log in
    @GetMapping("/Home/login")
    public String login(Model model) {
        signUpAdmin.signUpAdmin();
        model.addAttribute("not_found",false);
        return "/Home/log_in_page";
    }

    //log in - not found user
    @GetMapping("/Home/loginError")
    public String loginError(Model model) {
        model.addAttribute("not_found",true);
        return "/Home/log_in_page";
    }


    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("user_password") String password, HttpSession session) {

        RestaurantUser restaurantUser = new RestaurantUser(username, password);
        //if this is admin
        if(loginInterface.login(restaurantUser,password)){
            session.setAttribute("restaurantAdmin",restaurantUser.getUsername());
            return "redirect:/restaurantMenu/viewRestaurantMenu/"+username;
        }
        else if(loginCustomer.loginCustomer(restaurantUser, password)){//if this is customer and exists
            session.setAttribute("restaurantCustomer",restaurantUser.getUsername());
            return "redirect:/RestaurantMenuCustomer/viewRestaurantMenu/"+username;
        }
        return "redirect:/Home/loginError";

    }


}
