package com.example.student_restaurant_appplication_development.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class RestaurantMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private String dayMenu;

    @OneToMany(mappedBy = "restaurantMenu")
    private List<BreakfastFoods> breakfast = new ArrayList<>();

    @OneToMany(mappedBy = "restaurantMenu")
    private List<LunchFoods> lunch = new ArrayList<>();

    @OneToMany(mappedBy = "restaurantMenu")
    private List<DinnerFoods> dinner = new ArrayList<>();



    public RestaurantMenu(String dayMenu) {
        this.dayMenu = dayMenu;
    }

    public RestaurantMenu(Integer id,String dayMenu) {
        this.id = id;
        this.dayMenu = dayMenu;
    }

    public RestaurantMenu(Integer id,String  dayMenu, List<BreakfastFoods> breakfast, List<LunchFoods> lunch, List<DinnerFoods> dinner) {
        this.id = id;
        this.dayMenu = dayMenu;
        this.breakfast = new ArrayList<>(breakfast);
        this.lunch = new ArrayList<>(lunch);
        this.dinner = new ArrayList<>(dinner);
    }

    public RestaurantMenu() {

    }

    public Integer getId() {
        return id;
    }

    public String getDay() {
        return dayMenu;
    }

    public List<BreakfastFoods> getBreakfast() {
        return breakfast;
    }

    public List<LunchFoods> getLunch() {
        return lunch;
    }

    public List<DinnerFoods> getDinner() {
        return dinner;
    }
}
