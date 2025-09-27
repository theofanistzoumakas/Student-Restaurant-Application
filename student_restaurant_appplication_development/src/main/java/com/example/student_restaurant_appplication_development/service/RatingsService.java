package com.example.student_restaurant_appplication_development.service;

import com.example.student_restaurant_appplication_development.model.CustomerRatings;
import com.example.student_restaurant_appplication_development.model.RatingFoodsCounter;
import com.example.student_restaurant_appplication_development.model.RestaurantCustomer;
import com.example.student_restaurant_appplication_development.model.RestaurantFood;
import com.example.student_restaurant_appplication_development.repository.CustomerRatingsRepository;
import com.example.student_restaurant_appplication_development.repository.RatingFoodsCounterRepository;
import com.example.student_restaurant_appplication_development.repository.RestaurantCustomerRepository;
import com.example.student_restaurant_appplication_development.repository.RestaurantFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class RatingsService {


    CustomerRatingsRepository customerFavouritesRepository;
    RestaurantFoodRepository restaurantFoodRepository;
    RatingFoodsCounterRepository favouriteFoodsCounterRepository;
    RestaurantCustomerRepository restaurantCustomerRepository;

    @Autowired
    public RatingsService(CustomerRatingsRepository customerFavouritesRepository,
                          RestaurantFoodRepository restaurantFoodRepository,
                          RatingFoodsCounterRepository favouriteFoodsCounterRepository,
                          RestaurantCustomerRepository restaurantCustomerRepository) {
        this.customerFavouritesRepository = customerFavouritesRepository;
        this.restaurantFoodRepository = restaurantFoodRepository;
        this.favouriteFoodsCounterRepository = favouriteFoodsCounterRepository;
        this.restaurantCustomerRepository = restaurantCustomerRepository;
    }


    @Transactional
    public void editFavorites(RestaurantCustomer existingCustomer,String foodName, String command, Double rating,
                              String foodDescription,Boolean isVegetarian, Boolean isVegan){

        if(isVegetarian == null){//check nulls
            isVegetarian = false;
        }

        if(isVegan == null){
            isVegan = false;
        }
        //check if food is present
        Optional<RestaurantFood> existingRestaurantFood = restaurantFoodRepository
                .findByNameAndDescriptionAndVegetarianAndVegan(foodName,foodDescription,isVegetarian,isVegan);

        //if it is and wants to add this
        if(command.equals("add") && existingRestaurantFood.isPresent()) {
            Optional<CustomerRatings> existingCustomerFavourite = customerFavouritesRepository.findByRestaurantCustomerAndRestaurantFood(existingCustomer,existingRestaurantFood.orElseThrow());
            //check if it is rated

            if(existingCustomerFavourite.isEmpty()) {//if it is not

                customerFavouritesRepository.save(new CustomerRatings(existingCustomer, existingRestaurantFood.get(), rating));

                Optional<RatingFoodsCounter> favouriteFoodsCounter = favouriteFoodsCounterRepository
                        .findFavouriteFoodsCounterByRestaurantFood(existingRestaurantFood.get());
                favouriteFoodsCounter.ifPresent(foodsCounter -> favouriteFoodsCounterRepository
                        .delete(foodsCounter));

            }
        }

        if(command.equals("remove") && existingRestaurantFood.isPresent()) {
            Optional<CustomerRatings> existingCustomerFavourite = customerFavouritesRepository
                    .findByRestaurantCustomerAndRestaurantFood(existingCustomer,existingRestaurantFood.orElseThrow());


            if(existingCustomerFavourite.isPresent()) {

                customerFavouritesRepository.delete(existingCustomerFavourite.get());

                Optional<RatingFoodsCounter> favouriteFoodsCounter = favouriteFoodsCounterRepository
                        .findFavouriteFoodsCounterByRestaurantFood(existingRestaurantFood.get());
                favouriteFoodsCounter.ifPresent(foodsCounter -> favouriteFoodsCounterRepository
                        .delete(foodsCounter));
            }
        }


        if(existingRestaurantFood.isPresent()) {


            List<CustomerRatings> existingRestaurantFavouriteFood = customerFavouritesRepository
                    .findAllByRestaurantFood(existingRestaurantFood.get());
            if(!existingRestaurantFavouriteFood.isEmpty()) {

                countRatings(existingRestaurantFood.get(),existingRestaurantFavouriteFood);
            }

        }

    }

    //bayesian approximation rating for k star scale
    private void countRatings(RestaurantFood food, List<CustomerRatings> customerFavourites){

        List<CustomerRatings> foodRatingsObject = new ArrayList<>();

        for(CustomerRatings obj : customerFavourites){
            if(Objects.equals(obj.getRestaurantFood().getFoodId(), food.getFoodId())){
                foodRatingsObject.add(obj);
            }
        }

        double k = foodRatingsObject.size();


        double ratingsSum = 0.0;
        for (CustomerRatings obj : foodRatingsObject) {
            ratingsSum += obj.getRating();
        }

        if(ratingsSum!=0){

            double z = 1.96;

            double a = 0.0;
            double b = 0.0;

            for(int i=0;i<k;i++){
                a += (i+1)*(foodRatingsObject.get(i).getRating()+1)/(ratingsSum+k);
                b += (i+1)*(i+1)*(foodRatingsObject.get(i).getRating()+1)/(ratingsSum+k);
            }

            double score = a - z*Math.sqrt((b-a*a)/(ratingsSum+k+1));

            favouriteFoodsCounterRepository.save(new RatingFoodsCounter(food,score));
        }

    }

    public List<RatingFoodsCounter> viewLikes(){//view likes

        return favouriteFoodsCounterRepository.findAll().stream()
                .map(o -> new RatingFoodsCounter(o.getRestaurantFood(),
                        BigDecimal.valueOf(o.getCounter()).setScale(2, RoundingMode.HALF_UP).doubleValue()))
                .sorted(Comparator.comparing(RatingFoodsCounter::getCounter).reversed())
                .collect(Collectors.toList());
    }



    public Map<RestaurantFood,Long> most_rated_dishes(){
        //view foods with that are most rated

        Map<RestaurantFood,Long> all_rated_foods = new HashMap<>();

        List<RestaurantFood> foods = restaurantFoodRepository.findAll();

        for(RestaurantFood food : foods){
            Long ratings = customerFavouritesRepository.countByRestaurantFood(food);
            if(ratings!=0){
                all_rated_foods.put(food,ratings);
            }

        }
        //make the list size up to ten and sort them and make new map with no duplicates
        return all_rated_foods.entrySet().stream()
                .sorted(Map.Entry.<RestaurantFood,Long>comparingByValue().reversed()).limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2) -> e1,LinkedHashMap::new));



    }


    public Double positive_or_negative_rated_foods_percent(String type){

        List<RestaurantFood> foods = restaurantFoodRepository.findAll();

        List<RestaurantFood> positive_or_negative_rated_foods = new ArrayList<>();

        for(RestaurantFood food : foods){
            List<CustomerRatings> rates = customerFavouritesRepository.findAllByRestaurantFood(food);

            switch(type){//check foods with positive or negative ratings by customers
                case "positive":
                    if(rates.stream().anyMatch(customerFavourite -> customerFavourite.getRating() > 3)){

                        positive_or_negative_rated_foods.add(food);
                    }
                    break;
                case "negative":
                    if(rates.stream().anyMatch(customerFavourite -> customerFavourite.getRating() < 3)){
                        positive_or_negative_rated_foods.add(food);
                    }
                    break;
            }

        }

        double positive_or_negative_rated_foods_double = positive_or_negative_rated_foods.stream().distinct().count();//check unique foods
        double all_foods_double = foods.size();


        if(all_foods_double!=0) {//make values view with 2 decimals and make this double
            return new BigDecimal(positive_or_negative_rated_foods_double / all_foods_double * 100)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }
        else{
            return 0.0;
        }

    }

    //same but vegetarian or vegan foods positive rated
    public Double positive_rated_vegetarian_or_vegan_foods_percent(String type){

        List<RestaurantFood> foods = restaurantFoodRepository.findAll();

        List<RestaurantFood> positive_rated_foods = new ArrayList<>();

        for(RestaurantFood food : foods){
            List<CustomerRatings> rates = customerFavouritesRepository.findAllByRestaurantFood(food);

            switch(type){
                case "vegetarian":
                    if(rates.stream().anyMatch(customerFavourite -> customerFavourite.getRating() > 3) && food.getVegetarian() && !food.getVegan()){
                        positive_rated_foods.add(food);
                    }
                    break;
                case "vegan":
                    if(rates.stream().anyMatch(customerFavourite -> customerFavourite.getRating() > 3) && food.getVegetarian() && food.getVegan()){
                        positive_rated_foods.add(food);
                    }
                    break;
            }

        }

        double positive_rated_foods_double = positive_rated_foods.stream().distinct().count();
        double all_foods_double = foods.size();

        if(all_foods_double!=0) {
            return new BigDecimal(positive_rated_foods_double / all_foods_double * 100).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }else{
            return 0.0;
        }

    }


    public List<RatingFoodsCounter> viewTopFiveLikes(){
        //compare and sort
        return favouriteFoodsCounterRepository.findAll().stream()
                .map(o -> new RatingFoodsCounter(o.getRestaurantFood(),
                        BigDecimal.valueOf(o.getCounter()).setScale(2, RoundingMode.HALF_UP).doubleValue()))
                .sorted(Comparator.comparing(RatingFoodsCounter::getCounter).reversed()).limit(5).collect(Collectors.toList());
    }





}
