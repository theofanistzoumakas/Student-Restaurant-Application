package com.example.student_restaurant_appplication_development.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class RestaurantFood {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long foodId;


    private String name;

    //private Double favourite_counter;

    private String description;

    private Boolean vegetarian;

    private Boolean vegan;

    public RestaurantFood(){}

    public RestaurantFood(String name, String description, Boolean vegetarian, Boolean vegan) {
        this.name = name;
        this.description = description;
        this.vegetarian = vegetarian;
        this.vegan = vegan;
    }

    public String getName() {
        return name;
    }

    public Long getFoodId() {
        return foodId;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getVegetarian() { return vegetarian; }

    public Boolean getVegan() { return vegan; }
}
