
package com.example.resturantapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.resturantapplication.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.resturantapplication.app.AppController.TAG;
/**
 * @author  Nicholas Soultz
 * A temporary page to help with navigation between the other pages
 */
public class BufferPage extends Fragment {
    String cookie;
    int userId;
    String userIdString;
    String zipCode = "50014";
    String tag_json_obj = "jobj_req";
    EditText zipCodeText;
    TextView noZipCodeText;
    private static String restaurantName;
    private static int resturantID;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.buffer_page, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        zipCodeText = view.findViewById(R.id.zipCodeText);
        noZipCodeText = view.findViewById(R.id.noZipCodeText);
        view.findViewById(R.id.randomResturauntButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zipCodeText.length() <= 0){
                    noZipCodeText.setText(R.string.no_zip_code);
                }
                else{
                    JSONObject userInfo = new JSONObject();
                    makeJsonObjReqZipCode(userInfo);
//                Map<String, String> params = new HashMap();
                    zipCode = zipCodeText.toString();
                    try {
                        userInfo.put("zipCode",Integer.parseInt(zipCode));
                        userInfo.put("user_id",userId);
                        userInfo.put("cookieID",cookie);
                        makeJsonObjReqZipCode(userInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                WebsocketActivity wsa = new WebsocketActivity();
                wsa.setZip(zipCode);
                wsa.setUserId(userIdString);
                wsa.setCookie(cookie);
            }
        });
        view.findViewById(R.id.ReviewButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(BufferPage.this)
                        .navigate(R.id.bufferPageToUserReviewpage);
            }
        });
        view.findViewById(R.id.LogoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject userInfo = new JSONObject();
//                Map<String, String> params = new HashMap();
                try {
                    userInfo.put("user_id",userId);
                    userInfo.put("cookieID",cookie);
                    makeJsonObjReq(userInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        view.findViewById(R.id.ProfileButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(BufferPage.this)
                        .navigate(R.id.bufferPage_to_profilePage);
            }
        });
        view.findViewById(R.id.ProfileButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(BufferPage.this)
                        .navigate(R.id.bufferPage_to_profilePage);
            }
        });
        view.findViewById(R.id.socketButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(BufferPage.this)
                        .navigate(R.id.action_bufferPage_to_websocketActivity);
            }
        });
    }
    public void setCookie(String cookie){
        this.cookie = cookie;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    private void makeJsonObjReq(JSONObject userInfo) {
        //showProgressDialog();
        String sendURL = "http://10.24.227.2:8080/logOut";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                sendURL, userInfo,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        NavHostFragment.findNavController(BufferPage.this)
                                .navigate(R.id.bufferPage_to_LoginActivity);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hideProgressDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

    }
    private void makeJsonObjReqZipCode(JSONObject userInfo) {
        //showProgressDialog();
        String sendURL = "http://10.24.227.2:8080/recommendation/byZipCode?zipCode="+zipCodeText.getText();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                sendURL, userInfo,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        UserReviewActivity userReviewActivity = new UserReviewActivity();
                        try {
                            resturantID = (int) response.get("id");
                           restaurantName = (String) response.get("name");
                           noZipCodeText.setText("your random restaurant is "+restaurantName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hideProgressDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

    }
    public static String getRestaurantName() {
        return restaurantName;
    }
    public static int getRestaurantID() {
        return resturantID;
    }
}