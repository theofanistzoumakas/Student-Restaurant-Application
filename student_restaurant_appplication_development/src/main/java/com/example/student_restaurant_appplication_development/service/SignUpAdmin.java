package com.example.student_restaurant_appplication_development.service;

import com.example.student_restaurant_appplication_development.model.RestaurantAdmin;
import com.example.student_restaurant_appplication_development.model.RestaurantUser;
import com.example.student_restaurant_appplication_development.repository.RestaurantAdminRepository;
import com.example.student_restaurant_appplication_development.repository.RestaurantUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SignUpAdmin {

    RestaurantUserRepository restaurantUserRepository;
    RestaurantAdminRepository restaurantAdminRepository;

    public SignUpAdmin(RestaurantUserRepository restaurantUserRepository,
                       RestaurantAdminRepository restaurantAdminRepository) {
        this.restaurantUserRepository = restaurantUserRepository;
        this.restaurantAdminRepository = restaurantAdminRepository;
    }

    public void signUpAdmin() {
        List<RestaurantUser> existingAdminList = restaurantUserRepository.findAll();

        if(existingAdminList.isEmpty()){ // if it is not any admin create one
            String pass_result = PasswordEncryption.encryptPassword("YOUR_PASSWORD");
            RestaurantUser admin = new RestaurantUser("YOUR_USERNAME",pass_result);
            restaurantUserRepository.save(admin);
            Optional<RestaurantUser> foundRestaurantUser = restaurantUserRepository.findById(admin.getId());
            restaurantAdminRepository.save(new RestaurantAdmin(foundRestaurantUser.get()));
        }

    }
}
