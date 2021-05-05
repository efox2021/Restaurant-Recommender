package com.mc_03.Backend.User;
import com.google.common.hash.Hashing;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Table;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "usersTable")
public class Users {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 21)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column()
    private Date birthdate;

    public Users() {

    }



    public Long getId() {
        return id;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setPassword(String password)
    {

        this.password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    public String getPassword()
    {
        return password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public static boolean validEmail(Users user) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        if (user.getEmail() == null){
            return false;
        }

        Pattern emailPatter = Pattern.compile(regex);
        Matcher matchEmail = emailPatter.matcher(user.getEmail());

        return matchEmail.matches();
    }

    public static boolean validPassword(Users user) {
        boolean containsNumbers = false;
        boolean containsUppercase = false;
        boolean containslowercase = false;
        boolean containsSpecial = false;
        if(user.getPassword() == null || user.getPassword().length() < 8)
        {
            return false;
        }
        for(int i = 0; i < user.getPassword().length(); i++)
        {
            if("1234567890".contains(user.getPassword().substring(i, i+1)))
            {
                containsNumbers = true;
            }
            else if ("abcdefghijklmnopqrstuvwxyz".contains(user.getPassword().substring(i, i+1)))
            {
                containslowercase = true;
            }
            else if ("!@#$%^&*()".contains(user.getPassword().substring(i, i+1)))
            {
                containsSpecial = true;
            }
            else if("ACBDEFGHIJKLMNOPQRSTUVWXYZ".contains(user.getPassword().substring(i, i+1)))
            {
                containsUppercase = true;
            }
        }


        return  containsNumbers && containslowercase && containsSpecial && containsUppercase;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
