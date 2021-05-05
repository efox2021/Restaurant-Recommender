package com.mc_03.Backend.UserReview;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mc_03.Backend.Cookie.Cookie;
import com.mc_03.Backend.Cookie.CookieRepository;
import com.mc_03.Backend.Restaurant.RestaurantRepository;
import com.mc_03.Backend.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.util.stream.Collectors.toMap;


@RestController
public class UserReviewController {
    @Autowired private RestaurantRepository restaurantRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private UserReviewRepository userReviewRepository;
    @Autowired private ObjectMapper mapper;
    @Autowired private CookieRepository cookieRepository;
    private static Path excludedWordsPath = Paths.get("Backend/src/main/resources/excludedWords.txt");

    @PostMapping (path="/addReview", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ObjectNode addReview(@RequestBody UserReview userReview) throws Exception {
        Collection<Cookie> cookies = cookieRepository.findBycookieId(userReview.getCookieID());
        ObjectNode returnNode = mapper.createObjectNode();
        if(!cookies.isEmpty()) {
            returnNode.put("Success", "Congrats");
            userReviewRepository.save(userReview);
        }
        return returnNode;
    }

    @GetMapping(path="/getKeywordsByRID")
    public @ResponseBody ArrayList<String> getKeywords(@RequestParam(name="rid", required = true) int rid) throws IOException {
        return getTopKeywords(rid);
    }


    //Returns a list of user reviews for a given restaurant ID.
    @GetMapping(path="/getReviewsByRID")
    public @ResponseBody Iterable<UserReview> getReviewsRID(@RequestParam(name = "rid", required = true) int rid){
        return userReviewRepository.findByreviewId(rid);
    }


    //Returns a List of User Reviews for a given user ID.
    @GetMapping(path="/getReviewsByUID")
    public @ResponseBody Iterable<UserReview> getReviewsUID(@RequestParam(name = "uid", required = true) Long uid){
        return userReviewRepository.findByuserId(uid);
    }


    //return a mapping of keywords sorted by the number of times they are
    // found in user descriptions given a restaurant id
    // ordered in decreasing order
    public ArrayList<String> getTopKeywords(int rid) throws IOException {
        ArrayList<String> excludedWords = getExcludedWords();
        Map<String, Integer> keywordMap = new TreeMap();
        ArrayList<String> keywords = new ArrayList<>();

        // Hangs up here because nothing in database for rid I gave
        Collection<UserReview> reviews = userReviewRepository.findByreviewId(rid);

        // iterate through all user reviews of a given restaurant id
        for(UserReview rev : reviews){
            String description = rev.getDescription();
            String str[] = description.split(" ");

            // for each word in a user review description add new words
            // that aren't on the exclude words list to our tree map
            for(String s : str){
                if(!excludedWords.contains(s)){
                    if(keywordMap.isEmpty())
                        keywordMap.put(s, 1);

                    // if the keyword is already present in the tree then increment its value
                    if(keywordMap.containsKey(s)){
                        int n = keywordMap.get(s);
                        keywordMap.put(s, n++);
                    }
                }
            }

        }
        Map<String, Integer> sortedByValue = keywordMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        Set<String> keyset = sortedByValue.keySet();

        for(String key : keyset){
            keywords.add(key);
        }
    return keywords;
    }

    public ArrayList<String> getExcludedWords() throws IOException {
//        Path pathToFile = Paths.get(pathname);//
//        List<String> lines = Files.readAllLines(pathToFile);
//        int counter = 0;

//        //array list of the excluded words
//        for(String line : lines){
//            String trimmed = line.trim();
//            if(!excluded.contains(trimmed)){
//                excluded.add(trimmed);
//            }
//
//        }

        ArrayList<String> excluded = new ArrayList<>();
        excluded.add("the");
        excluded.add("and");
        excluded.add("but");
        excluded.add("or");
        excluded.add("so");
        excluded.add("to");
        excluded.add("go");
        excluded.add("now");
        excluded.add("am");
        excluded.add("is");
        excluded.add("for");
        excluded.add("from");
        excluded.add("under");
        excluded.add("above");
        excluded.add("below");
        excluded.add("all");
        excluded.add("them");
        excluded.add("these");


        return excluded;
    }

    //ArrayList<String> keywords = new ArrayList<>();
    //find all reviews by Rid
    //for each userReviewDescription in User Reviews
    //Parse description and compare processed words with excluded word list. If not in the list then add to hashmap (sortedmap?) and update count
    //return sorted array list of keywords returned by the RID

}
