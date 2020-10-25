package com.example.martin.shelem.handlers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.martin.shelem.instances.RecentGameRooms;
import com.example.martin.shelem.instances.Room;
import com.example.martin.shelem.instances.Player;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class SocketHandler2 extends AppCompatActivity {
    private static final  String URL = "http://barahoueibk.ir:2097";
    private String userID;
    public  static Socket socket;



    public static void  init(String userID){
        try {
            IO.Options options = new IO.Options();
            options.query = "userID=" + userID;
            socket = IO.socket(URL,options);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public static  void socketDisconnect() { socket.disconnect(); }



    /*
    -------------------------------------  NEW Method -------------------------------------
    */

    public static void getRoom(final String roomID, final onGetRoomsRecived onRoomsRecived) {

       socket.emit("getShelemRoom", roomID, new Ack() {
           @Override
           public void call(Object... args) {
               JSONObject jsonObject = null;
               Gson gson = new Gson();
               try {
                   jsonObject = new JSONObject(args[0].toString());
               } catch (JSONException e) {
                   e.printStackTrace();
               }
               Room room = gson.fromJson(jsonObject.toString(), Room.class);

               onRoomsRecived.onReciced(room);
           }
       });
    }

    public static void joinShelmRoom(JSONObject player,final onJoinShelemRoomRecived onJoinShelemRoomRecived) {

        socket.emit("joinShelemRoom",player, new Ack() {
            @Override
            public void call(final Object... args) {
                if (args[0].toString().equals("false")) {
                    onJoinShelemRoomRecived.onReciced("This Room Is Full");
                } else {
                    onJoinShelemRoomRecived.onReciced("Joined");
                }
            }
        });

    }

    public static void leftShelemRoom(final int roomID, final int userID,final int playerNumber,final onLeftShelemRoomRecived onLeftShelemRoomRecived){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("roomID",roomID);
            jsonObject.put("userID",userID);
            jsonObject.put("playerNumber",playerNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        socket.emit("LeftShelemRoom",jsonObject, new Ack() {
            @Override
            public void call(final Object... args) {
                JSONObject json = null;
                try {
                    json = new JSONObject(args[0].toString());
                    if (json.get("status").equals("false")) {
                        onLeftShelemRoomRecived.onReciced("not Lefted");
                    } else {
                        onLeftShelemRoomRecived.onReciced("Lefted");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void updatePlayerShelemLobby(final onGetUpdatePlayerShelemLobby onGetUpdatePlayerShelemLobby){

        socket.on("userJoined", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gson gson = new Gson();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(args[0].toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                onGetUpdatePlayerShelemLobby.onRecieved(gson.fromJson(jsonObject.toString(), Player.class));

            }
        });

    }






    /*
    -------------------------------------  OLD Method -------------------------------------
    */


    public static void deleteRoom(final int roomID, final deleteRoomListener deleteRoomListener) {
        socket.emit("deleteRoom", roomID , new Ack() {
            @Override
            public void call(Object... args) {
                deleteRoomListener.onDeleted(String.valueOf(args[0]));
            }
        });
    }

    public static void getRecentGame(String username,final onGetRecentGame onGetRecentGame){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("getRecentGame",jsonObject, new Ack() {
            @Override
            public void call(final Object... args) {
                JSONArray array = null;
                try {
                    array = new JSONArray(args[0].toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<RecentGameRooms> recentGameRoomsList=new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    RecentGameRooms recentGameRooms=new RecentGameRooms();
                    Player person1 = new Player();
                    Player person2 = new Player();
                    Player person3 = new Player();
                    Player person4 = new Player();

                    try {

                        recentGameRooms.setMaxPoints(Integer.parseInt(array.getJSONObject(i).get("maxpoint").toString()));

                        recentGameRooms.setRoomLevel(Integer.parseInt(array.getJSONObject(i).get("minlevel").toString()));

                        recentGameRooms.setId(array.getJSONObject(i).get("id").toString());

                        recentGameRooms.setTeemWiner(Integer.parseInt(array.getJSONObject(i).get("teemwiner").toString()));

                        recentGameRooms.setPointPlay(Integer.parseInt(array.getJSONObject(i).get("pointplay").toString()));

                        recentGameRooms.setPointTeemOne(Integer.parseInt(array.getJSONObject(i).get("ptone").toString()));

                        recentGameRooms.setPointTeemTwo(Integer.parseInt(array.getJSONObject(i).get("pttwo").toString()));

                        recentGameRooms.setGame(array.getJSONObject(i).getString("game"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recentGameRooms.setPerson1(person1);
                    recentGameRooms.setPerson2(person2);
                    recentGameRooms.setPerson3(person3);
                    recentGameRooms.setPerson4(person4);
                    recentGameRoomsList.add(recentGameRooms);
                }
                onGetRecentGame.onRevived(recentGameRoomsList);
            }
        });
    }

    public static void getShelemLobbyLeave(final onLeftShelemRoomRecived onLeftShelemRoomRecived){
        socket.on("shelemLobbyLeave", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                onLeftShelemRoomRecived.onReciced(args[0].toString());
            }
        });
    }

    public void getLobbyShelem(final onLobbyRecived onLobbyRecived){
        socket.on("shelemLobby", args -> {
            JSONObject json = (JSONObject) args[0];
            try {
                onLobbyRecived.onReciced(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public static void setStartGameShelem(final onShelemStartGameListener onShelemStartGameListener){
        socket.on("startGame", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if (args[0].toString().equals("true")){
                    onShelemStartGameListener.onRecived("true");
                } else {
                    onShelemStartGameListener.onRecived("false");
                }
            }
        });
    }

    public static void createShelemRoom(String name,String minLevel,String maxPoint, final onCreateShelemRoomListener onCreateShelemRoomListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",name);
            jsonObject.put("minlevel",minLevel);
            jsonObject.put("maxpoint",maxPoint);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        socket.emit("CreateRoomShelem",jsonObject, new Ack() {
            @Override
            public void call(final Object... args) {
                if (args[0].toString().equals("false")) {
                    onCreateShelemRoomListener.onRecived("Room Already Exists");
                } else {
                    onCreateShelemRoomListener.onRecived("Room Created");
                }
            }
        });
    }

    public static void checkStartGame(String roomId,String game) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("roomType",game);
            jsonObject.put("roomId", roomId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        socket.emit("checkStartGame",jsonObject);
    }









    /*
    ------------------------------------- Interface -------------------------------------
     */


    public interface onGetRoomsRecived{
        void onReciced(Room room);
    }

    public interface onJoinShelemRoomRecived{
        void onReciced(String onReciced);
    }

    public interface onLeftShelemRoomRecived{
        void onReciced(String onReciced);
    }

    public interface onLobbyRecived{
        void onReciced(JSONObject jsonObject) throws JSONException;
    }

    public interface onShelemStartGameListener{
        void onRecived(String onRecived);
    }

    public interface onUserDisconnectListener{
        void onRecived(String onRecived);
    }

    public interface onCreateShelemRoomListener{
        void onRecived(String onRecived);
    }

    public interface onGetRecentGame{
        void onRevived(List<RecentGameRooms> recentGameRoomList);
    }


    public interface updateRoomsListener{
        void onRecieved(List<Room> roomList);
    }


    public interface deleteRoomListener{
        void onDeleted(String response);
    }

    ///////////////////////

    public interface onGetUpdatePlayerShelemLobby{
        void onRecieved(Player player);
    }

}
