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
import com.example.martin.shelem.instances.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class APIHandler {

    private Activity activity;
    private UserDetails userDetails;

    private static final String urlAPIHandler = "http://barahoueibk.ir/shelem/APIHandler.php";





    public APIHandler(Activity activity) {
        this.activity = activity;
    }





    public void login(final String username, final String password, final ResponseListenerLogin responseListenerLogin) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlAPIHandler,
                response -> {

                    if (!response.trim().equals("error")) {
                        Log.i("kireboz", "login: " + response);
                        userDetails = new UserDetails(activity);
                        try {
                            userDetails.saveUserInfo(new JSONArray(response.trim()).getJSONObject(0));
                        } catch (JSONException e) {
                            Log.i("kireboz", "login: " + e);
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






    public void signUp(final String username, final String password, final String email, final String refcode, final ResponseListenerSignUp responseListenerSignUp) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlAPIHandler,
                response -> {
                    if (response.trim().split("_")[0].equals("success")) {
                        responseListenerSignUp.onRecived("success");
                        try {
                            userDetails.saveUserInfo(new JSONObject(response.trim().split("_")[1]));
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






    public void checkUsernameAvailibility (final String username, final ResponseListenerCheckUsernameAvailibility responseListenerCheckUsernameAvailibility) {

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






    public void checkEmailAvailibility (final String email, final ResponseListenerCheckEmailAvailibility responseListenerCheckEmailAvailibility) {

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








    public void updateAvatarNumber(final String userID, final String avatarNumber, final ResponseListenerSignUp responseListenerSignUp) {

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






    public void getForgetCode(final String email, final ResponseListenerForgetCode responseListenerForgetCode) {

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




    public void updateForgetCode(final String username,final ResponseListenerUpdateForgetCode responseListenerUpdateForgetCode) {

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




    public void updatePassword(final String username,final String password ,final ResponseListenerUpdatePassword responseListenerUpdatePassword) {

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




    public void updateCheckInvitation(final String username, final String callerUsername,final ResponseListenerUpdateCheckInvitation responseListenerUpdateCheckInvitation) {

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




    public void getInvitation(final String username, final String callerUsername,final ResponseListenerGetInvitation responseListenerGetInvitation) {

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





    public void createRoom(final String userID, final String isJoker, final String minLevel, final String maxPoint, final APIHandler.ResponseListenerCreateShelemRooms responseListenerCreateShelemRooms) {
        final StringRequest request = new StringRequest(Request.Method.POST, urlAPIHandler,
                response -> {
                    if (response.trim().split("_")[0].equals("success")) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.split("_")[1]);
                            Room room = new Room();
                            room.setRoomID(Integer.parseInt(jsonObject.getString("roomID")));
                            room.setRoomStatus(Integer.parseInt(jsonObject.getString("roomStatus")));
                            room.setIsJoker(Boolean.parseBoolean(jsonObject.getString("isJoker")));
                            room.setMaxPoint(Integer.parseInt(jsonObject.getString("maxPoint")));
                            room.setTeamOnePoints(0);
                            room.setTeamTwoPoints(0);
                            responseListenerCreateShelemRooms.onRecived("success");
                            responseListenerCreateShelemRooms.onDataRecieved(room);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        responseListenerCreateShelemRooms.onRecived(response);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    responseListenerCreateShelemRooms.onRecived("NoConnectionError");
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("method", "createRoom");
                params.put("userID", userID);
                params.put("isJoker", isJoker);
                params.put("minLevel", minLevel);
                params.put("maxPoint", maxPoint);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }








    public void insertPlayer(final String roomId, final String playerId, final String playerNumber, final APIHandler.ResponseListenerInsertShelemPlayer responseListenerInsertShelemPlayer) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlAPIHandler,
                response -> {
                    if (response.trim().equals("success")) {
                        responseListenerInsertShelemPlayer.onRecived("success");
                    } else {
                        responseListenerInsertShelemPlayer.onRecived(response);
                    }
                }, error -> {
            if (error instanceof NoConnectionError) {
                responseListenerInsertShelemPlayer.onRecived("NoConnectionError");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("method", "insertPlayer");
                params.put("roomID", roomId);
                params.put("userID", playerId);
                params.put("playerNumber", playerNumber);
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


    public interface ResponseListenerCreateShelemRooms {
        void onRecived(String response);
        void onDataRecieved(Room room);
    }

    public interface ResponseListenerInsertShelemPlayer{
        void onRecived(String response);
    }

}
