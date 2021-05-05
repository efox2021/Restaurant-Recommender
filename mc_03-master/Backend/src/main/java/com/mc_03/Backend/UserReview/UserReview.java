package com.mc_03.Backend.UserReview;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name= "userReviewsTable")
public class UserReview implements Serializable {
    @Id
    @GeneratedValue
    private int reviewId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String cookieID;

    @Column(nullable = false)
    private int restaurantId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int rating;

    public int getRestaurantId() {
        return restaurantId;
    }

    public int getRating() {
        return rating;
    }

    public int getReviewId() {
        return reviewId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public String getCookieID() {
        return cookieID;
    }
}
