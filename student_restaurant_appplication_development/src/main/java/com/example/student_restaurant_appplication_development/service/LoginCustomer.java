package com.example.student_restaurant_appplication_development.service;

import com.example.student_restaurant_appplication_development.model.RestaurantCustomer;
import com.example.student_restaurant_appplication_development.model.RestaurantUser;
import com.example.student_restaurant_appplication_development.repository.RestaurantCustomerRepository;
import com.example.student_restaurant_appplication_development.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginCustomer {

    RestaurantCustomerRepository restaurantCustomerRepository;
    RestaurantUserRepository restaurantUserRepository;

    LoginCustomer() {}

    @Autowired
    LoginCustomer(RestaurantCustomerRepository restaurantCustomerRepository, RestaurantUserRepository restaurantUserRepository) {
        this.restaurantCustomerRepository = restaurantCustomerRepository;
        this.restaurantUserRepository = restaurantUserRepository;
    }

    public boolean loginCustomer(RestaurantUser restaurantCustomer, String password) {
        Optional<RestaurantUser> userExists = restaurantUserRepository.findByUsername(restaurantCustomer.getUsername());

        if(userExists.isPresent()) {
            if(PasswordEncryption.matchPassword(password, userExists.get().getPassword())) {//match password
                return restaurantCustomerRepository.findByRestaurantUser(userExists.get()).isPresent();
            }
        }
        return false;
    }
}
