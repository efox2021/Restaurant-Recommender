package com.mc_03.Backend.Restaurant;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



/**
 * Repository class for <code>Restraunt</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data See here: http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 *
 * @author Carson Meyer
 */


@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long>{

    Collection<Restaurant> findAll();

    Collection<Restaurant> findById(@Param("id") int id);

    Restaurant save(Restaurant restaurantSave);

    @Query(value = "SELECT * from restaurant_table JOIN restaurant_address ON restaurant_table.address = restaurant_address.address_id WHERE restaurant_address.zip_code = :zipCode AND restaurant_table.is_closed = false ORDER BY RAND()", nativeQuery=true)
    Collection<Restaurant> findByRandomZipCode(@Param("zipCode") int zipCode);

    @Query(value = "SELECT * FROM restaurant_table rt JOIN restaurant_address ON rt.address = restaurant_address.address_id WHERE restaurant_address.zip_code = :zipCode AND rt.is_closed = false AND NOT EXISTS (SELECT * FROM restaurant_history JOIN users_table ON users_table.id = restaurant_history.user_id WHERE users_table.id = :uid AND restaurant_history.restaurant_id = rt.id) ORDER BY RAND()", nativeQuery=true)
    Collection<Restaurant> findByHistory(@Param("zipCode") int zipCode, @Param("uid") Long uid);

    Collection<Restaurant> findByyelpID(@Param("yelpID") String yelpID);

    long count();


}
