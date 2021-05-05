package com.mc_03.Backend.Restaurant;

import com.mc_03.Backend.Address.Address;
import com.mc_03.Backend.Address.AddressRepository;
import com.mc_03.Backend.Cookie.Cookie;
import com.mc_03.Backend.Cookie.CookieRepository;
import com.mc_03.Backend.RestaurantHistory.RestaurantHistory;
import com.mc_03.Backend.RestaurantHistory.RestaurantHistoryRepository;
import com.mc_03.Backend.User.UserRepository;
import com.mc_03.Backend.User.Users;
import com.mc_03.Backend.Yelp.Business.BusinessRepository;
import com.mc_03.Backend.Yelp.Business.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BusinessRepository businessRepository;
    
    @Autowired
    private RestaurantHistoryRepository restaurantHistoryRepository;
    
    @Autowired
    private CookieRepository cookieRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/recommendation/byZipCode")
    public @ResponseBody
    Restaurant requestRandomRestaurant(@RequestParam(name = "zipCode", required = true) int zipCode) throws Exception {

        if(zipCode <= 99999 && zipCode >= 0)
        {
            ArrayList<Restaurant> result = (ArrayList<Restaurant>) restaurantRepository.findByRandomZipCode(zipCode);
            if(result.isEmpty())
            {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("location",  Integer.toString(zipCode));
                Business[] returnList = businessRepository.findBusinesses(params);
                if(returnList.length == 0)
                {
                    return null;
                }
                for(int i = 0; i < returnList.length; i++)
                {
                    Address restAddress = new Address(returnList[i]);
                    addressRepository.save(restAddress);
                    Restaurant rest = new Restaurant(returnList[i], restAddress);
                    restaurantRepository.save(rest);
                }
                ArrayList<Restaurant> result1 = (ArrayList<Restaurant>) restaurantRepository.findByRandomZipCode(zipCode);
                return(result1.get(0));
            }
            else {
                return result.get(0);
            }
        }
        return null;
    }

    @GetMapping(path="/recommendation/loggedIn/byZipCode")
    public @ResponseBody
    HashMap<String, Object> requestLoggedInRestaurant(@RequestBody Map<String, String> request) throws Exception {



        int zipCode = Integer.parseInt(request.get("zipCode"));
        Long uid = Long.parseLong(request.get("user_id"));
        String cookie = request.get("cookieID");
        ArrayList<Cookie> tempCookie = (ArrayList<Cookie>) cookieRepository.findBycookieId(cookie);
        HashMap<String, Object> returnMapping = new HashMap<>();
        //Check if logged in
        if(tempCookie.isEmpty())
        {
            returnMapping.put("Status", "User_Not_Logged_In");
            return(returnMapping);
        }
        else {
            if(tempCookie.get(0).getUser().getId() != uid)
            {
                returnMapping.put("Status","Cookie_And_User_Dont_Match");
                return(returnMapping);
            }
        }
        //Review Recommendations
        historyChecker(uid);

        //Check if valid zipCode
        if(zipCode <= 99999 && zipCode >= 0)
        {
            ArrayList<Restaurant> result = (ArrayList<Restaurant>) restaurantRepository.findByHistory(zipCode, uid);
            if(result.isEmpty())
            {
                //Updating when there are not more recommendations in the DB
                HashMap<String, String> params = new HashMap<>();
                params.put("location",  Integer.toString(zipCode));
                Business[] returnList = businessRepository.findBusinesses(params);
                for(int i = 0; i < returnList.length; i++)
                {
                    ArrayList<Restaurant> inDB = (ArrayList<Restaurant>) restaurantRepository.findByyelpID(returnList[i].id);
                    if(inDB.isEmpty())
                    {
                        Address restAddress = new Address(returnList[i]);
                        addressRepository.save(restAddress);
                        Restaurant rest = new Restaurant(returnList[i], restAddress);
                        restaurantRepository.save(rest);
                    }
                }
                ArrayList<Restaurant> result1 = (ArrayList<Restaurant>) restaurantRepository.findByHistory(zipCode,uid);
                if(result1.isEmpty())
                {
                    returnMapping.put("Status","No_Restaurants_Found");
                }
                else {
                    returnMapping.put("Status","Success");
                    returnMapping.put("Restaurant",result1.get(0));
                }
                return(returnMapping);
            }
            else {
                returnMapping.put("Status","Success");
                returnMapping.put("Restaurant",result.get(0));
                return(returnMapping);
            }
        }
        else {
            returnMapping.put("Status", "Invalid_ZipCode");
            return(returnMapping);
        }
    }

    private void historyChecker(Long uid)
    {
        Optional<Users> temp = userRepository.findById(uid);
        if(!(temp.isPresent()))
        {
            return;
        }
        ArrayList<RestaurantHistory> rhList = (ArrayList<RestaurantHistory>) restaurantHistoryRepository.findByUser(temp.get());
        Date today = new Date();
        for(int i = 0; i < rhList.size(); i++)
        {
            if(today.getTime() - rhList.get(i).getVisitedDate().getTime() > 86400000 && rhList.get(i).getNotConsidered())
            {
                restaurantHistoryRepository.delete(rhList.get(i));
            }
            else if(today.getTime() - rhList.get(i).getVisitedDate().getTime() > 259200000 && rhList.get(i).isVisited())
            {
                restaurantHistoryRepository.delete(rhList.get(i));
            }
        }
    }

}
