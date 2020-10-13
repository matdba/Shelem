package com.example.martin.playingcards.handlers;


import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.example.martin.playingcards.interfaces.GetUserMoveListener;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class GameScreenServerHandler {

    private Socket socket;
    public AppCompatActivity activity;

    public GameScreenServerHandler(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setUserConnection() {
        try{
            socket = IO.socket("https://tranquil-reef-17185.herokuapp.com");
            socket.connect();
            socket.emit("join", "");
        }
        catch (URISyntaxException e){
            e.printStackTrace();
        }

        socket.on("newUserJoined", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, String.valueOf(args[0]), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void sendUserMove(String userId,String room,String move) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userMove",move);
            jsonObject.put("roomId", room);
            jsonObject.put("userId", userId);
            socket.emit("moveAction", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getUserMove(final GetUserMoveListener getUserMoveListener) {
        final JSONObject[] jsonObject = new JSONObject[1];
        socket.on("getUserMove", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                jsonObject[0] = (JSONObject) args[0];
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getUserMoveListener.callback(jsonObject[0]);
//                            Toast.makeText(activity, String.valueOf(jsonObject[0].getString("userMove")) + "_"
//                                     + String.valueOf(jsonObject[0].getString("userId")), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return jsonObject[0];
    }


}
