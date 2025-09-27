package com.example.student_restaurant_appplication_development.service;

import com.example.student_restaurant_appplication_development.model.Allergen;
import com.example.student_restaurant_appplication_development.model.CustomerAllergies;
import com.example.student_restaurant_appplication_development.model.RestaurantCustomer;
import com.example.student_restaurant_appplication_development.model.RestaurantFood;
import com.example.student_restaurant_appplication_development.repository.AllergenRepository;
import com.example.student_restaurant_appplication_development.repository.CustomerAllergiesRepository;
import com.example.student_restaurant_appplication_development.repository.RestaurantCustomerRepository;
import com.example.student_restaurant_appplication_development.repository.RestaurantFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ViewAllergens {

    private final AllergenRepository allergenRepository;
    private final CustomerAllergiesRepository customerAllergiesRepository;
    private final RestaurantFoodRepository restaurantFoodRepository;
    private final RestaurantCustomerRepository restaurantCustomerRepository;

    @Autowired
    public ViewAllergens(AllergenRepository allergenRepository, CustomerAllergiesRepository customerAllergiesRepository,
                         RestaurantFoodRepository restaurantFoodRepository,
                         RestaurantCustomerRepository restaurantCustomerRepository) {
        this.allergenRepository = allergenRepository;
        this.customerAllergiesRepository = customerAllergiesRepository;
        this.restaurantFoodRepository = restaurantFoodRepository;
        this.restaurantCustomerRepository = restaurantCustomerRepository;
    }

    public List<Allergen> getAllAllergens() {
        return Collections.unmodifiableList(allergenRepository.findAll());
    }

    public List<String> getCustomerAllergiesNames(RestaurantCustomer restaurantCustomer) {
        return customerAllergiesRepository.findByRestaurantCustomer(restaurantCustomer)
                .stream().map(CustomerAllergies::getAllergen)
                .map(Allergen::getAllergenName).toList();
    }

    public Map<Long,Boolean> foodsWithAllergens(RestaurantCustomer restaurantCustomer) {
        Map<Long,Boolean> foodsWithAllergens = new HashMap<>();

        List<RestaurantFood> restaurantFoods = restaurantFoodRepository.findAll();
        //find customer's allergens
        List<Allergen> allergens = customerAllergiesRepository.findByRestaurantCustomer(restaurantCustomer).stream().map(CustomerAllergies::getAllergen).toList();

        for (Allergen allergen : allergens) {
            for (RestaurantFood restaurantFood : restaurantFoods) {
                //check the allergen on menu food's description
                List<String> description_words = Arrays.asList(restaurantFood.getDescription().split("\\s+"));

                if(description_words.contains(allergen.getAllergenName())){//check if food has allergen
                    foodsWithAllergens.put(restaurantFood.getFoodId(),true);
                }
                else{
                    foodsWithAllergens.put(restaurantFood.getFoodId(),false);
                }
            }
        }

        return foodsWithAllergens;
    }


    public Map<Long,Double> allergicPeopleByAllergens(){
        Map<Long,Double> allergicPeople = new HashMap<>();


        List<Allergen> allergens = allergenRepository.findAll();

        for (Allergen allergen : allergens) {
            double allergicCustomers = customerAllergiesRepository.countByAllergen(allergen);
            //check how many customers are allergic to this
            allergicPeople.put(allergen.getId(),(
                    new BigDecimal(allergicCustomers/restaurantCustomerRepository.count() * 100).setScale(2, RoundingMode.HALF_UP).doubleValue()
            ));
        }

        return allergicPeople;

    }
}
