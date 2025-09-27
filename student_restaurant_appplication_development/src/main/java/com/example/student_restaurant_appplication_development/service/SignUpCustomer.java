package com.example.student_restaurant_appplication_development.service;

import com.example.student_restaurant_appplication_development.model.RestaurantCustomer;
import com.example.student_restaurant_appplication_development.model.RestaurantUser;
import com.example.student_restaurant_appplication_development.repository.RestaurantCustomerRepository;
import com.example.student_restaurant_appplication_development.repository.RestaurantUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignUpCustomer implements SignUpInterface{


    RestaurantCustomerRepository restaurantCustomerRepository;
    RestaurantUserRepository restaurantUserRepository;

    public SignUpCustomer(RestaurantCustomerRepository restaurantCustomerRepository, RestaurantUserRepository restaurantUserRepository) {
        this.restaurantCustomerRepository = restaurantCustomerRepository;
        this.restaurantUserRepository = restaurantUserRepository;
    }

    @Override
    public RestaurantUser signUp(String username, String userPassword) {

        Optional<RestaurantUser> existing_user = restaurantUserRepository.findByUsername(username);

        if (existing_user.isEmpty()) {
            String pass_result = PasswordEncryption.encryptPassword(userPassword);//password encryption
            RestaurantUser new_user = new RestaurantUser(username, pass_result);
            restaurantUserRepository.save(new_user);
            Optional<RestaurantUser> restaurantUserOptional = restaurantUserRepository.findById(new_user.getId());
            restaurantCustomerRepository.save(new RestaurantCustomer(restaurantUserOptional.get()));
            return new_user;
        }
         return null;

    }
}
