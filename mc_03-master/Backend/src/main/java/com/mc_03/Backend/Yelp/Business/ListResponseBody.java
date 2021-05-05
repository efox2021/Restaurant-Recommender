package com.mc_03.Backend.Yelp.Business;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ListResponseBody implements Serializable {
    public static class Region{
        @JsonProperty("center") public Center center;
        public static class Center{
            @JsonProperty("latitude") public double latitude;
            @JsonProperty("longitude") public double longitude;
        }
    }
    @JsonProperty("region") private Region region;
    @JsonProperty("businesses") private Business[] businesses;
    @JsonProperty("total") private int total;


    public void setRegion(Region region){
        this.region=region;
    }
    public Object getRegion(){
        return this.region;
    }
    public void setBusinesses(Business[] businesses){
        this.businesses=businesses;
    }
    public Business[] getBusinesses(){
        return this.businesses;
    }
    public void setTotal(int total){
        this.total=total;
    }
    public int getTotal(){
        return this.total;
    }
}
