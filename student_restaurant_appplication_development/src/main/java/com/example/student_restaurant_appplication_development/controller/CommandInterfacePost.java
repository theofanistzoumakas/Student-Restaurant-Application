package com.example.student_restaurant_appplication_development.controller;

import com.example.student_restaurant_appplication_development.model.RestaurantMenu;

import java.util.List;

public interface CommandInterfacePost {//group post methods for insert and update menus
    RestaurantMenu executePost(Integer idMenu, String dayMenu, List<String> breakfast, List<String> lunch,
                               List<String> dinner, List<String> breakfastDescription,
                               List<String> lunchDescription, List<String> dinnerDescription,List<String> breakfastIsVegetarian,
                               List<String> lunchIsVegetarian, List<String> dinnerIsVegetarian,List<String> breakfastIsVegan,
                               List<String> lunchIsVegan, List<String> dinnerIsVegan);
}
