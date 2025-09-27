package com.example.student_restaurant_appplication_development.model;

import jakarta.persistence.*;

@Entity
public class RestaurantUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;


    @OneToOne(mappedBy = "restaurantUser", cascade = CascadeType.ALL)
    private RestaurantCustomer restaurantCustomer;

    @OneToOne(mappedBy = "restaurantUser", cascade = CascadeType.ALL)
    private RestaurantAdmin restaurantAdmin;

    public RestaurantUser() {}

    public RestaurantUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }


}
