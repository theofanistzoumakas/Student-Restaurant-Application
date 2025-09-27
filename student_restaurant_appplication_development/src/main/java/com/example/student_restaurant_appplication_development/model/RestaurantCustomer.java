package com.example.student_restaurant_appplication_development.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class RestaurantCustomer {


    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private RestaurantUser restaurantUser;

    @OneToMany(mappedBy = "restaurantCustomer")
    private List<CustomerRatings> customerRatings;


    @OneToMany(mappedBy="restaurantCustomer")
    private List<CustomerAllergies> customerFoodAllergies;


    public RestaurantCustomer() {
    }


    public RestaurantCustomer(RestaurantUser restaurantUser) {
        this.restaurantUser = restaurantUser;
    }

    public String getUserName() {
        return restaurantUser.getUsername();
    }

    public List<CustomerRatings> getCustomerRatings() {
        return new ArrayList<>(customerRatings);
    }
}
