package com.example.student_restaurant_appplication_development.service;

import com.example.student_restaurant_appplication_development.model.RestaurantAdmin;
import com.example.student_restaurant_appplication_development.model.RestaurantUser;
import com.example.student_restaurant_appplication_development.repository.RestaurantAdminRepository;
import com.example.student_restaurant_appplication_development.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginAdmin implements LoginInterface {

    private final RestaurantAdminRepository restaurantAdminRepository;
    private final RestaurantUserRepository restaurantUserRepository;

    @Autowired
    public LoginAdmin(RestaurantAdminRepository restaurantAdminRepository, RestaurantUserRepository restaurantUserRepository) {
        this.restaurantAdminRepository = restaurantAdminRepository;
        this.restaurantUserRepository = restaurantUserRepository;
    }

    @Override
    public boolean login(RestaurantUser restaurantAdmin, String password) {
        Optional<RestaurantUser> userExists = restaurantUserRepository.findByUsername(restaurantAdmin.getUsername());

        if(userExists.isPresent() ) {
            if(PasswordEncryption.matchPassword(password, userExists.get().getPassword())) {//match password
                return restaurantAdminRepository.findByRestaurantUser(userExists.get()).isPresent();
            }
        }
        return false;
    }
}
