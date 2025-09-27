package com.example.student_restaurant_appplication_development.model;


import jakarta.persistence.*;

@Entity
public class CustomerRatings {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "restaurantCustomer_id")
    private RestaurantCustomer restaurantCustomer;

    @ManyToOne
    @JoinColumn(name = "restaurantFood_id")
    private RestaurantFood restaurantFood;

    private Double rating;


    public CustomerRatings(){}

    public CustomerRatings(RestaurantCustomer restaurantCustomer, RestaurantFood restaurantFood, Double rating) {
        this.restaurantCustomer = restaurantCustomer;
        this.restaurantFood = restaurantFood;
        this.rating = rating;
    }

    public String getRestaurantFoodNames(){
        return restaurantFood.getName();

    }

    public String getRestaurantFoodDescription(){
        return restaurantFood.getDescription();

    }

    public RestaurantFood getRestaurantFood(){
        return restaurantFood;
    }

    public Double getRating(){
        return rating;
    }

}
