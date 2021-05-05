package com.mc_03.Backend.Restaurant;

import com.mc_03.Backend.Address.Address;
import com.mc_03.Backend.Yelp.Business.Business;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * This is going to lay the foundation for data that we keep on Restaurant! Looking to keep track of the following data
 * - Name
 * - Location/Address
 * - Cuisine Type
 * - Date Added To DB?
 * - More to come here, this is just to get us started.
 */
@Entity
@Table(name = "restaurantTable")
public class Restaurant implements Serializable {



    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne()
    @JoinColumn(name="Address", nullable = false)
    private Address address;

    @Column
    private double longitude;

    @Column
    private double latitude;

    @Column
    private String alias;

    @Column
    private String cusAlias;

    @Column
    private String cusTitle;

    @Column
    private String phone;

    @Column(nullable = false, unique = true)
    private String yelpID;

    @Column
    private boolean isClosed;

    @Column
    private String price;

    @Column
    private double yelpRating;

    @Column
    private int reviewCount;

    @Column
    private String URL;


    @Column(nullable = false)
    private Date dateAdded;


    public Restaurant() {

    }

    public Restaurant(String rname, Address address, Date dateAdded, double yelpRating){
        this.name = rname;
        this.address = address;
        this.dateAdded = dateAdded;
        this.yelpRating = yelpRating;
    }

    public Restaurant(Business bus, Address ad)
    {
        name = bus.name;
        address = ad;
        longitude = bus.coordinates.longitude;
        latitude = bus.coordinates.latitude;
        alias = bus.alias;
        cusAlias = bus.categories[0].alias;
        cusTitle = bus.categories[0].title;
        phone = bus.phone;
        yelpID = bus.id;
        isClosed = bus.isClosed;
        price = bus.price;
        yelpRating = bus.rating;
        reviewCount = bus.reviewCount;
        URL = bus.URL;
        dateAdded = new Date();

    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getYelp_id() {
        return yelpID;
    }

    public void setYelp_id(String yelp_id) {
        this.yelpID = yelp_id;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getYelpRating() {
        return yelpRating;
    }

    public void setYelpRating(double yelpRating) {
        this.yelpRating = yelpRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getCusAlias() {
        return cusAlias;
    }

    public void setCusAlias(String cusAlias) {
        this.cusAlias = cusAlias;
    }

    public String getCusTitle() {
        return cusTitle;
    }

    public void setCusTitle(String cusTitle) {
        this.cusTitle = cusTitle;
    }
}
