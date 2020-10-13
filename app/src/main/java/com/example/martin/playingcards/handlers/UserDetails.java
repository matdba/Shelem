package com.example.martin.playingcards.handlers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDetails {

    private SharedPreferences preference;
    private static final String preferenceName = "UserData";


    public UserDetails(Context context) {
        preference = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }





    public void saveUserInfo(JSONObject userInfo) {
        SharedPreferences.Editor editor = preference.edit();
        try {
            editor.putBoolean("loggedIN", true);
            editor.putString("userID", userInfo.getString("id"));
            editor.putString("username", userInfo.getString("username"));
            editor.putString("email", userInfo.getString("email"));
            editor.putInt("avatarNumber", Integer.parseInt(userInfo.getString("profilePictureNum")));
        } catch (JSONException e) { e.printStackTrace(); }
        editor.apply();
    }



    public void setAvatarNumber(int avatarNumber) { preference.edit().putInt("avatarNumber", avatarNumber).apply(); }



    public boolean isLoggedIN() {
        return preference.getBoolean("loggedIN", false);
    }


    public String getUserID() {
        return preference.getString("userID", "none");
    }


    public String getUsername() {
        return preference.getString("username", "none");
    }


    public int getAvatarNumber() { return preference.getInt("avatarNumber", 0); }


    public String getEmail() { return preference.getString("email", "none"); }



    public void removeUserInfo() {
        SharedPreferences.Editor editor = preference.edit();
        editor.clear().apply();
    }
}