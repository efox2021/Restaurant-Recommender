package com.mc_03.Backend.Chat;

import com.mc_03.Backend.Cookie.Cookie;
import com.mc_03.Backend.Cookie.CookieRepository;
import com.mc_03.Backend.User.UserRepository;
import com.mc_03.Backend.User.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.persistence.Column;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

@ServerEndpoint("/chat/{zipCode}/{uid}/{cookieID}")
@Controller
public class WebSocketServer {

    private static Map<Long, Session> userSessions = new Hashtable<>();
    private static Map<Session, Long> sessionsUser = new Hashtable<>();
    private static Map<Long, Integer> userToZipcode = new Hashtable<>();

    @Autowired
    private CookieRepository cookieRepository;

    @Autowired
    private UserRepository userRepository;

    @OnOpen
    public void onOpen(Session session, @PathParam("zipCode") String zipCode, @PathParam("uid") String uid, @PathParam("cookieID") String cookieID) throws IOException {
        System.out.println("OnOpen set");

        ArrayList<Cookie> cookies = ((ArrayList<Cookie>) cookieRepository.findBycookieId(cookieID));

        System.out.println(cookies.isEmpty());
        if(cookies.isEmpty())
        {
            session.getBasicRemote().sendText("User Not Logged in");
            session.close();

        }
        else {
            System.out.println("UID IN" + uid);
            System.out.println("Cookie IN" + cookieID);
            Long uidLong = Long.parseLong(uid);
            int zipCodeInt = Integer.parseInt(zipCode);
           if(cookies.get(0).getUser().getId() == uidLong)
           {

              userSessions.put(uidLong, session);
              sessionsUser.put(session, uidLong);
              userToZipcode.put(uidLong, zipCodeInt);
              Optional<Users> temp = userRepository.findById(uidLong);
              if(!temp.isPresent())
              {
                  return;
              }
              String message = temp.get().getUsername() + " is in the chat!";
              broadcast(message, zipCodeInt);
           }
           else {
              session.getBasicRemote().sendText("Cookie and User ID mismatch");
              session.close();
           }
        }

        //Say who joined the chat depending on the zipcode given
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException
    {
        //Format is the following
        //<message>
        int zipCode = userToZipcode.get(sessionsUser.get(session));
        broadcast(message, zipCode);
    }

    @OnClose
    public void onClose(Session session) throws IOException
    {
        System.out.println(userSessions.get(Long.parseLong("1")));
        System.out.println("Session" + session);
        Long user = sessionsUser.get(session);
        System.out.println("ID" + user);
        sessionsUser.remove(session);
        userSessions.remove(user);
        int zipCode = userToZipcode.get(session);
        userToZipcode.remove(user);
        Optional<Users> temp = userRepository.findById(user);
        if(!temp.isPresent())
        {
            return;
        }
        String message = (temp.get().getUsername() + " has left the chat!");
        broadcast(message, zipCode);
    }

    @OnError
    public void onError(Session session, Throwable throwable)
    {
        // Do error handling here
        System.out.println("Error reported on chat");
    }


    private void broadcast(String message, int zipCodeSending)
    {
        sessionsUser.forEach((session, username)->{
            try {
                if(zipCodeSending == userToZipcode.get(username)) {
                    System.out.println(message);
                    session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
