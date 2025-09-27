package com.example.student_restaurant_appplication_development.controller;

import com.example.student_restaurant_appplication_development.model.*;
import com.example.student_restaurant_appplication_development.repository.*;
import com.example.student_restaurant_appplication_development.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/restaurantMenu/")
public class RestaurantMenuControllerAdmin {


    List<RestaurantMenu> restaurantMenus;
    List<RatingFoodsCounter> favouriteFoodsCounters;

    CommandInterfacePost commandInterfacePostInsert;
    CommandInterfacePost commandInterfacePostUpdate;
    CommandInterfacePost commandInterfacePostDelete;
    CommandInterfaceGet commandInterfaceGet;
    RatingsService favouritesService;
    StringBuilder action;
    List<String> menuDays;
    StringBuilder messageWithMenu;
    ViewAllergens viewAllergens;
    UpdateAllergenList updateAllergenList;
    ViewDishes viewDishes;
    AnnouncementsService announcementsService;
    ViewMenus viewMenus;
    FindRestaurantUser findRestaurantUser;
    DeleteMenu deleteMenu;

    public RestaurantMenuControllerAdmin(@Qualifier("insertMenu") CommandInterfacePost commandInterfacePostInsert,
                                         @Qualifier("updateMenu") CommandInterfacePost commandInterfacePostUpdate,
                                         DeleteMenu deleteMenu,
                                         CommandInterfaceGet commandInterfaceGet,
                                         RatingsService favouritesService, ViewAllergens viewAllergens,
                                         UpdateAllergenList updateAllergenList, ViewDishes viewDishes,
                                         AnnouncementsService announcementsService, ViewMenus viewMenus,
                                         FindRestaurantUser findRestaurantUser) {
        this.commandInterfacePostInsert = commandInterfacePostInsert;
        this.commandInterfacePostUpdate = commandInterfacePostUpdate;
        this.deleteMenu = deleteMenu;
        this.commandInterfaceGet = commandInterfaceGet;
        this.favouritesService = favouritesService;
        this.viewAllergens = viewAllergens;
        this.updateAllergenList = updateAllergenList;
        this.viewDishes = viewDishes;
        this.announcementsService = announcementsService;
        this.viewMenus = viewMenus;
        this.findRestaurantUser = findRestaurantUser;

    }

    @GetMapping("/viewRestaurantMenu/{username}")
    public String viewMenus(Model model, @PathVariable String username, HttpSession session) {

        String user_username = (String) session.getAttribute("restaurantAdmin");// get attribute from session
        Optional<RestaurantUser> restaurantAdmin = findRestaurantUser.findUser(user_username);//check
        boolean showMessage = false;

        if(restaurantAdmin.isPresent()) {//if admin exists

            restaurantMenus = commandInterfaceGet.executeGet();

            model.addAttribute("restaurantMenus", restaurantMenus);
            model.addAttribute("menusCount",restaurantMenus.size()<7);//if days of menu are less than seven
            model.addAttribute("restaurantAdmin", restaurantAdmin.get().getUsername());
            if(action!=null) {
                model.addAttribute("action", action.toString());
                model.addAttribute("messageWithMenu", messageWithMenu.toString());
                showMessage = true;
            }
            else{
                model.addAttribute("action","");
                model.addAttribute("messageWithMenu","");
            }

            model.addAttribute("showMessage",showMessage);


            model.addAttribute("topFiveDishes",favouritesService.viewTopFiveLikes());

            model.addAttribute("vegetarianDishesPercent",viewDishes.viewVegetarianOrVeganDishesPercent("vegetarian"));
            model.addAttribute("veganDishesPercent",viewDishes.viewVegetarianOrVeganDishesPercent("vegan"));
            model.addAttribute("timeNow", LocalTime.now().getHour()<12);
            action = null;
            return "/RestaurantAdmin/viewRestaurantMenu";
        }

            return "redirect:/error";
    }

    @GetMapping("restaurantRatings/{username}")
    public String viewRatings(Model model,HttpSession session, @PathVariable String username){
        String user_username = (String) session.getAttribute("restaurantAdmin");
        Optional<RestaurantUser> restaurantAdmin = findRestaurantUser.findUser(user_username);
        if(restaurantAdmin.isPresent()) {
            favouriteFoodsCounters = favouritesService.viewLikes();

            model.addAttribute("restaurantAdmin", restaurantAdmin.get().getUsername());
            model.addAttribute("favouriteFoodsCounters", favouriteFoodsCounters);
            restaurantMenus = commandInterfaceGet.executeGet();
            model.addAttribute("menusCount",restaurantMenus.size()<7);
            model.addAttribute("mostRatedDishes",favouritesService.most_rated_dishes());
            model.addAttribute("positiveRatedDishes",favouritesService.positive_or_negative_rated_foods_percent("positive"));
            model.addAttribute("negativeRatedDishes",favouritesService.positive_or_negative_rated_foods_percent("negative"));
            model.addAttribute("vegetarianPositiveRatedDishes",
                    favouritesService.positive_rated_vegetarian_or_vegan_foods_percent("vegetarian"));
            model.addAttribute("veganPositiveRatedDishes",
                    favouritesService.positive_rated_vegetarian_or_vegan_foods_percent("vegan"));


            return "/RestaurantAdmin/restaurantDishesRatings";
        }

        return "redirect:/error";
    }


