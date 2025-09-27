package com.example.student_restaurant_appplication_development.service;

import com.example.student_restaurant_appplication_development.model.*;
import com.example.student_restaurant_appplication_development.repository.CustomerRatingsRepository;
import com.example.student_restaurant_appplication_development.repository.RatingFoodsCounterRepository;
import com.example.student_restaurant_appplication_development.repository.RestaurantFoodRepository;
import com.example.student_restaurant_appplication_development.repository.RestaurantMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ViewDishes {


    RestaurantFoodRepository restaurantFoodRepository;
    CustomerRatingsRepository customerFavouritesRepository;
    RestaurantMenuRepository restaurantMenuRepository;
    RatingFoodsCounterRepository ratingFoodsCounterRepository;

    @Autowired
    public ViewDishes(RestaurantFoodRepository restaurantFoodRepository,
                      CustomerRatingsRepository customerFavouritesRepository,
                      RestaurantMenuRepository restaurantMenuRepository, RatingFoodsCounterRepository ratingFoodsCounterRepository) {
        this.restaurantFoodRepository = restaurantFoodRepository;
        this.customerFavouritesRepository = customerFavouritesRepository;
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.ratingFoodsCounterRepository = ratingFoodsCounterRepository;
    }

    public Map<RestaurantFood,Boolean> viewDishes(){
        Map<RestaurantFood,Boolean> map = new HashMap<>();
        List<RestaurantFood> foods = restaurantFoodRepository.findAll();
        List<RestaurantMenu> menus = restaurantMenuRepository.findAll();
        Set<RestaurantFood> currentFoods = new HashSet<>();



        for(RestaurantMenu menu : menus){//add dishes
            currentFoods.addAll(menu.getBreakfast().stream().map(BreakfastFoods::getRestaurantFood).toList());
            currentFoods.addAll(menu.getLunch().stream().map(LunchFoods::getRestaurantFood).toList());
            currentFoods.addAll(menu.getDinner().stream().map(DinnerFoods::getRestaurantFood).toList());
        }





        for(RestaurantFood food : foods){//check rated dishes
            Optional<RatingFoodsCounter> foodIsRated = ratingFoodsCounterRepository.findFavouriteFoodsCounterByRestaurantFood(food);
            if(foodIsRated.isEmpty()){
                if(!currentFoods.contains(food)){
                    map.put(food,true);
                }
                else{
                    map.put(food,false);
                }
            }
            else{
                map.put(food,false);
            }
        }

        return Collections.unmodifiableMap(map);

    }

    public void deleteDish(Long foodId){
        restaurantFoodRepository.deleteById(foodId);
    }

    public Double viewAllVegetarianOrVeganDishesPercent(String type){

        double all_foods = restaurantFoodRepository.findAll().size();

        if(all_foods!=0) {//if foods exists
            return switch (type) {
                case "vegetarian" ->
                        new BigDecimal(restaurantFoodRepository
                                .findByVegetarianAndVegan(true, false).size() / all_foods * 100)
                                .setScale(2, RoundingMode.HALF_UP).doubleValue();//see all foods from base that are vegetarian
                case "vegan" ->
                        new BigDecimal(restaurantFoodRepository
                                .findByVegetarianAndVegan(true, true).size() / all_foods * 100)
                                .setScale(2, RoundingMode.HALF_UP).doubleValue();//see all foods from base that are vegan
                default -> 0.0;
            };
        }
        else{
            return 0.0;
        }

    }

    public Double viewVegetarianOrVeganDishesPercent(String type) {
        List<RestaurantMenu> menus = restaurantMenuRepository.findAll();

        Set<RestaurantFood> foods = new HashSet<>();
        double vegetarianOrVeganFoodsNumber;

        for(RestaurantMenu menu : menus){//add all dishes

            foods.addAll(menu.getBreakfast().stream().map(BreakfastFoods::getRestaurantFood).toList());
            foods.addAll(menu.getLunch().stream().map(LunchFoods::getRestaurantFood).toList());
            foods.addAll(menu.getDinner().stream().map(DinnerFoods::getRestaurantFood).toList());
        }

        if(!foods.isEmpty()) {
            switch (type) {
                case "vegetarian":
                    vegetarianOrVeganFoodsNumber = foods.stream().filter(food -> food.getVegetarian() && !food.getVegan()).toList().size();
                    return new BigDecimal(vegetarianOrVeganFoodsNumber / foods.size() * 100)
                            .setScale(2, RoundingMode.HALF_UP).doubleValue();//see how many times vegetarian foods are on the menu
                case "vegan":
                    vegetarianOrVeganFoodsNumber = foods.stream().filter(food -> food.getVegetarian() && food.getVegan()).toList().size();
                    return new BigDecimal(vegetarianOrVeganFoodsNumber / foods.size() * 100)
                            .setScale(2, RoundingMode.HALF_UP).doubleValue();//see how many times vegan foods are on the menu

            }
        }

        return 0.0;

    }

}
