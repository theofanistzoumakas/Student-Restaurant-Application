package com.example.student_restaurant_appplication_development.controller;


import com.example.student_restaurant_appplication_development.model.*;
import com.example.student_restaurant_appplication_development.repository.*;
import com.example.student_restaurant_appplication_development.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/RestaurantMenuCustomer/")
public class RestaurantMenuControllerCustomer {

    List<RestaurantMenu> restaurantMenus;

    CommandInterfaceGet commandInterfaceGet;

    RatingsService ratingService;

    ViewAllergens viewAllergens;

    UpdateAllergenList updateAllergenList;

    ViewDishes viewDishes;

    AnnouncementsService announcementsService;

    FindRestaurantUser findRestaurantUser;

    RestaurantMenuControllerCustomer(CommandInterfaceGet commandInterfaceGet,
                                     RatingsService ratingService, ViewAllergens viewAllergens,
                                     UpdateAllergenList updateAllergenList, ViewDishes viewDishes,
                                     AnnouncementsService announcementsService, FindRestaurantUser findRestaurantUser) {
        this.commandInterfaceGet = commandInterfaceGet;
        this.ratingService = ratingService;
        this.viewAllergens = viewAllergens;
        this.updateAllergenList = updateAllergenList;
        this.viewDishes = viewDishes;
        this.announcementsService = announcementsService;
        this.findRestaurantUser = findRestaurantUser;

    }

    @GetMapping("/viewRestaurantMenu/{username}")
    public String viewMenus(Model model, HttpSession session,@PathVariable String username) {
        restaurantMenus = commandInterfaceGet.executeGet();
        String user_username = (String) session.getAttribute("restaurantCustomer");
        Optional<RestaurantUser> restaurantUser = findRestaurantUser.findUser(user_username);

        if(restaurantUser.isPresent()) {//check if customer exists
            Optional<RestaurantCustomer> restaurantCustomer = findRestaurantUser.findCustomer(restaurantUser.get());
            List<String> customerAllergiesNames = viewAllergens.getCustomerAllergiesNames(restaurantCustomer.get());
            model.addAttribute("restaurantMenus", restaurantMenus);
            model.addAttribute("restaurantCustomer", user_username);
            model.addAttribute("customerFavouriteFoodNames", restaurantCustomer.orElseThrow().getCustomerRatings().stream().map(CustomerRatings::getRestaurantFoodNames).collect(Collectors.toList()));
            model.addAttribute("customerFavouriteFoodDescription", restaurantCustomer.orElseThrow().getCustomerRatings().stream().map(CustomerRatings::getRestaurantFoodDescription).collect(Collectors.toList()));
            model.addAttribute("customerAllergens", viewAllergens.foodsWithAllergens(restaurantCustomer.get()));

            model.addAttribute("topFiveDishes",ratingService.viewTopFiveLikes());

            model.addAttribute("vegetarianDishesPercent",viewDishes.viewVegetarianOrVeganDishesPercent("vegetarian"));
            model.addAttribute("veganDishesPercent",viewDishes.viewVegetarianOrVeganDishesPercent("vegan"));
            model.addAttribute("restaurantCustomer", restaurantCustomer.get().getUserName());

            return "/RestaurantCustomer/restaurantMenu";
        }else{
            return "redirect:/error";
        }
    }

    @PostMapping("/editFavouriteFoodPost")
    public String editFavourites(@RequestParam String foodName, @RequestParam String foodAction,
                                  @RequestParam(name= "rating",required = false) Double rating,HttpSession session,
                                 @RequestParam String foodDescription,
                                 @RequestParam(required = false) Boolean isVegetarian,
                                 @RequestParam(required = false) Boolean isVegan) {

        String username = (String) session.getAttribute("restaurantCustomer");
        Optional<RestaurantUser> existingUser = findRestaurantUser.findUser(username);

        if(existingUser.isPresent()) {//edit ratings

            Optional<RestaurantCustomer> existingCustomer = findRestaurantUser.findCustomer(existingUser.get());
            existingCustomer.ifPresent(restaurantCustomer -> ratingService
                    .editFavorites(restaurantCustomer, foodName, foodAction, rating, foodDescription, isVegetarian,isVegan));

            return "redirect:/RestaurantMenuCustomer/viewRatedDishes/"+username;
        }else{
            return "redirect:/error";
        }

    }