    @GetMapping("viewAllergenList/{username}")
    public String viewAllergenList(Model model, HttpSession session,@PathVariable String username) {

        String user_username = (String) session.getAttribute("restaurantAdmin");
        Optional<RestaurantUser> restaurantAdmin = findRestaurantUser.findUser(user_username);
        if(restaurantAdmin.isPresent()) {
            List<Allergen> allergenList = viewAllergens.getAllAllergens();


            model.addAttribute("restaurantAdmin", restaurantAdmin.get().getUsername());
            model.addAttribute("allergenList", allergenList);
            model.addAttribute("admin",true);
            model.addAttribute("allergicPeople",viewAllergens.allergicPeopleByAllergens());
            restaurantMenus = commandInterfaceGet.executeGet();
            model.addAttribute("menusCount",restaurantMenus.size()<7);
            return "AllergensPage";
        }
        return "redirect:/error";
    }


    @PostMapping("/addRestaurantMenuPost")
    public String insertMenu(@RequestParam("day") String day,HttpSession session,
                             @RequestParam(name="breakfast",required = false) List<String> breakfast,
                             @RequestParam(name="breakfastDescription",required = false) List<String> breakfastDescription,
                             @RequestParam(name="breakfastIsVegetarian",required = false) List<String> breakfastIsVegetarian,
                             @RequestParam(name="breakfastIsVegan",required = false) List<String> breakfastIsVegan,
                             @RequestParam(name="lunch",required = false) List<String> lunch,
                             @RequestParam(name="lunchDescription",required = false) List<String> lunchDescription,
                             @RequestParam(name="lunchIsVegetarian",required = false) List<String> lunchIsVegetarian,
                             @RequestParam(name="lunchIsVegan",required = false) List<String> lunchIsVegan,
                             @RequestParam(name="dinner",required = false) List<String> dinner,
                             @RequestParam(name="dinnerDescription",required = false) List<String> dinnerDescription,
                             @RequestParam(name="dinnerIsVegetarian",required = false) List<String> dinnerIsVegetarian,
                             @RequestParam(name="dinnerIsVegan",required = false) List<String> dinnerIsVegan) {
        String username = (String) session.getAttribute("restaurantAdmin");
        Optional<RestaurantUser> restaurantAdmin = findRestaurantUser.findUser(username);

        if(restaurantAdmin.isPresent()) {//insert menu

            breakfast.remove("start");
            breakfastDescription.remove("start");
            lunch.remove("start");
            lunchDescription.remove("start");
            dinner.remove("start");
            dinnerDescription.remove("start");

            if(!breakfast.isEmpty() && !breakfastDescription.isEmpty() && !lunch.isEmpty()
                    && !lunchDescription.isEmpty() && !dinner.isEmpty() && !dinnerDescription.isEmpty()){
                RestaurantMenu restaurantMenu = commandInterfacePostInsert.executePost(null,day,breakfast,
                        lunch,dinner,breakfastDescription,lunchDescription,dinnerDescription,breakfastIsVegetarian,
                        lunchIsVegetarian,dinnerIsVegetarian,breakfastIsVegan,lunchIsVegan,dinnerIsVegan);
                action = new StringBuilder().append("προστέθηκε");
                messageWithMenu = new StringBuilder().append(restaurantMenu.getDay());
                return "redirect:/restaurantMenu/viewRestaurantMenu/" + username;
            }
            else{
                return "redirect:/restaurantMenu/addRestaurantMenu/"+username;
            }
        }

        return "redirect:/error";
    }


    @GetMapping("/addRestaurantMenu/{username}")
    public String getInsertMenu(Model model,@PathVariable String username, HttpSession session) {

        Optional<RestaurantUser> restaurantAdmin = findRestaurantUser.findUser(username);
        if(restaurantAdmin.isPresent()) {
            model.addAttribute("restaurantAdmin", restaurantAdmin.get().getUsername());
            model.addAttribute("menuDays", Collections.unmodifiableList(getAvailableDays()));
            model.addAttribute("availableDishes", Collections.unmodifiableMap(viewDishes.viewDishes()));
            restaurantMenus = commandInterfaceGet.executeGet();
            model.addAttribute("menusCount",restaurantMenus.size()<7);
        }

        return "/RestaurantAdmin/addRestaurantMenu";

    }

