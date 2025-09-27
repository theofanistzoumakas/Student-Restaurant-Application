package com.example.student_restaurant_appplication_development.model;

import jakarta.persistence.*;

@Entity
public class CustomerAllergies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="restaurantCustomer_id")
    RestaurantCustomer restaurantCustomer;

    @ManyToOne
    @JoinColumn(name="allergen_id")
    Allergen allergen;

    public CustomerAllergies() {}

    public CustomerAllergies(RestaurantCustomer restaurantCustomer, Allergen allergen) {
        this.restaurantCustomer = restaurantCustomer;
        this.allergen = allergen;
    }

    public Allergen getAllergen() {
        return allergen;
    }
}
