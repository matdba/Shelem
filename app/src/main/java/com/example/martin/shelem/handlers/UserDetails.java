package com.example.martin.shelem.handlers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
            editor.putString("allDetails",userInfo.toString());
            editor.putBoolean("loggedIN", true);
            editor.putString("userID", userInfo.getString("id"));
            editor.putString("username", userInfo.getString("username"));
            editor.putString("email", userInfo.getString("email"));
            editor.putInt("avatarNumber", Integer.parseInt(userInfo.getString("profilePictureNum")));
        } catch (JSONException e) { e.printStackTrace(); }
        editor.apply();
    }

    public JSONObject getAllDetails(){

        JSONObject jsonObject = null;
        JSONObject all = new JSONObject();
        Log.i("koskalak", "getAllDetails: "+preference.getString("allDetails", "none") + "ok");
        try {
            jsonObject = new JSONObject(preference.getString("allDetails", "none"));


            all.put("userID",jsonObject.get("id"));
            all.put("username",jsonObject.get("username"));
            all.put("profilePictureNum",jsonObject.get("profilePictureNum"));
            all.put("totalxp",jsonObject.get("totalxp"));
            all.put("totalMatches",jsonObject.get("totalMatches"));
            all.put("wonMatches",jsonObject.get("wonMatches"));
            all.put("playedTime",jsonObject.get("playedTime"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return all;
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