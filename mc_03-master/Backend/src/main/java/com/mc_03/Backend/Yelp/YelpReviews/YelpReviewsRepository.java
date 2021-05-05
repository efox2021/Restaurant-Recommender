package com.mc_03.Backend.Yelp.YelpReviews;

import com.mc_03.Backend.Yelp.Business.Business;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class YelpReviewsRepository {

    private HttpHeaders createHttpHeaders() {
        String encodedAuth = ("Bearer " + "_NHYbJknk8hFhs9xR-Z912yfvZwy8r7z2jbyck2JOOdFTteSn2iWBtxUBPH5tyKdNKOnhN_OwLuIjIK1SG1cC7OzIMbQ_NUU-E-1cXxGGa9iU9euQI7R3oQnLFR_X3Yx");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", encodedAuth);
        return headers;
    }

    //Returns an array of YelpReviews when given a Business businessID
    public YelpReviews[] findReviews(String businessID) {
        if (businessID != null) {
            String theUrl = "https://api.yelp.com/v3/businesses/" + businessID + "/reviews";
            System.out.println(theUrl);
            RestTemplate restTemplate = new RestTemplate();
            try {
                HttpHeaders headers = createHttpHeaders();
                HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
                ResponseEntity<YelpReviewsListResponseBody> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, YelpReviewsListResponseBody.class);
                System.out.println("Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());

                return response.getBody().getReviews();
            } catch (Exception eek) {
                System.out.println("** Exception: " + eek.getMessage());
                return new YelpReviews[0];
            }
        } else {
            System.out.println("Please enter a valid businessID");
            return null;
        }
    }


    //This works fine
    public YelpReviews[] findReviewsByBusiness(Business business) {
        return findReviews(business.id);
    }

    public YelpReviews[] findReviewsWithBusinessLocale(Business business, String locale){
        if (business != null) {

            String theUrl = "https://api.yelp.com/v3/businesses/" + business.id + "/reviews" + ((locale != null) ? "?"+locale : "");
            System.out.println(theUrl);
            RestTemplate restTemplate = new RestTemplate();
            try {
                HttpHeaders headers = createHttpHeaders();
                HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
                ResponseEntity<YelpReviewsListResponseBody> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, YelpReviewsListResponseBody.class);
                System.out.println("Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());

                return response.getBody().getReviews();
            } catch (Exception eek) {
                System.out.println("** Exception: " + eek.getMessage());
                return new YelpReviews[0];
            }
        } else {
            System.out.println("Please enter a valid business ID");
            return null;
        }
    }

    public YelpReviews[] findReviewsWithIDLocale(String businessID, String locale){
        if (businessID != null) {

            String theUrl = "https://api.yelp.com/v3/businesses/" + businessID + "/reviews" + ((locale != null) ? "?"+locale : "");
            System.out.println(theUrl);
            RestTemplate restTemplate = new RestTemplate();
            try {
                HttpHeaders headers = createHttpHeaders();
                HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
                ResponseEntity<YelpReviewsListResponseBody> response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, YelpReviewsListResponseBody.class);
                System.out.println("Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());

                return response.getBody().getReviews();
            } catch (Exception eek) {
                System.out.println("** Exception: " + eek.getMessage());
                return new YelpReviews[0];
            }
        } else {
            System.out.println("Please enter a valid businessID");
            return null;
        }
    }
}
