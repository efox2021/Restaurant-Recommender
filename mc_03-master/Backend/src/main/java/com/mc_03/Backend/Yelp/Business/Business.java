package com.mc_03.Backend.Yelp.Business;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Business implements Serializable {
    public static class Coordinates {
        @JsonProperty("longitude") public double longitude;
        @JsonProperty("latitude") public double latitude;
    }
    public static class Categories {
        @JsonProperty("alias") public String alias;
        @JsonProperty("title") public String title;
    }
    public static class Location {
        @JsonProperty("address1") public String address1;
        @JsonProperty("address2") public String address2;
        @JsonProperty("address3") public String address3;
        @JsonProperty("city") public String city;
        @JsonProperty("country") public String country;
        @JsonProperty("display_address") public String[] displayAddress;
        @JsonProperty("state") public String state;
        @JsonProperty("zip_code") public String zipCode;
    }

    @JsonProperty("location") public Location location;
    @JsonProperty("categories") public Categories[] categories;
    @JsonProperty("coordinates") public Coordinates coordinates;
    @JsonProperty("display_phone") public String displayPhone;
    @JsonProperty("distance") public double distance;
    @JsonProperty("id") public String id;
    @JsonProperty("alias") public String alias;
    @JsonProperty("image_url") public String imageURL;
    @JsonProperty("is_closed") public boolean isClosed;
    @JsonProperty("name") public String name;
    @JsonProperty("phone") public String phone;
    @JsonProperty("price") public String price;
    @JsonProperty("rating") public double rating;
    @JsonProperty("review_count") public int reviewCount;
    @JsonProperty("url") public String URL;
    @JsonProperty("transactions") public String[] transactions;



}

