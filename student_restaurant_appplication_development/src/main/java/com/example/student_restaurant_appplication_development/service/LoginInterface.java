package com.example.student_restaurant_appplication_development.service;

import com.example.student_restaurant_appplication_development.model.RestaurantAdmin;
import com.example.student_restaurant_appplication_development.model.RestaurantUser;

public interface LoginInterface {//group necessary methods for login
    boolean login(RestaurantUser restaurantAdmin, String password);
}
