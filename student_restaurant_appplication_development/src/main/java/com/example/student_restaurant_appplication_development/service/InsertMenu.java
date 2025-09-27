package com.example.student_restaurant_appplication_development.service;

import com.example.student_restaurant_appplication_development.controller.CommandInterfacePost;
import com.example.student_restaurant_appplication_development.model.*;
import com.example.student_restaurant_appplication_development.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InsertMenu implements CommandInterfacePost {


    private final RestaurantMenuRepository restaurantMenuRepository;
    private final RestaurantFoodRepository restaurantFoodRepository;
    private final BreakFastFoodsRepository breakFastFoodsRepository;
    private final LunchFoodsRepository lunchFoodsRepository;
    private final DinnerFoodsRepository dinnerFoodsRepository;

    @Autowired
    public InsertMenu(RestaurantMenuRepository restaurantMenuRepository, RestaurantFoodRepository restaurantFoodRepository,BreakFastFoodsRepository breakFastFoodsRepository, LunchFoodsRepository lunchFoodsRepository, DinnerFoodsRepository dinnerFoodsRepository) {
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.restaurantFoodRepository = restaurantFoodRepository;
        this.breakFastFoodsRepository = breakFastFoodsRepository;
        this.lunchFoodsRepository = lunchFoodsRepository;
        this.dinnerFoodsRepository = dinnerFoodsRepository;

    }

    @Override
    public RestaurantMenu executePost(Integer idMenu,String dayMenu, List<String> breakfast,List<String> lunch,
                                      List<String> dinner, List<String> breakfastDescription,
                                      List<String> lunchDescription, List<String> dinnerDescription,
                                      List<String> vegetarianBreakfast,List<String> vegetarianLunch,
                                      List<String> vegetarianDinner,List<String> veganBreakfast,
                                      List<String> veganLunch, List<String> veganDinner) {

        RestaurantMenu restaurantMenu = new RestaurantMenu(dayMenu);

        breakfast = fillNullContents(breakfast);//make sure that parameters will not have any null
        lunch = fillNullContents(lunch);
        dinner = fillNullContents(dinner);
        breakfastDescription = fillNullContents(breakfastDescription);
        lunchDescription = fillNullContents(lunchDescription);
        dinnerDescription = fillNullContents(dinnerDescription);
        vegetarianBreakfast = fillNullContents(vegetarianBreakfast);
        vegetarianLunch = fillNullContents(vegetarianLunch);
        vegetarianDinner = fillNullContents(vegetarianDinner);
        veganBreakfast = fillNullContents(veganBreakfast);
        veganLunch = fillNullContents(veganLunch);
        veganDinner = fillNullContents(veganDinner);

        for (int i = 0; i < breakfast.size(); i++) {

            Optional<RestaurantFood> existingRestaurantFood  =
                    restaurantFoodRepository.findByNameAndDescriptionAndVegetarianAndVegan(breakfast.get(i),
                            breakfastDescription.get(i),vegetarianBreakfast.contains(Integer.toString(i)),
                            veganBreakfast.contains(Integer.toString(i)));//check if THE EXACT food exists with all this parameters
            if(existingRestaurantFood.isPresent()) {//if exists save to menu
                BreakfastFoods breakfastFoods = new BreakfastFoods(restaurantMenu,existingRestaurantFood.get());
                restaurantMenu.getBreakfast().add(breakfastFoods);
            }
            else{//if not save new food and save it to menu
                boolean isVegetarian = vegetarianBreakfast.contains(Integer.toString(i));
                boolean isVegan = veganBreakfast.contains(Integer.toString(i));
                RestaurantFood restaurantFood = new RestaurantFood(breakfast.get(i),breakfastDescription.get(i),isVegetarian,isVegan);
                restaurantFoodRepository.save(restaurantFood);
                BreakfastFoods breakfastFoods = new BreakfastFoods(restaurantMenu,restaurantFood);

                restaurantMenu.getBreakfast().add(breakfastFoods);
            }

        }

        for (int i=0; i<lunch.size(); i++) {//same with breakfast

            Optional<RestaurantFood> existingRestaurantFood  =
                    restaurantFoodRepository.findByNameAndDescriptionAndVegetarianAndVegan(lunch.get(i),
                            lunchDescription.get(i), vegetarianLunch.contains(Integer.toString(i)),veganLunch.contains(Integer.toString(i)));
            if(existingRestaurantFood.isPresent()) {
                LunchFoods lunchFoods = new LunchFoods(restaurantMenu, existingRestaurantFood.get());

                restaurantMenu.getLunch().add(lunchFoods);
            }else {
                boolean isVegetarian = vegetarianLunch.contains(Integer.toString(i));
                boolean isVegan = veganLunch.contains(Integer.toString(i));
                RestaurantFood restaurantFood = new RestaurantFood(lunch.get(i),lunchDescription.get(i),isVegetarian,isVegan);
                restaurantFoodRepository.save(restaurantFood);
                LunchFoods lunchFoods = new LunchFoods(restaurantMenu, restaurantFood);

                restaurantMenu.getLunch().add(lunchFoods);
            }

        }

        for (int i=0; i<dinner.size(); i++) {//same with breakfast
            Optional<RestaurantFood> existingRestaurantFood  =
                    restaurantFoodRepository.findByNameAndDescriptionAndVegetarianAndVegan(dinner.get(i),
                            dinnerDescription.get(i),vegetarianDinner.contains(Integer.toString(i)),
                            veganDinner.contains(Integer.toString(i)));
            if(existingRestaurantFood.isPresent()) {
                DinnerFoods dinnerFoods = new DinnerFoods(restaurantMenu, existingRestaurantFood.get());

                restaurantMenu.getDinner().add(dinnerFoods);
            }
            else {
                boolean isVegetarian = vegetarianDinner.contains(Integer.toString(i));
                boolean isVegan = veganDinner.contains(Integer.toString(i));
                RestaurantFood restaurantFood = new RestaurantFood(dinner.get(i), dinnerDescription.get(i), isVegetarian, isVegan);
                restaurantFoodRepository.save(restaurantFood);
                DinnerFoods dinnerFoods = new DinnerFoods(restaurantMenu, restaurantFood);

                restaurantMenu.getDinner().add(dinnerFoods);
            }
        }

        restaurantMenuRepository.save(restaurantMenu);

        breakFastFoodsRepository.saveAll(restaurantMenu.getBreakfast());
        lunchFoodsRepository.saveAll(restaurantMenu.getLunch());
        dinnerFoodsRepository.saveAll(restaurantMenu.getDinner());


        return restaurantMenu;
    }


    private List<String> fillNullContents(List<String> thisList){

        if(thisList == null) {
            return new ArrayList<>();
        }


        for(int i=0;i<thisList.size();i++){
            if(thisList.get(i)==null){
                thisList.set(i,"nothing");
            }
        }

        return new ArrayList<>(thisList);
    }

}
