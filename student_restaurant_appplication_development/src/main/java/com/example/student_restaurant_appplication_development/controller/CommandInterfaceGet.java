package com.example.student_restaurant_appplication_development.controller;

import com.example.student_restaurant_appplication_development.model.RestaurantMenu;

import java.util.List;

public interface CommandInterfaceGet {//group get methods for insert and update menu
    List<RestaurantMenu> executeGet();
}
