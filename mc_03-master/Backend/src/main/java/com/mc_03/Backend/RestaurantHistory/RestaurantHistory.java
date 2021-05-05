package com.mc_03.Backend.RestaurantHistory;

import com.mc_03.Backend.Restaurant.Restaurant;
import com.mc_03.Backend.User.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="restaurant_history")
public class RestaurantHistory {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Users user;

    @ManyToOne
    private Restaurant restaurant;

    @Column(nullable = false)
    private boolean visited;

    @Column
    private Date visitedDate;

    public RestaurantHistory(Users user, Restaurant restaurant)
    {
        this.user = user;
        this.restaurant = restaurant;
    }

    public RestaurantHistory()
    {

    }

    public Users getUsers() {
        return user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean getNotConsidered() {
        return !visited;
    }

    public void setNotConsidered(boolean visited) {
        visitedDate = null;
        this.visited = !visited;
    }

    public Date getVisitedDate() {
        return visitedDate;
    }

    public void setVisitedDate(Date visitedDate) {
        this.visitedDate = visitedDate;
    }

}