    @GetMapping("showAllDishes/{username}")
    public String showDishes(Model model, HttpSession session,@PathVariable String username) {
        String user_username = (String) session.getAttribute("restaurantAdmin");
        Optional<RestaurantUser> restaurantAdmin = findRestaurantUser.findUser(user_username);
        if(restaurantAdmin.isPresent()) {
            model.addAttribute("restaurantAdmin", restaurantAdmin.get().getUsername());
            model.addAttribute("availableDishes", viewDishes.viewDishes());
            restaurantMenus = commandInterfaceGet.executeGet();
            model.addAttribute("menusCount",restaurantMenus.size()<7);
            model.addAttribute("allVegetarianDishes",viewDishes.viewAllVegetarianOrVeganDishesPercent("vegetarian"));
            model.addAttribute("allVeganDishes",viewDishes.viewAllVegetarianOrVeganDishesPercent("vegan"));
            return "/RestaurantAdmin/restaurantDishes";
        }
        return "redirect:/error";
    }


    @GetMapping("/viewUpdateRestaurantMenu/{restaurantMenuId}")
    public String viewUpdateMenu(Model model,@PathVariable Integer restaurantMenuId, HttpSession session) {
        String username = (String) session.getAttribute("restaurantAdmin");//check if admin exists
        Optional<RestaurantUser> restaurantAdmin = findRestaurantUser.findUser(username);
        Optional<RestaurantMenu> found_restaurantMenu = viewMenus.findRestaurantMenu(restaurantMenuId);//check if menu exists

        if(restaurantAdmin.isPresent() && found_restaurantMenu.isPresent()) {


            model.addAttribute("menuDays", Collections.unmodifiableList(getAvailableDays()));
            model.addAttribute("restaurantMenus", found_restaurantMenu.get());
            model.addAttribute("restaurantAdmin", restaurantAdmin.get().getUsername());
            restaurantMenus = commandInterfaceGet.executeGet();
            model.addAttribute("menusCount",restaurantMenus.size()<7);
            return "/RestaurantAdmin/updateRestaurantMenu";
        }

        return "redirect:/error";
    }

    @PostMapping("/updateRestaurantMenuPost")
    public String updateMenu(@RequestParam("restaurantMenuId") Integer id , HttpSession session,
                            @RequestParam("day") String menuDay,
                             @RequestParam("breakfast") List<String> breakfast,
                             @RequestParam("breakfastDescription") List<String> breakfastDescription,
                             @RequestParam(name="breakfastIsVegetarian", required = false) List<String> breakfastIsVegetarian,
                             @RequestParam(name="breakfastIsVegan", required = false) List<String> breakfastIsVegan,
                             @RequestParam("lunch") List<String> lunch,
                             @RequestParam("lunchDescription") List<String> lunchDescription,
                             @RequestParam(name="lunchIsVegetarian",required = false) List<String> lunchIsVegetarian,
                             @RequestParam(name="lunchIsVegan",required = false) List<String> lunchIsVegan,
                             @RequestParam("dinner") List<String> dinner,
                             @RequestParam("dinnerDescription") List<String> dinnerDescription,
                             @RequestParam(name="dinnerIsVegetarian",required = false) List<String> dinnerIsVegetarian,
                             @RequestParam(name="dinnerIsVegan",required = false) List<String> dinnerIsVegan){

        String username = (String) session.getAttribute("restaurantAdmin");
        Optional<RestaurantUser> restaurantAdmin = findRestaurantUser.findUser(username);

        if(restaurantAdmin.isPresent()) {//update menu

            breakfast.remove("start");
            breakfastDescription.remove("start");
            lunch.remove("start");
            lunchDescription.remove("start");
            dinner.remove("start");
            dinnerDescription.remove("start");

            RestaurantMenu updatedRestaurantMenu = commandInterfacePostUpdate.executePost(id,menuDay,breakfast,lunch,
                    dinner,breakfastDescription,lunchDescription,dinnerDescription,breakfastIsVegetarian,
                    lunchIsVegetarian,dinnerIsVegetarian,breakfastIsVegan,lunchIsVegan,dinnerIsVegan);
            action = new StringBuilder().append("άλλαξε");//parameter to show in front-end
            messageWithMenu = new StringBuilder().append(updatedRestaurantMenu.getDay());
            return "redirect:/restaurantMenu/viewRestaurantMenu/"+username;
        }

        return "redirect:/error";
    }


