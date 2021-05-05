
package com.example.resturantapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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
import java.util.Hashtable;
import java.util.Map;

import static com.example.resturantapplication.app.AppController.TAG;

/**
 * @author Nicholas Soultz
 * Page that keeps tract of the user being logged in using cookies from the server after
 * confirmation from server for a succesful login
 */
public class LoginActivity extends Fragment {
    String tag_json_obj = "jobj_req";
    EditText userName;
    EditText password;
    int userId;
    String cookie;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        userId = 0;
        cookie = null;
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_page, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userName = view.findViewById(R.id.editTextUserName);
        password = view.findViewById(R.id.editTextPassword);
        view.findViewById(R.id.signUpTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginActivity.this)
                        .navigate(R.id.login_page_to_signup_page);
            }
        });
        view.findViewById(R.id.loginButon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject userInfo = new JSONObject();
                try {
                    userInfo.put("username",userName.getText());
                    userInfo.put("password",password.getText());
                    makeJsonObjReq(userInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        view.findViewById(R.id.tempB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginActivity.this)
                        .navigate(R.id.loginActivity_to_bufferPage);
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
        String sendURL = "http://10.24.227.2:8080/login";
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
                        try {
                            HashMap hash;
                            ProfilePage profile = new ProfilePage();
                            UserReviewActivity userReviewActivity = new UserReviewActivity();
                            BufferPage bufferPage = new BufferPage();
                            userId = (int) response.get("user_id");
                             cookie =(String) response.get("cookieID");
                            System.out.println("the cookie is "+cookie);
                            System.out.println("the userId is "+userId);
                            profile.setCookie(cookie);
                            profile.setUserId(userId);
                            userReviewActivity.setCookie(cookie);
                            userReviewActivity.setUserId(userId);
                            bufferPage.setCookie(cookie);
                            bufferPage.setUserId(userId);

                            WebsocketActivity wsa = new WebsocketActivity();
                            wsa.setCookie(cookie);
                            wsa.setUserId(userId);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        NavHostFragment.findNavController(LoginActivity.this)
                                .navigate(R.id.loginActivity_to_bufferPage);
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
        }) {
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }
}
