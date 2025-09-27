package com.example.student_restaurant_appplication_development.service;

import com.example.student_restaurant_appplication_development.model.RestaurantCustomer;
import com.example.student_restaurant_appplication_development.model.RestaurantUser;
import com.example.student_restaurant_appplication_development.repository.RestaurantCustomerRepository;

public interface SignUpInterface {//group necessary methods for signup
    RestaurantUser signUp(String user_username, String user_password);
}
