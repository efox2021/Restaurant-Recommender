package com.mc_03.Backend.Address;


import com.mc_03.Backend.Yelp.Business.Business;

import javax.persistence.*;

@Entity
@Table(name="restaurantAddress")
public class Address {

    @Id
    @GeneratedValue
    private int addressId;

    @Column
    private String line1;

    @Column
    private String line2;

    @Column
    private String line3;

    @Column
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private int zipCode;

    public Address(Business bus) {
        line1 = bus.location.address1;
        line2 = bus.location.address2;
        line3 = bus.location.address3;
        country = bus.location.country;
        city = bus.location.city;
        state = bus.location.state;
        zipCode = Integer.parseInt(bus.location.zipCode);
    }

    public Address(int addressId, String line1,  String line2, String line3, String city, String state, int zipCode)
    {
        this.addressId = addressId;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Address() {

    }

    public int getAddressId() {
        return addressId;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        if(zipCode < 99999 && zipCode > 0)
            this.zipCode = zipCode;
        else
            throw new IllegalArgumentException();
    }
}
