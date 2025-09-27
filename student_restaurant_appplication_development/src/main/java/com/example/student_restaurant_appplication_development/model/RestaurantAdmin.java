package com.example.student_restaurant_appplication_development.model;

import jakarta.persistence.*;

@Entity
public class RestaurantAdmin {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private RestaurantUser restaurantUser;

    public RestaurantAdmin() {}

    public RestaurantAdmin(RestaurantUser restaurantUser) {
        this.restaurantUser = restaurantUser;
    }

    public String getUsername() {
        return restaurantUser.getUsername();
    }

    public Long getId() {
        return id;
    }

}
