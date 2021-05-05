package com.mc_03.Backend.RestaurantHistory;

import com.mc_03.Backend.Restaurant.Restaurant;
import com.mc_03.Backend.User.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface RestaurantHistoryRepository extends CrudRepository<RestaurantHistory, Long> {

    Collection<RestaurantHistory> findAll();

    RestaurantHistory save(RestaurantHistory restaurantHistorySave);

    Collection<RestaurantHistory> findById(@Param("id") long id);

    Collection<RestaurantHistory> findByUser(@Param("user") Users user);

    Collection<RestaurantHistory> findByRestaurant(@Param("restaurant") Restaurant restaurant);

    @Query(value = "SELECT * FROM restaurant_history JOIN users_table ON restaurant_history.user_id = users_table.id JOIN restaurant_table ON restaurant_table.id = restaurant_history.restaurant_id WHERE users_table.id = :id", nativeQuery=true)
    Collection<RestaurantHistory> findByUserID(@Param("id") long id);

    @Query(value = "SELECT * FROM restaurant_history JOIN users_table ON restaurant_history.user_id = users_table.id JOIN restaurant_table ON restaurant_table.id = restaurant_history.restaurant_id WHERE restaurant_table.id = :id", nativeQuery=true)
    Collection<RestaurantHistory> findByRestaurant(@Param("id") long id);

    long count();

}
