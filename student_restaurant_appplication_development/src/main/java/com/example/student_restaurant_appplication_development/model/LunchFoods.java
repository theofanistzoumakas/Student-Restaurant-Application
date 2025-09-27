package com.example.student_restaurant_appplication_development.model;


import jakarta.persistence.*;

@Entity
public class LunchFoods {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurantMenu_id")
    private RestaurantMenu restaurantMenu;

    @ManyToOne
    @JoinColumn(name = "restaurantFood_id")
    private RestaurantFood restaurantFood;

    public LunchFoods() {}

    public LunchFoods(RestaurantMenu restaurantMenu, RestaurantFood restaurantFood) {
        this.restaurantMenu = restaurantMenu;
        this.restaurantFood = restaurantFood;
    }


    public RestaurantMenu getRestaurantMenu() {
        return restaurantMenu;
    }

    public RestaurantFood getRestaurantFood() {
        return restaurantFood;
    }

}
