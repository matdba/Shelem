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
               Room room = null;
               try {
                   jsonObject = new JSONObject(args[0].toString());
                   if (jsonObject.get("status").equals("true")) {
                       room = gson.fromJson(jsonObject.get("room").toString(), Room.class);
                       onRoomsRecived.onRoomReciced(room);

                   } else onRoomsRecived.onCaptionReciced(jsonObject.get("caption").toString());
               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }
       });
    }

    public static void joinShelmRoom(JSONObject player,final onJoinShelemRoomRecived onJoinShelemRoomRecived) {

        socket.emit("joinShelemRoom",player, new Ack() {
            @Override
            public void call(final Object... args) {
                JSONObject jsonObject = null;
                Gson gson = new Gson();
                try {
                    jsonObject = new JSONObject(args[0].toString());
                    if (!jsonObject.get("status").equals("true")) {
                        onJoinShelemRoomRecived.onStatusReciced(false);
                        onJoinShelemRoomRecived.onCaptionReciced(jsonObject.get("caption").toString());
                        onJoinShelemRoomRecived.onStartGameReciced(false);
                    } else {
                        onJoinShelemRoomRecived.onPlayerReciced(gson.fromJson(jsonObject.get("player").toString(), Player.class));
                        onJoinShelemRoomRecived.onStatusReciced(true);
                        if (jsonObject.get("startGame").equals("true")) onJoinShelemRoomRecived.onStartGameReciced(true);
                        else onJoinShelemRoomRecived.onStartGameReciced(false);

                    }
                } catch (JSONException e) { e.printStackTrace(); }

            }
        });
    }

    public static void leftShelemRoom(final String roomID, final int userID,final int playerNumber,final onLeftShelemRoomRecived onLeftShelemRoomRecived){
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

        socket.on("updateLobby", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gson gson = new Gson();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(args[0].toString());
                    Log.i("top", "call: " + args[0].toString());
                    if (jsonObject.get("status").equals("true")){
                        onGetUpdatePlayerShelemLobby.onPlayerReciced(gson.fromJson(jsonObject.get("player").toString(), Player.class));
                        if (jsonObject.get("startGmae").equals("true")) onGetUpdatePlayerShelemLobby.onStartGameReciced(true);
                        else onGetUpdatePlayerShelemLobby.onStartGameReciced(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void createShelemRoom(JSONObject player,String maxPoint,String isJoker ,final onCreateShelemRoomListener onCreateShelemRoomListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("isJoker",isJoker);
            jsonObject.put("maxPoint",maxPoint);
            jsonObject.put("player",player);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        socket.emit("createShelemRoom",jsonObject, new Ack() {
            JSONObject jsonObject = null;
            Gson gson = new Gson();
            @Override
            public void call(final Object... args) {
                try {
                    jsonObject = new JSONObject(args[0].toString());
                    if (jsonObject.get("status").equals("true")) {
                        onCreateShelemRoomListener.onRoomRecived(gson.fromJson(jsonObject.get("room").toString(), Room.class));
                        onCreateShelemRoomListener.onCreateRoomRecived(true);
                    } else {
                        onCreateShelemRoomListener.onCreateRoomRecived(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }






    /*
    -------------------------------------  OLD Method -------------------------------------
    */



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







    /*
    ------------------------------------- Interface -------------------------------------
     */


    public interface onGetRoomsRecived{
        void onRoomReciced(Room room);
        void onCaptionReciced(String onReciced);
    }

    public interface onJoinShelemRoomRecived{
        void onStatusReciced(Boolean onReciced);
        void onPlayerReciced(Player player);
        void onCaptionReciced(String onReciced);
        void onStartGameReciced(Boolean onReciced);
    }

    public interface onLeftShelemRoomRecived{
        void onReciced(String onReciced);
    }

    public interface onCreateShelemRoomListener{
        void onCreateRoomRecived(Boolean onRecived);
        void onRoomRecived(Room onRecived);
    }

    public interface onGetRecentGame{
        void onRevived(List<RecentGameRooms> recentGameRoomList);
    }

    public interface onGetUpdatePlayerShelemLobby{
        void onPlayerReciced(Player player);
        void onStartGameReciced(Boolean onReciced);

    }

}
