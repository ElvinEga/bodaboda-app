package com.beast.bodaboda.bodaboda.component;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.beast.bodaboda.bodaboda.LoginActivity;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "LoginPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Key of Sessions(make variable public to access from outside)
    public static final String KEY_ID ="id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_ROLE = "role";
    public static final String KEY_IP = "ip";
    public static final String KEY_NAME = "name";
    public static final String KEY_EXPIRED = "EXPIRED";
    public static final String KEY_TOKEN = "token";



    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    /**
     * Create login session
     * */
    public void createLoginSession(String id,String name,String username,String password,String role,String ip, String expired, String token){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing id in pref
        editor.putString(KEY_ID, id);
        // Storing name in pref
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_USERNAME, username);
        // Storing password in pref
        editor.putString(KEY_PASSWORD, password);
        // Storing role in pref
        editor.putString(KEY_ROLE, role);
        // Storing role in pref
        editor.putString(KEY_EXPIRED, expired);
        editor.putString(KEY_IP, ip);
        // Storing token in pref
        editor.putString(KEY_TOKEN, token);

        // commit changes
        editor.commit();
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user id
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        //user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));

        // user password
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        // user role
        user.put(KEY_ROLE, pref.getString(KEY_ROLE, null));

        user.put(KEY_IP, pref.getString(KEY_IP, null));

        user.put(KEY_EXPIRED, pref.getString(KEY_EXPIRED, null));

        // session token
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));


        // return user
        return user;
    }
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}