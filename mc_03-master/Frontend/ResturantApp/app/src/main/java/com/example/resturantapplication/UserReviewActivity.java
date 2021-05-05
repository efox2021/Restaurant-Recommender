
package com.example.resturantapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.resturantapplication.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import static com.example.resturantapplication.app.AppController.TAG;

/**
 * @author Nicholas Soultz
 * Creates the review from user input, storing it in a JSON object to send to the server
 */
public class UserReviewActivity extends Fragment {
    RatingBar userRating;
    EditText reviewText;
    String restaurantName = getResturantName();
    static  int  resturantID;
    TextView resturantNameText;
    public static String resturantName = BufferPage.getRestaurantName();
    TextView failedTextView;
    String tag_json_obj = "jobj_req";
    String cookie;
    int userId;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.user_review_page, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userRating = view.findViewById(R.id.ratingBar);
        reviewText = view.findViewById(R.id.reviewText);
        failedTextView = view.findViewById(R.id.failedTextView);
        resturantNameText = view.findViewById(R.id.resturantName);
        resturantNameText.setText(BufferPage.getRestaurantName());
        view.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reviewText.length() == 0){
                    failedTextView.setText(R.string.NoReview);
                }
                else{
                    JSONObject userInfo = new JSONObject();
                    try {
                        userInfo.put("id",BufferPage.getRestaurantID());
                        userInfo.put("user_id",userId);
                        userInfo.put("cookieID",cookie);
                        userInfo.put("restaurant name",restaurantName);
                        userInfo.put("rating",userRating.getNumStars());
                        userInfo.put("description",reviewText.getText());
                        makeJsonObjReq(userInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
    /**
     *
     * @param userInfo
     * A JSON object holding info about the user to be passed to the server for storage using Volley
     */
    private void makeJsonObjReq(JSONObject userInfo) {
        //showProgressDialog();
        String sendURL = "http://10.24.227.2:8080/addReview";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                sendURL, userInfo,

                new Response.Listener<JSONObject>() {

                    /**
                     *
                     * @param response
                     * A JSON object returned from the server
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        NavHostFragment.findNavController(UserReviewActivity.this)
                                .navigate(R.id.userReviewPageToBufferPage);
                        // hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            /**
             *
             * @param error
             * A VolleyError thrown by the response handler  and logged
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hideProgressDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }
    public void setCookie(String cookie){
        this.cookie = cookie;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    public static String getResturantName(){
        return resturantName;
    }
}