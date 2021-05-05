
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

import static com.example.resturantapplication.app.AppController.TAG;

/**
 * @author Nicholas Soultz
 * page that displays the user information and allows them to update their information
 * with the server and some user preferences
 */
public class ProfilePage extends Fragment {
    String currentName = "john doe";
    String currentEmail = "johndoe@gmail.com";
    String tag_json_obj = "jobj_req";
    EditText newFirstName;
    EditText newLastName;
    EditText newUserName;
    EditText newEmail;
    String cookie;
    int userId;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_page, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newEmail = view.findViewById(R.id.NewEmail);
        newFirstName = view.findViewById(R.id.NewFirstName);
        newLastName = view.findViewById(R.id.NewLastName);
        newUserName = view.findViewById(R.id.NewUserName);
       // hiddenTextView = view.findViewById(R.id.hiddenTextView);
        view.findViewById(R.id.CancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfilePage.this)
                        .navigate(R.id.profilePageToBufferPage);
            }
        });
        view.findViewById(R.id.UpdateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject userInfo = new JSONObject();
                try {
                    if (newLastName.getText().length() <= 0 || newFirstName.getText().length() <= 0 || newEmail.getText().length() <= 0 || newUserName.getText().length() <=0 ){
                        //hiddenTextView.setText(R.string.emptyField);
                    }
                    else{
                        userInfo.put("user_id",userId);
                        userInfo.put("cookieID",cookie);
                        userInfo.put("username",newUserName.getText());
                        userInfo.put("firstName",newFirstName.getText());
                        userInfo.put("lastName",newLastName.getText());
                        userInfo.put("email",newEmail.getText());
                        makeJsonObjReq(userInfo);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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
        String sendURL = "http://10.24.227.2:8080/userChange";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                sendURL, userInfo,

                new Response.Listener<JSONObject>() {

                    /**
                     *
                     * @param response
                     * A JSON object returned from the server
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        NavHostFragment.findNavController(ProfilePage.this)
                                .navigate(R.id.profilePageToBufferPage);
                    }
                }, new Response.ErrorListener() {

            /**
             *
             * @param error
             * an error that is thrown by the response handler
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

    /**
     *
     * @return CurrentName
     * returns the current name of the User
     */
    public String getName(){
        return currentName;
    }

    /**
     *
     * @return currentEmail
     * returns the current Email of the user
     */
    public String getEmail(){
        return currentEmail;
    }
    public void setCookie(String cookie){
        this.cookie = cookie;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }

}