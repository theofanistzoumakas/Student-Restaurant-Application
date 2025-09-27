package com.example.student_restaurant_appplication_development.service;

import com.example.student_restaurant_appplication_development.controller.CommandInterfacePost;
import com.example.student_restaurant_appplication_development.model.*;
import com.example.student_restaurant_appplication_development.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DeleteMenu{


    private final RestaurantMenuRepository restaurantMenuRepository;
    private final BreakFastFoodsRepository breakFastFoodsRepository;
    private final LunchFoodsRepository lunchFoodsRepository;
    private final DinnerFoodsRepository dinnerFoodsRepository;

    @Autowired
    public DeleteMenu(RestaurantMenuRepository restaurantMenuRepository,
                      BreakFastFoodsRepository breakFastFoodsRepository, LunchFoodsRepository lunchFoodsRepository,
                      DinnerFoodsRepository dinnerFoodsRepository) {
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.breakFastFoodsRepository = breakFastFoodsRepository;
        this.lunchFoodsRepository = lunchFoodsRepository;
        this.dinnerFoodsRepository = dinnerFoodsRepository;

    }


    @Transactional
    public RestaurantMenu executePost(Integer idMenu) {

        Optional<RestaurantMenu> existingRestaurantMenu = restaurantMenuRepository.findById(idMenu);

        if(existingRestaurantMenu.isPresent()) {//delete everything except foods
            breakFastFoodsRepository.deleteByRestaurantMenu(existingRestaurantMenu.get());
            lunchFoodsRepository.deleteByRestaurantMenu(existingRestaurantMenu.get());
            dinnerFoodsRepository.deleteByRestaurantMenu(existingRestaurantMenu.get());

            restaurantMenuRepository.delete(existingRestaurantMenu.get());

            return existingRestaurantMenu.get();
        }

        return null;
    }

}
