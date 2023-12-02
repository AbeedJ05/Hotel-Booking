package com.example.hotelbooking.SharedPref;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.example.hotelbooking.Activities.Sign_In;
import com.example.hotelbooking.Model.User;



public class SharedPrefManager {
    // Name of the shared preferences file
    private static final String SHARED_PREF_NAME = "my_shared_pref";

    // Keys for storing data in shared preferences
    private static final String KEY_ID = "user_id";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_PHONE = "user_phone";
    private static final String KEY_PASS = "user_pass";


    // Singleton instance of SharedPrefManager
    private static SharedPrefManager mInstance;
    // Context object to access shared preferences
    private static Context mCtx;

    // Private constructor following the Singleton design pattern
    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    // Returns the instance of SharedPrefManager, creating it if necessary
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }


    public boolean isLoggedIn() {
        // Retrieve the shared preferences
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        // Check if the user name is not null, indicating that the user is logged in
        return sharedPreferences.getString(KEY_NAME, null) != null;

    }

    public void userLogin(User user) {
        // Retrieve the shared preferences in private mode
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Store user details in shared preferences
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_PASS, user.getPassword());

        // Save changes to shared preferences
        editor.apply();
    }


    public User getUsers() {
        // Retrieve the shared preferences
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        // Retrieve user details from shared preferences
        int id = sharedPreferences.getInt(KEY_ID, 0);
        String name = sharedPreferences.getString(KEY_NAME, null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        String phone = sharedPreferences.getString(KEY_PHONE, null);
        String pass = sharedPreferences.getString(KEY_PASS, null);

        // Create and return a User object
        return new User(id, name, email, pass, phone);
    }


    public void loggedOut() {
        // Retrieve the shared preferences
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Clear all data from shared preferences
        editor.clear();
        // Apply changes to shared preferences
        editor.apply();

        // Redirect to the sign-in activity
        Intent intent = new Intent(mCtx, Sign_In.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);
    }


    public void userUpdate(User user) {
        // Retrieve the shared preferences
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Update user details in shared preferences
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_PASS, user.getPassword());

        // Save changes to shared preferences
        editor.apply();
    }

}




