package com.mc_03.Backend.Yelp.YelpReviews;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YelpReviews {
    public static class User{
        @JsonProperty("id") public String id;
        @JsonProperty("profile_url") public String profileURL;
        @JsonProperty("name") public String name;
        @JsonProperty("image_url") public String imageURL;
    }

    @JsonProperty("id") public String id;
    @JsonProperty("text") public String text;
    @JsonProperty("url") public String url;
    @JsonProperty("rating") public int rating;
    @JsonProperty("time_created") public String timeCreated;

}
