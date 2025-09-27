package com.example.student_restaurant_appplication_development.service;

import com.example.student_restaurant_appplication_development.model.Allergen;
import com.example.student_restaurant_appplication_development.model.CustomerAllergies;
import com.example.student_restaurant_appplication_development.model.RestaurantCustomer;
import com.example.student_restaurant_appplication_development.repository.AllergenRepository;
import com.example.student_restaurant_appplication_development.repository.CustomerAllergiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UpdateAllergenList {

    AllergenRepository allergenRepository;
    CustomerAllergiesRepository customerAllergiesRepository;

    @Autowired
    public UpdateAllergenList(AllergenRepository allergenRepository, CustomerAllergiesRepository customerAllergiesRepository) {
        this.allergenRepository = allergenRepository;
        this.customerAllergiesRepository = customerAllergiesRepository;
    }
    //save new allergens
    public void saveNewAllergens(List<String> newAllergenList) {

        List<Allergen> allergens = allergenRepository.findAll(); //fewer requests on database

        List<String> allergenNames = allergens.stream().map(Allergen::getAllergenName).toList();//get allergen names
        List<Allergen> updatedList = newAllergenList.stream().filter(name -> !allergenNames.contains(name))
                .map(Allergen::new).collect(Collectors.toList());//create the new allergen list


        allergenRepository.saveAll(updatedList);


    }

    public void saveCustomerAllergens(List<String> newAllergenList, RestaurantCustomer restaurantCustomer) {

        List<CustomerAllergies> existingCustomerAllergies = customerAllergiesRepository.findByRestaurantCustomer(restaurantCustomer);


        for(String newAllergenName : newAllergenList) {
            Optional<Allergen> newAllergen = allergenRepository.findByAllergenName(newAllergenName);
            if(newAllergen.isPresent()) {
                Optional<CustomerAllergies> existingCustomerAllergy = customerAllergiesRepository.findByRestaurantCustomerAndAllergen(restaurantCustomer, newAllergen.get());
                if(existingCustomerAllergy.isEmpty()) {
                    customerAllergiesRepository.save(new CustomerAllergies(restaurantCustomer, newAllergen.get()));
                }
            }
        }

        for(CustomerAllergies customerAllergies : existingCustomerAllergies){
            if(!newAllergenList.contains(customerAllergies.getAllergen().getAllergenName())){
                customerAllergiesRepository.delete(customerAllergies);
            }
        }

    }

    @Transactional
    public void updateNewAllergens(String allergenName) {//remove allergen
        Optional<Allergen> allergenToRemove = allergenRepository.findByAllergenName(allergenName);//get allergen

        customerAllergiesRepository.deleteByAllergen(allergenToRemove.get());//remove from customer if exists

        allergenToRemove.ifPresent(allergen -> allergenRepository.delete(allergen));//remove
    }
}
