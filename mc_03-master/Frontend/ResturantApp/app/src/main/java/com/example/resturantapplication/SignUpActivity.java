
package com.example.resturantapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.resturantapplication.app.AppController;
//import com.example.sumon.androidvolley.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.resturantapplication.app.AppController.TAG;

/**
 * @author Nicholas Soultz
 * creates a user with the server allowing the user to have an account to be able to use the app
 */
public class SignUpActivity extends Fragment {
    EditText firstName;
    EditText lastName;
    EditText userName;
    EditText email;
    EditText password;
    TextView responseTextView;
    String tag_json_obj = "jobj_req";
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.signup_page, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         responseTextView = view.findViewById(R.id.signUpTextView);
         firstName = view.findViewById(R.id.firstnameEditText);
         lastName = view.findViewById(R.id.lastnameEditText);
         userName = view.findViewById(R.id.usernameEditText);
         email = view.findViewById(R.id.emailEditText);
         password = view.findViewById(R.id.passwordEditText);
        view.findViewById(R.id.LoginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SignUpActivity.this)
                        .navigate(R.id.signup_page_to_login_page);
            }
        });
        view.findViewById(R.id.signupButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstName.length() == 0 || lastName.length() == 0 || userName.length() == 0 || email.length() == 0 || password.length() == 0){
                    responseTextView.setText(R.string.emptyFields);
                }
                JSONObject userInfo = new JSONObject();
//                Map<String, String> params = new HashMap();
                try {
                    userInfo.put("firstName",firstName.getText());
                    userInfo.put("lastName",lastName.getText());
                    userInfo.put("email",email.getText());
                    userInfo.put("password",password.getText());
                    userInfo.put("username",userName.getText());
                    makeJsonObjReq(userInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //NavHostFragment.findNavController(SignUpActivity.this)
                   //     .navigate(R.id.signup_page_to_login_page);
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
        String sendURL = "http://10.24.227.2:8080/addUser";
        //String sendURL = "localhost/addUser/";

        //String sendURL = "http://coms-309-mc-03.cs.iastate.edu:8080/addUser?firstname=Test&lastname=Test&email=testing&password=password1&username=cme";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
                sendURL, userInfo,

                new Response.Listener<JSONObject>() {

                    /**
                     *
                     * @param response
                     * A JSON object returned from the server
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                                NavHostFragment.findNavController(SignUpActivity.this)
                                        .navigate(R.id.signup_page_to_login_page);
                        //Log.d(TAG, response.toString());

                        //Log.d("We made it here");
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
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Androidhive");
                params.put("email", "abc@androidhive.info");
                params.put("pass", "password123");

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }
}
