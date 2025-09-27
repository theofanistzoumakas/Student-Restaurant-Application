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
public class UpdateMenu implements CommandInterfacePost {

    RestaurantMenuRepository restaurantMenuRepository;
    RestaurantFoodRepository restaurantFoodRepository;
    BreakFastFoodsRepository breakFastFoodsRepository;
    LunchFoodsRepository lunchFoodsRepository;
    DinnerFoodsRepository dinnerFoodsRepository;

    @Autowired
    public UpdateMenu(RestaurantMenuRepository restaurantMenuRepository, RestaurantFoodRepository restaurantFoodRepository, BreakFastFoodsRepository breakFastFoodsRepository, LunchFoodsRepository lunchFoodsRepository,DinnerFoodsRepository dinnerFoodsRepository) {
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.restaurantFoodRepository = restaurantFoodRepository;
        this.breakFastFoodsRepository = breakFastFoodsRepository;
        this.lunchFoodsRepository = lunchFoodsRepository;
        this.dinnerFoodsRepository = dinnerFoodsRepository;
    }

    @Override
    @Transactional
    public RestaurantMenu executePost(Integer idMenu,String dayMenu, List<String> breakfast, List<String> lunch,
                                      List<String> dinner, List<String> breakfastDescription,
                                      List<String> lunchDescription, List<String> dinnerDescription,
                                      List<String> vegetarianBreakfast,List<String> vegetarianLunch,
                                      List<String> vegetarianDinner,List<String> veganBreakfast,
                                      List<String> veganLunch, List<String> veganDinner){

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


        Optional<RestaurantMenu> existingRestaurantMenu = restaurantMenuRepository.findById(idMenu);//check if menu exists

        if(existingRestaurantMenu.isPresent()){//delete all breakfasts, lunch and dinners but not the foods - make breakfasts, lunch and dinners again

            breakFastFoodsRepository.deleteByRestaurantMenu(existingRestaurantMenu.get());
            lunchFoodsRepository.deleteByRestaurantMenu(existingRestaurantMenu.get());
            dinnerFoodsRepository.deleteByRestaurantMenu(existingRestaurantMenu.get());


            for(int i=0;i<breakfast.size();i++){//check if THE EXACT food exist with all the arguments
                Optional<RestaurantFood> existingRestaurantFood =
                        restaurantFoodRepository.findByNameAndDescriptionAndVegetarianAndVegan(
                                breakfast.get(i),breakfastDescription.get(i),
                                vegetarianBreakfast.contains(Integer.toString(i)),
                                veganBreakfast.contains(Integer.toString(i)));


                if(existingRestaurantFood.isPresent()) {//if exists save it to menu
                        breakFastFoodsRepository.save(new BreakfastFoods(existingRestaurantMenu.get(), existingRestaurantFood.get()));

                }else{//if not, save this and save this to menu
                    boolean isVegetarian = vegetarianBreakfast.contains(Integer.toString(i));
                    boolean isVegan = veganBreakfast.contains(Integer.toString(i));
                    RestaurantFood restaurantFood = new RestaurantFood(breakfast.get(i),breakfastDescription.get(i),isVegetarian,isVegan);
                    restaurantFoodRepository.save(restaurantFood);
                    breakFastFoodsRepository.save(new BreakfastFoods(existingRestaurantMenu.get(), restaurantFood));

                }
            }



            for(int i=0;i<lunch.size();i++) {//same with breakfast
                Optional<RestaurantFood> existingRestaurantFood =
                        restaurantFoodRepository.findByNameAndDescriptionAndVegetarianAndVegan(lunch.get(i),
                                lunchDescription.get(i), vegetarianLunch.contains(Integer.toString(i)),veganLunch.contains(Integer.toString(i)));


                if (existingRestaurantFood.isPresent()) {

                        lunchFoodsRepository.save(new LunchFoods(existingRestaurantMenu.get(), existingRestaurantFood.get()));

                } else {
                    boolean isVegetarian = vegetarianLunch.contains(Integer.toString(i));
                    boolean isVegan = veganLunch.contains(Integer.toString(i));
                    RestaurantFood restaurantFood = new RestaurantFood(lunch.get(i),lunchDescription.get(i),isVegetarian,isVegan);
                    restaurantFoodRepository.save(restaurantFood);
                    lunchFoodsRepository.save(new LunchFoods(existingRestaurantMenu.get(), restaurantFood));

                }

            }


            for(int i=0;i<dinner.size();i++) {//same with dinner
                Optional<RestaurantFood> existingRestaurantFood =
                        restaurantFoodRepository.findByNameAndDescriptionAndVegetarianAndVegan(
                                dinner.get(i),dinnerDescription.get(i),vegetarianDinner.contains(Integer.toString(i)),
                                veganDinner.contains(Integer.toString(i)));


                if (existingRestaurantFood.isPresent()) {
                        dinnerFoodsRepository.save(new DinnerFoods(existingRestaurantMenu.get(), existingRestaurantFood.get()));

                } else {
                    boolean isVegetarian = vegetarianDinner.contains(Integer.toString(i));
                    boolean isVegan = veganDinner.contains(Integer.toString(i));
                    RestaurantFood restaurantFood = new RestaurantFood(dinner.get(i),dinnerDescription.get(i),isVegetarian,isVegan);
                    restaurantFoodRepository.save(restaurantFood);
                    dinnerFoodsRepository.save(new DinnerFoods(existingRestaurantMenu.get(), restaurantFood));

                }

            }

            return existingRestaurantMenu.get();
        }

        return null;
    }

    private List<String> fillNullContents(List<String> thisList){

        if(thisList == null){
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
