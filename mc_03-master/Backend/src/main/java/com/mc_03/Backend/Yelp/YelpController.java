package com.mc_03.Backend.Yelp;

import com.mc_03.Backend.Yelp.YelpReviews.YelpReviewsRepository;
import com.mc_03.Backend.Yelp.YelpReviews.YelpReviews;
import com.mc_03.Backend.Yelp.Business.BusinessRepository;
import com.mc_03.Backend.Yelp.Business.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class YelpController {
    @Autowired public BusinessRepository businessRepository;
    @Autowired public YelpReviewsRepository userReviewRepo;

    @GetMapping("/businesses")
    public Business[] findBusinesses(@RequestParam Map<String, String> parameters) throws Exception {
        return businessRepository.findBusinesses(parameters);
    }

    @GetMapping("/findReviews")
    public YelpReviews[] findReviews(@RequestParam String id) throws Exception {
        return userReviewRepo.findReviews(id);
    }



    public void setBusinessRepository(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    public void setUserReviewRepo(YelpReviewsRepository userReviewRepo) {
        this.userReviewRepo = userReviewRepo;
    }
}