    @GetMapping("viewAllergenList/{username}")
    public String viewAllergenList(Model model, HttpSession session,@PathVariable String username) {

        String user_username = (String) session.getAttribute("restaurantCustomer");
        Optional<RestaurantUser> restaurantUser = findRestaurantUser.findUser(user_username);
        if(restaurantUser.isPresent()) {

            Optional<RestaurantCustomer> restaurantCustomer = findRestaurantUser.findCustomer(restaurantUser.get());
            List<Allergen> allergenList = viewAllergens.getAllAllergens();

            model.addAttribute("allergenList", allergenList);
            model.addAttribute("admin",false);
            model.addAttribute("allergicPeople",viewAllergens.allergicPeopleByAllergens());
            model.addAttribute("customerAllergiesNames",viewAllergens.getCustomerAllergiesNames(restaurantCustomer.get()));
            model.addAttribute("restaurantCustomer", restaurantCustomer.get().getUserName());
            return "AllergensPage";
        }
        return "redirect:/error";
    }


    @GetMapping("viewRatedDishes/{username}")
    public String viewRatedDishes(Model model, HttpSession session,@PathVariable String username) {

        String user_username = (String) session.getAttribute("restaurantCustomer");
        Optional<RestaurantUser> restaurantUser = findRestaurantUser.findUser(user_username);
        if(restaurantUser.isPresent()) {

            Optional<RestaurantCustomer> restaurantCustomer = findRestaurantUser.findCustomer(restaurantUser.get());

            model.addAttribute("customerFavouriteFoods", restaurantCustomer.orElseThrow().getCustomerRatings());
            model.addAttribute("restaurantCustomer", restaurantCustomer.get().getUserName());
            return "/RestaurantCustomer/customerRatedDishes";

        }
        return "redirect:/error";
    }


    @PostMapping("UpdateAllergenList")
    public String updateAllergenList(@RequestParam(name="allergens",required = false) List<String> allergens, HttpSession session){

        String username = (String) session.getAttribute("restaurantCustomer");
        Optional<RestaurantUser> restaurantUser = findRestaurantUser.findUser(username);

        if(allergens==null){
            allergens = new ArrayList<>();
        }

        if(restaurantUser.isPresent()) {//edit new customer allergen list

            Optional<RestaurantCustomer> restaurantCustomer = findRestaurantUser.findCustomer(restaurantUser.get());

            updateAllergenList.saveCustomerAllergens(allergens, restaurantCustomer.get());

            return "redirect:/RestaurantMenuCustomer/viewAllergenList/"+username;
        }

        return "redirect:/error";

    }


    @GetMapping("announcements/{username}")
    public String announcements(Model model, HttpSession session,@PathVariable String username) {
        String user_username = (String) session.getAttribute("restaurantCustomer");
        Optional<RestaurantUser> restaurantUser = findRestaurantUser.findUser(user_username);

        if(restaurantUser.isPresent()) {

            Optional<RestaurantCustomer> restaurantCustomer = findRestaurantUser.findCustomer(restaurantUser.get());
            model.addAttribute("announcements",announcementsService.viewAnnouncements());
            model.addAttribute("admin",false);
            restaurantMenus = commandInterfaceGet.executeGet();
            model.addAttribute("menusCount",restaurantMenus.size()<7);
            model.addAttribute("restaurantCustomer", restaurantCustomer.get().getUserName());
            return "restaurantAnnouncements";
        }
        return "redirect:/error";

    }

}
