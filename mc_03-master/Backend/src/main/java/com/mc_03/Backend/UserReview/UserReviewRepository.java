package com.mc_03.Backend.UserReview;

import com.mc_03.Backend.Restaurant.Restaurant;
import com.mc_03.Backend.User.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Repository
public interface UserReviewRepository extends CrudRepository<UserReview, Long> {

    Collection<UserReview> findAll();
    UserReview save(UserReview userReviewSave);

    Collection<UserReview> findByreviewId(@Param("reviewId") int rid);


    Collection<UserReview> findByuserId(@Param("userId") long id);


//    @Query(value = "SELECT * from restaurant_table JOIN restaurant_address ON restaurant_table.address = restaurant_address.address_id WHERE restaurant_address.zip_code = :zipCode = false ORDER BY RAND()", nativeQuery=true)
//    Collection<Restaurant> findByRandomZipCode(@Param("zipCode") int zipCode);

    long count();
}