    @PostMapping("/deleteRestaurantMenuPost")
    public String deleteMenu(@RequestParam("restaurantMenuId") Integer id , HttpSession session){

        String username = (String) session.getAttribute("restaurantAdmin");
        Optional<RestaurantUser> restaurantAdmin = findRestaurantUser.findUser(username);

        if(restaurantAdmin.isPresent()) {//delete the menu
            RestaurantMenu deletedRestaurantMenu = deleteMenu.executePost(id);
            action = new StringBuilder().append("διαγράφηκε");//parameter to show in front-end
            messageWithMenu = new StringBuilder().append(deletedRestaurantMenu.getDay());
            return "redirect:/restaurantMenu/viewRestaurantMenu/"+username;
        }

        return "redirect:/error";
    }

    @PostMapping("UpdateAllergenList")
    public String updateAllergenList(@RequestParam(name="allergens",required = false) List<String> allergens, HttpSession session,
                                     @RequestParam("action") String action){

        String username = (String) session.getAttribute("restaurantAdmin");
        Optional<RestaurantUser> restaurantAdmin = findRestaurantUser.findUser(username);

        if(restaurantAdmin.isPresent()) {
            if(action.equals("all")){//action is to check if admin added or removed an allergen
                if(allergens!=null) {
                    if(!allergens.isEmpty()) {
                        updateAllergenList.saveNewAllergens(allergens);
                    }
                }
            }
            else{
                updateAllergenList.updateNewAllergens(action);
            }

            return "redirect:/restaurantMenu/viewAllergenList/"+username;
        }

        return "redirect:/error";

    }

    @PostMapping("deleteDish")
    public String deleteDish(@RequestParam("dishId") Long dishId, HttpSession session){
        String username = (String) session.getAttribute("restaurantAdmin");
        Optional<RestaurantUser> restaurantAdmin = findRestaurantUser.findUser(username);
        if(restaurantAdmin.isPresent()) {

            viewDishes.deleteDish(dishId);
            return "redirect:/restaurantMenu/showAllDishes/"+username;
        }

        return "redirect:/error";
    }



    private List<String> getAvailableDays(){
        restaurantMenus = commandInterfaceGet.executeGet();// get available days
        menuDays = new ArrayList<>();
        menuDays.add("Δευτέρα");
        menuDays.add("Τρίτη");
        menuDays.add("Τετάρτη");
        menuDays.add("Πέμπτη");
        menuDays.add("Παρασκευή");
        menuDays.add("Σάββατο");
        menuDays.add("Κυριακή");

        for (RestaurantMenu restaurantMenu : restaurantMenus) {//remove days already exist
            menuDays.remove(restaurantMenu.getDay());
        }

        return menuDays;
    }


    @GetMapping("announcements/{username}")
    public String announcements(Model model, HttpSession session,@PathVariable String username){
        String user_username = (String) session.getAttribute("restaurantAdmin");
        Optional<RestaurantUser> restaurantAdmin = findRestaurantUser.findUser(user_username);

        if(restaurantAdmin.isPresent()) {
            model.addAttribute("announcements",announcementsService.viewAnnouncements());
            model.addAttribute("admin",true);
            restaurantMenus = commandInterfaceGet.executeGet();
            model.addAttribute("menusCount",restaurantMenus.size()<7);
            model.addAttribute("restaurantAdmin", restaurantAdmin.get().getUsername());
            return "restaurantAnnouncements";
        }
        return "redirect:/error";

    }

    @PostMapping("updateAnnouncementList")
    public String updateAnnouncementsList(@RequestParam(name="announcementTitle",required = false)
                                              List<String> announcementTitleList,
                                          @RequestParam(name="announcementDescription",required = false)
                                          List<String> announcementDescriptionList,
                                          @RequestParam(name="announcementId",required = false) Long announcementId,
                                          HttpSession session,
                                          @RequestParam("action") String action){


        announcementTitleList.remove("start");
        announcementDescriptionList.remove("start");
        String username = (String) session.getAttribute("restaurantAdmin");
        Optional<RestaurantUser> restaurantAdmin = findRestaurantUser.findUser(username);
        //action is to check if admin added or removed an announcement
        if(restaurantAdmin.isPresent()) {
            if(action.equals("add")){
                if(!announcementTitleList.isEmpty() && !announcementDescriptionList.isEmpty()) {
                    announcementsService.addAnnouncement(announcementTitleList, announcementDescriptionList);
                }
            }
            else{

                announcementsService.removeAnnouncement(Long.valueOf(action));
            }

            return "redirect:/restaurantMenu/announcements/"+username;
        }

        return "redirect:/error";
    }


}
