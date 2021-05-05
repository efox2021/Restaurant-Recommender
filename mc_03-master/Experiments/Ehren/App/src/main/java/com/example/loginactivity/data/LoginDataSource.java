package com.example.loginactivity.data;

import com.example.loginactivity.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication

            //4 cases - https://developer.android.com/training/id-auth/authenticate
            //        : error on the device or network cause accountmanager to fail
            //        : user decided not to grant permissions
            //        : stored account credentials aren't sufficient to gain access
            //        : cached auth token has expired - need to invalidate token


            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Ehren Fox");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}