package com.example.student_restaurant_appplication_development.service;


import com.example.student_restaurant_appplication_development.model.RestaurantAdmin;
import com.example.student_restaurant_appplication_development.model.RestaurantCustomer;
import com.example.student_restaurant_appplication_development.model.RestaurantUser;
import com.example.student_restaurant_appplication_development.repository.RestaurantAdminRepository;
import com.example.student_restaurant_appplication_development.repository.RestaurantCustomerRepository;
import com.example.student_restaurant_appplication_development.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindRestaurantUser {

    RestaurantUserRepository restaurantUserRepository;
    RestaurantCustomerRepository restaurantCustomerRepository;
    RestaurantAdminRepository restaurantAdminRepository;

    @Autowired
    public FindRestaurantUser(RestaurantUserRepository restaurantUserRepository,
                              RestaurantAdminRepository restaurantAdminRepository,
                              RestaurantCustomerRepository restaurantCustomerRepository) {
        this.restaurantUserRepository = restaurantUserRepository;
        this.restaurantAdminRepository = restaurantAdminRepository;
        this.restaurantCustomerRepository = restaurantCustomerRepository;
    }

    //find user
    public Optional<RestaurantUser> findUser(String username) {
        return restaurantUserRepository.findByUsername(username);
    }

    public Optional<RestaurantCustomer> findCustomer(RestaurantUser restaurantUser ) {
        return restaurantCustomerRepository.findByRestaurantUser(restaurantUser);
    }

    public Optional<RestaurantAdmin> findAdmin(RestaurantUser restaurantUser) {
        return restaurantAdminRepository.findByRestaurantUser(restaurantUser);
    }


}
