package com.example.martin.playingcards.handlers;

import android.content.Context;

import com.example.martin.playingcards.instances.RecentGameRooms;
import com.example.martin.playingcards.instances.Room;
import com.example.martin.playingcards.instances.Player;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

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
    public Socket socket;
    private Context context;



    public SocketHandler2(Context context){
        this.context = context;
        try {
            socket = IO.socket(URL);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }



    public void socketDisconnect() { socket.disconnect(); }







    /*
    ------------------------------------- Method -------------------------------------
     */





    public void getRooms(final onGetRoomsRecived onRoomsRecived) {

       socket.emit("getRooms", (Ack) args -> {
           JSONObject jsonObject = null;
           try {
               jsonObject = new JSONObject(args[0].toString());
           } catch (JSONException e) {
               e.printStackTrace();
           }
           List<Room> rooms = new ArrayList<>();
           Iterator<String> keys = jsonObject.keys();

           while (keys.hasNext()) {
               String key = keys.next();
               try {
                   JSONArray innerJsonArray =  jsonObject.getJSONArray(key);
                   Room room = new Room();
                   room.setId(innerJsonArray.getJSONObject(0).getString("roomID"));
                   room.setStatus(innerJsonArray.getJSONObject(0).getInt("roomStatus"));
                   room.setJoker(innerJsonArray.getJSONObject(0).getBoolean("isJoker"));
                   room.setMinimumLevel(innerJsonArray.getJSONObject(0).getInt("minLevel"));
                   room.setMaxPoints(innerJsonArray.getJSONObject(0).getInt("maxPoint"));
                   room.setTeamOnePoint(innerJsonArray.getJSONObject(0).getInt("teamOnePoints"));
                   room.setTeamTwoPoint(innerJsonArray.getJSONObject(0).getInt("teamTwoPoints"));

                   for (int i = 0; i < innerJsonArray.length(); i++) {
                       Player player = new Player();
                       player.setRoomID(innerJsonArray.getJSONObject(i).getString("roomID"));
                       player.setUserID(innerJsonArray.getJSONObject(i).getString("userID"));
                       player.setUsername(innerJsonArray.getJSONObject(i).getString("username"));
                       player.setPlayerStatus(innerJsonArray.getJSONObject(i).getInt("playerStatus"));
                       player.setPlayerNumber(innerJsonArray.getJSONObject(i).getInt("playerNumber"));
                       room.playerList.add(player);
                   }
                   rooms.add(room);
               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }
           onRoomsRecived.onReciced(rooms);
       });
    }







    public void updateRooms(final updateRoomsListener updateRoomsListener) {

        socket.on("updateRooms", args -> {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(args[0].toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            List<Room> rooms = new ArrayList<>();
            Iterator<String> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                try {
                    JSONArray innerJsonArray =  jsonObject.getJSONArray(key);
                    Room room = new Room();
                    room.setId(innerJsonArray.getJSONObject(0).getString("roomID"));
                    room.setStatus(innerJsonArray.getJSONObject(0).getInt("roomStatus"));
                    room.setJoker(innerJsonArray.getJSONObject(0).getBoolean("isJoker"));
                    room.setMinimumLevel(innerJsonArray.getJSONObject(0).getInt("minLevel"));
                    room.setMaxPoints(innerJsonArray.getJSONObject(0).getInt("maxPoint"));
                    room.setTeamOnePoint(innerJsonArray.getJSONObject(0).getInt("teamOnePoints"));
                    room.setTeamTwoPoint(innerJsonArray.getJSONObject(0).getInt("teamTwoPoints"));

                    for (int i = 0; i < innerJsonArray.length(); i++) {
                        Player player = new Player();
                        player.setRoomID(innerJsonArray.getJSONObject(i).getString("roomID"));
                        player.setUserID(innerJsonArray.getJSONObject(i).getString("userID"));
                        player.setUsername(innerJsonArray.getJSONObject(i).getString("username"));
                        player.setPlayerStatus(innerJsonArray.getJSONObject(i).getInt("playerStatus"));
                        player.setPlayerNumber(innerJsonArray.getJSONObject(i).getInt("playerNumber"));
                        room.playerList.add(player);
                    }
                    rooms.add(room);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            updateRoomsListener.onRecieved(rooms);
        });
    }







    public void deleteRoom(final String roomID, final deleteRoomListener deleteRoomListener) {
        socket.emit("deleteRoom", roomID ,(Ack) args -> deleteRoomListener.onDeleted(String.valueOf(args[0])));
    }








    public void getRecentGame(String username,final onGetRecentGame onGetRecentGame){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("getRecentGame",jsonObject, new Ack() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
//                                person1.setProfilePic(Integer.parseInt(array.getJSONObject(i).get("picture_One").toString()));
//                                person1.setUsername(array.getJSONObject(i).get("user_One").toString());
//
//
//                                person2.setProfilePic(Integer.parseInt(array.getJSONObject(i).get("picture_Two").toString()));
//                                person2.setUsername(array.getJSONObject(i).get("user_Two").toString());
//
//
//                                person3.setProfilePic(Integer.parseInt(array.getJSONObject(i).get("picture_Three").toString()));
//                                person3.setUsername(array.getJSONObject(i).get("user_Three").toString());
//
//
//                                person4.setProfilePic(Integer.parseInt(array.getJSONObject(i).get("picture_Four").toString()));
//                                person4.setUsername(array.getJSONObject(i).get("user_Four").toString());

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
        });
    }



    public void joinOrLeftRoomGame(String username, String roomId, int playerNumber, final onJoinShelemRoomRecived onJoinShelemRoomRecived) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",username);
            jsonObject.put("roomId", roomId);
            jsonObject.put("playerNumber", String.valueOf(playerNumber));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("joinOrLeftShelemRoom",jsonObject, new Ack() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ( args[0].toString().equals("false")) {
                            onJoinShelemRoomRecived.onReciced("This Room Is Full");
                        } else {
                            onJoinShelemRoomRecived.onReciced("Joined");
                        }
                    }
                });
            }
        });

    }

    public void getShelemLobbyLeave(final onLeftShelemRoomRecived onLeftShelemRoomRecived){
        socket.on("shelemLobbyLeave", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onLeftShelemRoomRecived.onReciced(args[0].toString());
                    }
                });
            }
        });
    }

    public void getLobbyShelem(final onLobbyRecived onLobbyRecived){
        socket.on("shelemLobby", args -> runOnUiThread(() -> {
            JSONObject json = (JSONObject)args[0];
            try {
                onLobbyRecived.onReciced(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }));
    }





    public void setStartGameShelem(final onShelemStartGameListener onShelemStartGameListener){
        socket.on("startGame", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (args[0].toString().equals("true")){
                            onShelemStartGameListener.onRecived("true");
                        } else {
                            onShelemStartGameListener.onRecived("false");
                        }
                    }
                });
            }
        });
    }





    public void createShelemRoom(String name,String minLevel,String maxPoint, final onCreateShelemRoomListener onCreateShelemRoomListener){
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (args[0].toString().equals("false")) {
                            onCreateShelemRoomListener.onRecived("Room Already Exists");
                        } else {
                            onCreateShelemRoomListener.onRecived("Room Created");
                        }
                    }
                });
            }
        });
    }

    public void checkStartGame(String roomId,String game) {
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
        void onReciced(List<Room> rooms);
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

}
