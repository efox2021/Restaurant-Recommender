package com.mc_03.Backend.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.hash.Hashing;
import com.mc_03.Backend.Cookie.Cookie;
import com.mc_03.Backend.Cookie.CookieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CookieRepository cookieRepository;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping(path = "/addUser", consumes = "application/json", produces = "application/json")
    public @ResponseBody ObjectNode addUser(@RequestBody Users user)
    {
        ObjectNode returnNode = mapper.createObjectNode();
        //JSON return
        if(userRepository.findByUsername(user.getUsername()).size() == 0)
        {

            try {
                userRepository.save(user);
                returnNode.put("status", "User_Added");
            }
            catch (Exception e)
            {
                returnNode.put("status", "Unknown_Error");
            }

        }
        else {
            returnNode.put("status", "Username_Taken");
        }

        return returnNode;
    }

    /** For debugging
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Users> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
    */
    /**
     *
     * @param request - JSON Object, needs username and password keys in object <br>
     *                 <code>       { username: string,
     *                          <br>  password: string } </code>
     * @return - JSON Object with any of the following status
     */
    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public @ResponseBody HashMap<String,Object> index(@RequestBody Map<String, String> request) {

        //Find the user
        Users[] users = userRepository.findByUsername(request.get("username")).toArray(new Users[0]);
        HashMap<String,Object> returnObject = new HashMap<>();

        //Checking if it found a user with that username
        if(users.length == 1)
        {
            String hashPass = Hashing.sha256().hashString(request.get("password"), StandardCharsets.UTF_8).toString();
            if(users[0].getPassword().equals(hashPass)) {
                returnObject.put("status", "Logged_In");

                //Creating a new cookie
                Cookie userCookie = new Cookie();
                userCookie.setDateAdded(new Date());
                userCookie.setLastLoggedIn(new Date());
                userCookie.setDeviceName("testing");
                userCookie.setUser(users[0]);
                cookieRepository.save(userCookie);

                returnObject.put("user_id", userCookie.getUser().getId());
                userCookie.setUser(null);
                returnObject.put("cookieID", userCookie.getCookieID());
            }
            else {
                returnObject.put("status", "Incorrect_Password");
            }
        }
        else {
            returnObject.put("status", "Not_A_User");
        }
        return(returnObject);
    }

    @PostMapping(path = "/logout", consumes = "application/json", produces = "application/json")
    public @ResponseBody HashMap<String,Object> logout(@RequestBody Map<String, String> request){
        String uid = request.get("user_id");
        String cid = request.get("cookieID");
        HashMap<String,Object> returnObject = new HashMap<String,Object>();
        if(uid == null)
        {

            returnObject.put("status", "missing_user_id");
            return (returnObject);

        }
        else if(cid == null)
        {
            returnObject.put("status", "missing_cookie_id");
            return (returnObject);
        }
        else {
            ArrayList<Cookie> returnList = (ArrayList<Cookie>) cookieRepository.findBycookieId(cid);
            if(returnList.isEmpty())
            {
                returnObject.put("status", "user_not_logged_in");
            }
            else {
                if(returnList.get(0).getUser().getId() == Integer.parseInt(uid))
                {
                    cookieRepository.deleteBycookieId(cid);
                    returnObject.put("status", "logged_out");
                }
                else {
                    returnObject.put("status", "user_id_and_cookie_id_dont_match");
                }

            }

            return(returnObject);
        }
    }

    @PostMapping(path = "/userchange", consumes = "application/json", produces = "application/json")
    public @ResponseBody HashMap<String, Object> changeUserDetails(@RequestBody Map<String, String> request)
    {
        String uid = request.get("user_id");
        String cid = request.get("cookieID");
        HashMap<String,Object> returnObject = new HashMap<String,Object>();
        if(uid == null)
        {

            returnObject.put("status", "missing_user_id");
            return (returnObject);

        }
        else if(cid == null)
        {
            returnObject.put("status", "missing_cookie_id");
            return (returnObject);
        }
        else {
            ArrayList<Cookie> returnList = (ArrayList<Cookie>) cookieRepository.findBycookieId(cid);
            if(returnList.isEmpty())
            {
                returnObject.put("status", "user_not_logged_in");
            }
            else {
                if(returnList.get(0).getUser().getId() == Long.parseLong(uid))
                {
                    String username = request.get("username");
                    String firstName = request.get("firstName");
                    String lastName = request.get("lastName");
                    String email = request.get("email");
                    Optional<Users> temp = userRepository.findById(Long.parseLong(uid));

                    if(!username.isEmpty())
                    {
                        temp.get().setUsername(username);
                    }
                    else if(!firstName.isEmpty())
                    {
                        temp.get().setFirstName(firstName);
                    }
                    else if(!lastName.isEmpty())
                    {
                        temp.get().setLastName(lastName);
                    }
                    else if(!email.isEmpty())
                    {
                        temp.get().setEmail((email));
                    }
                    userRepository.save(temp.get());
                    returnObject.put("status", "user_info_updated");
                }
                else {
                    returnObject.put("status", "user_id_and_cookie_id_dont_match");
                }

            }

            return(returnObject);
        }
    }


}
