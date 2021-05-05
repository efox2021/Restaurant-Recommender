package com.mc_03.Backend.Yelp.Business;

import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Repository
public class BusinessRepository {
    private final static int DEFAULT_LIMIT = 50;
    private HttpHeaders createHttpHeaders()
    {
        String encodedAuth = ("Bearer " + "_NHYbJknk8hFhs9xR-Z912yfvZwy8r7z2jbyck2JOOdFTteSn2iWBtxUBPH5tyKdNKOnhN_OwLuIjIK1SG1cC7OzIMbQ_NUU-E-1cXxGGa9iU9euQI7R3oQnLFR_X3Yx");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", encodedAuth);
        return headers;
    }

    public Business[] findBusinesses(Map<String, String> parameters) throws Exception {

        if (parameters.containsKey("location") ^ (parameters.containsKey("latitude") && parameters.containsKey("longitude"))) {
            String theUrl = "https://api.yelp.com/v3/businesses/search?limit=" + DEFAULT_LIMIT;
            for (String param : parameters.keySet()) {
                theUrl += "&" + param + "=" + parameters.get(param);
            }
            System.out.println(theUrl);
            RestTemplate restTemplate = new RestTemplate();
            try {
                HttpHeaders headers = createHttpHeaders();
                HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
                ResponseEntity<ListResponseBody> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, ListResponseBody.class);
                System.out.println("Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());

                return response.getBody().getBusinesses();
            } catch (Exception eek) {
                System.out.println("** Exception: " + eek.getMessage());
                return new Business[0];
            }
        } else{
            System.out.println("Please enter location or latitude and longitude parameters");
            return null;
        }
    }
    }




