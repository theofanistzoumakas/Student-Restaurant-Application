package com.example.student_restaurant_appplication_development.model;

import jakarta.persistence.*;

@Entity
public class RatingFoodsCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToOne
    @JoinColumn(name = "restaurantFood_id")
    private RestaurantFood restaurantFood;

    private Double counter;

    public RatingFoodsCounter() {}

    public RatingFoodsCounter(RestaurantFood restaurantFood, Double counter) {
        this.restaurantFood = restaurantFood;
        this.counter = counter;
    }

    public Double getCounter() {
        return counter;
    }

    public RestaurantFood getRestaurantFood() {
        return restaurantFood;
    }

}
