package com.example.martin.shelem.handlers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.martin.shelem.activities.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDetails {


    private static SharedPreferences preference;
    private static final String preferenceName = "UserData";


    public static void init(Context context) {

        preference = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }


    public static void saveUserInfo(JSONObject userInfo) {
        SharedPreferences.Editor editor = preference.edit();
        try {
            editor.putString("allDetails",userInfo.toString());
            editor.putBoolean("loggedIN", true);
            editor.putString("userID", userInfo.getString("userID"));
            editor.putString("username", userInfo.getString("username"));
            editor.putString("email", userInfo.getString("email"));
            editor.putInt("avatarNumber", Integer.parseInt(userInfo.getString("avatarNumber")));
            editor.putInt("totalXp", Integer.parseInt(userInfo.getString("totalXp")));
            editor.putInt("totalMatches", Integer.parseInt(userInfo.getString("totalMatches")));
            editor.putInt("wonMatches", Integer.parseInt(userInfo.getString("wonMatches")));
            editor.putFloat("playedTime", Float.parseFloat(userInfo.getString("playedTime")));
        } catch (JSONException e) { e.printStackTrace(); }
        editor.apply();
    }


    public static JSONObject getAllDetails(){

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(preference.getString("allDetails", "none"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    public static void setAvatarNumber(int avatarNumber) { preference.edit().putInt("avatarNumber", avatarNumber).apply(); }


    public static boolean isLoggedIN() {
        return preference.getBoolean("loggedIN", false);
    }


    public static String getUserID() {
        return preference.getString("userID", "none");
    }


    public static String getUsername() {
        return preference.getString("username", "none");
    }


    public static int getAvatarNumber() { return preference.getInt("avatarNumber", 0); }


    public static int getTotalXp() { return preference.getInt("totalXp", 0); }


    public static int getTotalMatches() { return preference.getInt("totalMatches", 0); }


    public static int getWonMatches() { return preference.getInt("wonMatches", 0); }


    public static float getPlayedTime() { return preference.getFloat("playedTime", 0); }


    public static void removeUserInfo() {
        SharedPreferences.Editor editor = preference.edit();
        editor.clear().apply();
    }
}