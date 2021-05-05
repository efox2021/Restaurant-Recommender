package com.mc_03.Backend.RestaurantHistory;

import com.mc_03.Backend.Cookie.Cookie;
import com.mc_03.Backend.Cookie.CookieRepository;
import com.mc_03.Backend.Restaurant.Restaurant;
import com.mc_03.Backend.Restaurant.RestaurantRepository;
import com.mc_03.Backend.User.UserRepository;
import com.mc_03.Backend.User.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RestaurantHistoryController {

    //Get recent history for user

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CookieRepository cookieRepository;

    @Autowired
    private RestaurantHistoryRepository restaurantHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/restaurantHistory/pass")
    public @ResponseBody
    HashMap<String, String> restaurantPass(@RequestBody Map<String, String> request) {
        HashMap<String, String> returnMap = new HashMap<>();
        if(request.get("restaurant_id") == null || request.get("user_id") == null || request.get("cookieID") == null)
        {
            returnMap.put("Status", "Missing_Data");
            return(returnMap);
        }
        int rid = Integer.parseInt(request.get("restaurant_id"));
        Long uid = Long.parseLong(request.get("user_id"));
        String cookie = request.get("cookieID");
        ArrayList<Cookie> tempCookie = (ArrayList<Cookie>) cookieRepository.findBycookieId(cookie);
        Optional<Users> tempUser = userRepository.findById(uid);
        if(!(tempUser.isPresent()))
        {
            returnMap.put("Status", "User_ID_Not_Valid");
            return(returnMap);
        }
        if(tempCookie.isEmpty())
        {
            returnMap.put("Status","Not_Logged_In");
            return(returnMap);
        }

        ArrayList<Restaurant> tempRest = (ArrayList<Restaurant>) restaurantRepository.findById(rid);
        if(tempRest.isEmpty())
        {
            returnMap.put("Status", "User_ID_OR_Restaurant_ID_Incorrect");
            return(returnMap);
        }

        RestaurantHistory toStore = new RestaurantHistory(tempUser.get(), tempRest.get(0));
        toStore.setNotConsidered(true);
        toStore.setVisitedDate(new Date());

        restaurantHistoryRepository.save(toStore);
        returnMap.put("Status", "Saved");
        return(returnMap);
    }

    @PostMapping("/restaurantHistory/visited")
    public @ResponseBody
    HashMap<String, String> restaurantVisited(@RequestBody Map<String, String> request) {
        HashMap<String, String> returnMap = new HashMap<>();
        if(request.get("restaurant_id") == null || request.get("user_id") == null || request.get("cookieID") == null)
        {
            returnMap.put("Status", "Missing_Data");
            return(returnMap);
        }
        else {
            System.out.println("passed");
        }
        int rid = Integer.parseInt(request.get("restaurant_id"));
        Long uid = Long.parseLong(request.get("user_id"));
        String cookie = request.get("cookieID");

        ArrayList<Cookie> tempCookie = (ArrayList<Cookie>) cookieRepository.findBycookieId(cookie);
        Optional<Users> tempUser = userRepository.findById(uid);
        if(!(tempUser.isPresent()))
        {
            returnMap.put("Status", "User_ID_Not_Valid");
            return(returnMap);
        }
        if(tempCookie.isEmpty())
        {
            returnMap.put("Status","Not_Logged_In");
            return(returnMap);
        }
        else {
            if(tempCookie.get(0).getUser().getId() != uid)
            {
                returnMap.put("Status","Cookie_And_User_Dont_Match");
                return(returnMap);
            }
        }



        ArrayList<Restaurant> tempRest = (ArrayList<Restaurant>) restaurantRepository.findById(rid);
        if(tempRest.isEmpty())
        {
            returnMap.put("Status", "Restaurant_ID_Not_Valid");
            return(returnMap);
        }

        RestaurantHistory toStore = new RestaurantHistory(tempUser.get(), tempRest.get(0));
        toStore.setVisited(true);
        toStore.setVisitedDate(new Date());

        restaurantHistoryRepository.save(toStore);
        returnMap.put("Status", "Saved");
        return(returnMap);
    }

}
