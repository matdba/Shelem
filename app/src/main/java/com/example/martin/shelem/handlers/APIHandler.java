package com.example.martin.shelem.handlers;


import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.martin.shelem.activities.BaseActivity;
import com.example.martin.shelem.instances.Player;
import com.example.martin.shelem.instances.Room;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class APIHandler {

    private static Activity activity;

    private static final String urlAPIHandler = "http://barahoueibk.ir/shelem/APIHandler.php";





    public static void init () {

        activity = BaseActivity.activity;

    }





    public static void login(final String username, final String password, final ResponseListenerLogin responseListenerLogin) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlAPIHandler,
                response -> {

                    if (!response.trim().equals("error")) {
                        try {
                            UserDetails.saveUserInfo(new JSONArray(response.trim()).getJSONObject(0));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        responseListenerLogin.onRecived("success");
                    } else {
                        responseListenerLogin.onRecived(response);
                    }
                }, error -> {
                    if (error instanceof NoConnectionError) {
                        responseListenerLogin.onRecived("NoConnectionError");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("method", "login");
                params.put("username", username.trim());
                params.put("password", password.trim());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }






    public static void signUp(final String username, final String password, final String email, final String refcode, final ResponseListenerSignUp responseListenerSignUp) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlAPIHandler,
                response -> {
                    if (response.trim().split("_")[0].equals("success")) {
                        responseListenerSignUp.onRecived("success");
                        try {
                            UserDetails.saveUserInfo(new JSONObject(response.trim().split("_")[1]));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        responseListenerSignUp.onRecived(response);
                    }

                }, error -> {
                    if (error instanceof NoConnectionError) {
                        responseListenerSignUp.onRecived("NoConnectionError");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("method", "signUp");
                params.put("username", username);
                params.put("password", password);
                params.put("email", email);
                params.put("refcode", refcode);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }






    public static void checkUsernameAvailibility (final String username, final ResponseListenerCheckUsernameAvailibility responseListenerCheckUsernameAvailibility) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlAPIHandler,
                response -> responseListenerCheckUsernameAvailibility.onRecived(response), error -> {
            if (error instanceof NoConnectionError) {
                responseListenerCheckUsernameAvailibility.onRecived("NoConnectionError");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("method", "checkUsernameAvailibility");
                params.put("username", username);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }






    public static void checkEmailAvailibility (final String email, final ResponseListenerCheckEmailAvailibility responseListenerCheckEmailAvailibility) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlAPIHandler,
                response -> responseListenerCheckEmailAvailibility.onRecived(response), error -> {
            if (error instanceof NoConnectionError) {
                responseListenerCheckEmailAvailibility.onRecived("NoConnectionError");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("method", "checkEmailAvailibility");
                params.put("email", email);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }






    public static void updateAvatarNumber(final String userID, final String avatarNumber, final ResponseListenerSignUp responseListenerSignUp) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlAPIHandler,
                response -> {
                    if (!response.trim().equals("error")) {
                        responseListenerSignUp.onRecived("success");
                    } else {
                        responseListenerSignUp.onRecived(response);
                    }

                }, error -> {
            if (error instanceof NoConnectionError) {
                responseListenerSignUp.onRecived("NoConnectionError");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("method", "updateAvatarNumber");
                params.put("userID", userID);
                params.put("avatarNumber", avatarNumber);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }





    public static void getForgetCode(final String email, final ResponseListenerForgetCode responseListenerForgetCode) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlAPIHandler,
                response -> {
                    if (response.trim().equals("success")) {
                        responseListenerForgetCode.onRecived(response);
                    } else {
                        responseListenerForgetCode.onRecived(response);
                    }

                }, error -> {
                    if (error instanceof NoConnectionError) {
                        responseListenerForgetCode.onRecived("NoConnectionError");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("method", "getForgetCode");
                params.put("email", email);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);

    }




    public static void updateForgetCode(final String username,final ResponseListenerUpdateForgetCode responseListenerUpdateForgetCode) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlAPIHandler,
                response -> {
                    if (response.trim().equals("success")) {
                        responseListenerUpdateForgetCode.onRecived(response);
                    } else {
                        responseListenerUpdateForgetCode.onRecived(response);
                    }

                }, error -> {
                    if (error instanceof NoConnectionError) {
                        responseListenerUpdateForgetCode.onRecived("NoConnectionError");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("method", "updateForgetCode");
                params.put("username", username);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);

    }




    public static void updatePassword(final String username,final String password ,final ResponseListenerUpdatePassword responseListenerUpdatePassword) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlAPIHandler,
                response -> {
                    if (response.trim().equals("success")) {
                        responseListenerUpdatePassword.onRecived(response);
                    } else {
                        responseListenerUpdatePassword.onRecived(response);
                    }

                }, error -> {
                    if (error instanceof NoConnectionError) {
                        responseListenerUpdatePassword.onRecived("NoConnectionError");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("method", "updatePassword");
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);

    }




    public static void updateCheckInvitation(final String username, final String callerUsername,final ResponseListenerUpdateCheckInvitation responseListenerUpdateCheckInvitation) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlAPIHandler,
                response -> {

                    if (response.trim().equals("success")) {
                        responseListenerUpdateCheckInvitation.onRecived("success");
                    } else {
                        responseListenerUpdateCheckInvitation.onRecived(response);
                    }
                }, error -> {
                    if (error instanceof NoConnectionError) {
                        responseListenerUpdateCheckInvitation.onRecived("NoConnectionError");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("method", "updateCheckInvitation");
                params.put("username", username.trim());
                params.put("callerUsername", callerUsername.trim());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);

    }




    public static void getInvitation(final String username, final String callerUsername,final ResponseListenerGetInvitation responseListenerGetInvitation) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlAPIHandler,
                response -> {

                    if (response.trim().split("_")[0].equals("success")) {
//                            try {
//                                Invitation.setInvitation(new JSONObject(response.trim().split("_")[1]));
//                            } catch (JSONException e) { e.printStackTrace(); }
                        responseListenerGetInvitation.onRecived("success");
                    } else {
                        responseListenerGetInvitation.onRecived(response);
                    }
                }, error -> {
                    if (error instanceof NoConnectionError) {
                        responseListenerGetInvitation.onRecived("NoConnectionError");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("method", "getInvitation");
                params.put("username", username.trim());
                params.put("callerUsername", callerUsername.trim());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);

    }



    public static void updatePlayer (final Player player, final ResponseListenerUpdatePlayer responseListenerUpdatePlayer) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlAPIHandler,
                response -> responseListenerUpdatePlayer.onStatusRecived(true), error -> {
                    if (error instanceof NoConnectionError) {
                        responseListenerUpdatePlayer.onCaptionRecived("NoConnectionError");
                        responseListenerUpdatePlayer.onStatusRecived(false);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                Gson gson = new Gson();
                params.put("method", "updatePlayer");
                params.put("player", gson.toJson(player, Player.class));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }




    public interface ResponseListenerLogin {
        void onRecived(String response);
    }

    public interface ResponseListenerSignUp {
        void onRecived(String response);
    }

    public interface ResponseListenerForgetCode {
        void onRecived(String response);
    }

    public interface ResponseListenerUpdateForgetCode{
        void onRecived(String response);
    }

    public interface ResponseListenerUpdatePassword{
        void onRecived(String response);
    }

    public interface ResponseListenerUpdateCheckInvitation {
        void onRecived(String response);
    }

    public interface ResponseListenerGetInvitation {
        void onRecived(String response);
    }

    public interface ResponseListenerCheckUsernameAvailibility {
        void onRecived(String response);
    }

    public interface ResponseListenerCheckEmailAvailibility {
        void onRecived(String response);
    }

    public interface ResponseListenerUpdatePlayer {
        void onCaptionRecived(String response);
        void onStatusRecived(Boolean response);

    }

}
