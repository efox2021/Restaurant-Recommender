package com.mc_03.Backend.Cookie;

import com.mc_03.Backend.User.Users;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cookieTable")
public class Cookie {

    @ManyToOne(cascade = CascadeType.ALL)
    private Users user;

    /** 32-length hexadecimal */
    @GenericGenerator(name = "CookieGenerator", strategy = "com.mc_03.Backend.Cookie.CookieGenerator")
    @GeneratedValue(generator = "CookieGenerator")
    @Column(columnDefinition = "CHAR(32)", nullable = false)
    @Id
    private String cookieId;

    @Column(nullable = false)
    private Date dateAdded;

    @Column(nullable = false)
    private Date lastLoggedIn;

    @Column(nullable = false)
    private String deviceName;


    public Cookie()
    {
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getCookieID() {
        return cookieId;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(Date lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


}
