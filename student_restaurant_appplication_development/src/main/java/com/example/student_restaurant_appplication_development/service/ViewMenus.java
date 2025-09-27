package com.example.student_restaurant_appplication_development.service;

import com.example.student_restaurant_appplication_development.controller.CommandInterfaceGet;
import com.example.student_restaurant_appplication_development.model.RestaurantMenu;
import com.example.student_restaurant_appplication_development.repository.RestaurantMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ViewMenus  implements CommandInterfaceGet {

    private final RestaurantMenuRepository restaurantMenuRepository;

    @Autowired
    public ViewMenus(RestaurantMenuRepository restaurantMenuRepository) {
        this.restaurantMenuRepository = restaurantMenuRepository;
    }

    @Override
    public List<RestaurantMenu> executeGet(){

        Map<String,Integer> daysofTheWeek = new HashMap<>();//sort days of the week
        daysofTheWeek.put("Δευτέρα", 1);
        daysofTheWeek.put("Τρίτη", 2);
        daysofTheWeek.put("Τετάρτη", 3);
        daysofTheWeek.put("Πέμπτη", 4);
        daysofTheWeek.put("Παρασκευή", 5);
        daysofTheWeek.put("Σάββατο", 6);
        daysofTheWeek.put("Κυριακή", 7);

        List<RestaurantMenu> menuList = restaurantMenuRepository.findAll();

        Comparator<RestaurantMenu> sortedDays = Comparator.comparing(
                day ->daysofTheWeek.getOrDefault(day.getDay(), Integer.MAX_VALUE));//get days by value

        menuList.sort(sortedDays);//sort days

        return Collections.unmodifiableList(menuList);
    }

    public Optional<RestaurantMenu> findRestaurantMenu(int id){
        return restaurantMenuRepository.findById(id);
    }
}
