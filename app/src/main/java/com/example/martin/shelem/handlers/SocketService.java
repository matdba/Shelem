package com.example.martin.shelem.handlers;



import com.example.martin.shelem.instances.Player;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URISyntaxException;

public class SocketService  {
    private static final  String URL = "http://barahoueibk.ir:2097";
    private static Socket socket;



    public static void init(String userID) {
        try {
            socket = IO.socket(URL);
            IO.Options options = new IO.Options();
            options.query = "userID=" + userID;
            socket = IO.socket(URL,options);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }



    public static void socketDisconnect() { socket.disconnect(); }



    public static void findRoom(final String userID, final String username, final String avatarNumber, final FindRoomListener findRoomListener) {
        socket.emit("findRoom", userID, username, avatarNumber, (Ack) args -> {
            try {
                findRoomListener.onFound(Integer.parseInt(args[0].toString()), Integer.parseInt(args[1].toString()), new JSONArray(args[2].toString()));
            } catch (JSONException e) { e.printStackTrace(); }
        });
    }





    public static void leaveRoom(final String socketRoomID, final String playerNumber, final LeaveRoomListener leaveRoomListener) {
        socket.emit("leaveRoom", socketRoomID, playerNumber, (Ack) args -> leaveRoomListener.onLeft(String.valueOf(args[0])));
    }





    public static void userJoined(final UserJoinedListener userJoinedListener) {

        socket.on("userJoined", args -> {
            Player player = new Player();
            player.setUserID(Integer.parseInt(args[0].toString()));
            player.setUsername(args[1].toString());
            player.setProfilePictureNum(Integer.parseInt(args[2].toString()));
            player.setPlayerNumber(Integer.parseInt(args[3].toString()));
            userJoinedListener.onUserJoined(player);
        });

    }






    public static void userLeft(final UserLeftListener userLeftListener) {

        socket.on("userLeft", args -> {
            userLeftListener.onUserLeft(Integer.parseInt(args[0].toString()));
        });
    }



    public static void readyToGo(final ReadyToGoListener readyToGoListener) {
        socket.on("readyToGo", args -> readyToGoListener.onReady());
    }








    public interface FindRoomListener { void onFound(int socketRoomID, int playerNumber, JSONArray jsonArray) throws JSONException; }


    public interface LeaveRoomListener { void onLeft(String response); }


    public interface UserJoinedListener { void onUserJoined(Player player); }


    public interface UserLeftListener { void onUserLeft(int playerNumber); }


    public interface ReadyToGoListener { void onReady(); }


    public interface UnreadyToGoListener { void onUnready(); }
}
