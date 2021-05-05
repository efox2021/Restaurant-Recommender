package com.mc_03.Backend.Yelp.YelpReviews;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class YelpReviewsListResponseBody implements Serializable {
    @JsonProperty("possible_languages") private String[] languages;
    @JsonProperty("reviews") private YelpReviews[] reviews;
    @JsonProperty("total") private int total;


    public void setLanguages(String[] languages){
        this.languages=languages;
    }
    public String[] getLanguages(){
        return this.languages;
    }
    public void setReviews(YelpReviews[] reviews){
        this.reviews=reviews;
    }
    public YelpReviews[] getReviews(){
        return this.reviews;
    }
    public void setTotal(int total){
        this.total=total;
    }
    public int getTotal(){
        return this.total;
    }
}
