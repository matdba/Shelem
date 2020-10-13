package com.example.martin.shelem.handlers;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.example.martin.shelem.interfaces.ResponseListenerSocketHandler;
import com.example.martin.shelem.instances.RecentGameRooms;
import com.example.martin.shelem.instances.Player;
import com.example.martin.shelem.instances.Room;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class SocketHandler extends AppCompatActivity {
    private String socketUrl;
    private Socket socket;
    private Context context;
    ResponseListenerSocketHandler responseListener;

    public SocketHandler(String socketUrl,Context context){
        this.socketUrl=socketUrl;
        this.context=context;
    }

    public void socketConect() throws URISyntaxException {
        socket = IO.socket(socketUrl);
        socket.connect();
    }

    public void socketDisconnect() throws URISyntaxException {
        socket = IO.socket(socketUrl);
        socket.disconnect();
    }

    public void setJoinRoomGame(String avatarId,String roomType,String username,String roomId,int playerNumber) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("playerUsername",username);
        jsonObject.put("roomId", roomId);
        jsonObject.put("roomType", roomType);
        jsonObject.put("avatarId", avatarId);
        jsonObject.put("playerNumber", String.valueOf(playerNumber));

        socket.emit("joinRoom",jsonObject, new Ack() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((boolean) args[0] == false) {
                            responseListener.onResponseJoinRoomGame("This Room Is Full");
                        } else {
                            responseListener.onResponseJoinRoomGame("Joined");
                        }
                    }
                });
            }
        });

    }

    public void getCreateRoomGame(String roomMode,String roomName){

        socket.emit("CreateRoomShelem",roomMode,roomName, new Ack() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((boolean) args[0] == false) {
                            responseListener.onResponseCreateRoomGame("Room Already Exists");
                        } else {
                            responseListener.onResponseCreateRoomGame("Room Created");
                        }
                    }
                });
            }
        });
    }

    public void setShelemPlayerCards(String[] playerOne,String[] playerTwo,String[] playerThree,String[] playerFour,String[] leftOvers_Cards,String roomId,String roomType) {
        String PlayerOneCards="";
        String PlayerTwoCards="";
        String PlayerThreeCards="";
        String PlayerFourCards="";
        String leftOversCards="";
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < playerOne.length; i++) {
            if (i == playerOne.length - 1){
                PlayerOneCards+=playerOne[i];
                PlayerTwoCards+=playerTwo[i];
                PlayerThreeCards+=playerThree[i];
                PlayerFourCards+=playerFour[i];
            }
            else {
                PlayerOneCards+=playerOne[i]+",";
                PlayerTwoCards+=playerTwo[i]+",";
                PlayerThreeCards+=playerThree[i]+",";
                PlayerFourCards+=playerFour[i]+",";
            }
        }
        for (int i = 0; i < leftOvers_Cards.length; i++) {
            if (i==leftOvers_Cards.length-1)
                leftOversCards += leftOvers_Cards[i];
            else
                leftOversCards += leftOvers_Cards[i] + ",";
        }
        try {
            jsonObject.put("playerOne",PlayerOneCards);
            jsonObject.put("playerTwo",PlayerTwoCards);
            jsonObject.put("playerThree",PlayerThreeCards);
            jsonObject.put("playerFour",PlayerFourCards);
            jsonObject.put("leftOversCards",leftOversCards);
            jsonObject.put("roomId",roomId);
            jsonObject.put("roomType",roomType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("setPlayerCards",jsonObject);
    }

    public  void sendUserMove(String username,String roomId,String moveCard,String playerNumber) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("moveCard",moveCard);
        jsonObject.put("playerUsername", username);
        jsonObject.put("roomId", roomId);
        jsonObject.put("palyerNumber", playerNumber);
        socket.emit("moveDetection",jsonObject);
    }

    public void setExitRoom(String playerId,String roomId,String roomType) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roomType",roomType);
        jsonObject.put("roomId", roomId);
        jsonObject.put("playerId", playerId);
        socket.emit("exitRoom",jsonObject);
    }

    public void setExitShelemRoom(String username,String roomId,String roomType,String playerNumber) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roomType",roomType);
        jsonObject.put("roomId", roomId);
        jsonObject.put("playerUsername", username);
        jsonObject.put("playerNumber", playerNumber);
        socket.emit("exitPlayerRoom",jsonObject);
    }

    public void setCheckStart(String roomId,String roomType) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roomType",roomType);
        jsonObject.put("roomId", roomId);
        socket.emit("checkStartGame",jsonObject);
    }

    public void getExitRoom(){
        socket.on("playerExitRoom", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = args[0].toString();
                        responseListener.onResponseExitRoom(data);
                    }
                });
            }
        });
    }

    public void getUpdateMove(final onGetUserMove onGetUserMove){
        socket.on("moveCardUpdate", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onGetUserMove.ononRecived((JSONObject) args[0]);
                    }
                });
            }
        });
    }

    public void getUserDisconnect(){
        socket.on("userdisconnect", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];
                        responseListener.onResponseUserDisconnect(data);
                        //Toast.makeText(ChatBoxActivity.this,data,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    public void getShelemRooms(final onRoomsRecived onRoomsRecived){
        socket.emit("getRoomsData", new Ack() {
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
                        List<Room> rooms =new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            Room room =new Room();
                            Player person1=new Player();
                            Player person2=new Player();
                            Player person3=new Player();
                            Player person4=new Player();

                            try {
//                                person1.setProfilePic(Integer.parseInt(String.valueOf(array.getJSONObject(i).get("avatarIdP_One"))));
//                                person1.setUsername((String) array.getJSONObject(i).get("p_one"));
//
//
//                                person2.setProfilePic(Integer.parseInt(String.valueOf(array.getJSONObject(i).get("avatarIdP_Two"))));
//                                person2.setUsername((String) array.getJSONObject(i).get("p_two"));
//
//
//                                person3.setProfilePic(Integer.parseInt(String.valueOf(array.getJSONObject(i).get("avatarIdP_Three"))));
//                                person3.setUsername((String) array.getJSONObject(i).get("p_three"));
//
//
//                                person4.setProfilePic(Integer.parseInt(String.valueOf(array.getJSONObject(i).get("avatarIdP_Four"))));
//                                person4.setUsername((String) array.getJSONObject(i).get("p_four"));

                                room.setMaxPoints(Integer.parseInt(String.valueOf( array.getJSONObject(i).get("maxPoint"))));

                                room.setMinimumLevel(Integer.parseInt(String.valueOf(array.getJSONObject(i).get("roomLvl"))));

                                room.setId((String) array.getJSONObject(i).get("roomId"));
//                                if (String.valueOf(array.getJSONObject(i).get("flagBet")).equals("true"))
//                                    shelemRoom.setType("betRoom");
//                                else
//                                    shelemRoom.setType("ezRoom");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                            shelemRoom.setPerson1(person1);
//                            shelemRoom.setPerson2(person2);
//                            shelemRoom.setPerson3(person3);
//                            shelemRoom.setPerson4(person4);
                            rooms.add(room);
                        }

                        onRoomsRecived.onReciced(rooms);
                    }

                });
            }
        });
    } //ok

    public void getLobbyShelem(final onLobbyRecived onLobbyRecived){
        socket.on("shelemLobby", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                         JSONObject json = (JSONObject)args[0];
                        try {
                            onLobbyRecived.onReciced(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    } //ok

    public void getShelemCardsPlayer(final onGetmyCards onGetPlayerCards){
        socket.on("shelemPlayerCards", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onGetPlayerCards.ononRecivedCards((JSONObject) args[0]);
                    }
                });
            }
        });
    }

    public void checkStartGameShelem(final onShelemStartGameLIstener onShelemStartGameLIstener){
        socket.on("startGame", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject json = (JSONObject)args[0];
                        try {
                            onShelemStartGameLIstener.onRecived(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("nasr", "run: "+json.toString());
                    }
                });
            }
        });
    } //ok

    public void getLobbyShelemExit(final onLobbyRecived onLobbyRecived){
        socket.on("shelemLobbyLeave", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                         JSONObject json = (JSONObject)args[0];
                        try {
                            onLobbyRecived.onRecivedExitRoom(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    } //ok

    public void getRecentGame(String username,final onGetrecentGame onGetrecentGame){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",username);
            Log.i("pooof Gaide shodim", "getRecentGame: "+username);
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
                            Player person1=new Player();
                            Player person2=new Player();
                            Player person3=new Player();
                            Player person4=new Player();

                            try {
//                                person1.setProfilePic(Integer.parseInt(String.valueOf(array.getJSONObject(i).get("avatarIdP_One"))));
//                                person1.setUsername((String) array.getJSONObject(i).get("p_one"));
//
//
//                                person2.setProfilePic(Integer.parseInt(String.valueOf(array.getJSONObject(i).get("avatarIdP_Two"))));
//                                person2.setUsername((String) array.getJSONObject(i).get("p_two"));
//
//
//                                person3.setProfilePic(Integer.parseInt(String.valueOf(array.getJSONObject(i).get("avatarIdP_Three"))));
//                                person3.setUsername((String) array.getJSONObject(i).get("p_three"));
//
//
//                                person4.setProfilePic(Integer.parseInt(String.valueOf(array.getJSONObject(i).get("avatarIdP_Four"))));
//                                person4.setUsername((String) array.getJSONObject(i).get("p_four"));

                                recentGameRooms.setMaxPoints(Integer.parseInt(String.valueOf( array.getJSONObject(i).get("maxPoint"))));

                                recentGameRooms.setRoomLevel(Integer.parseInt(String.valueOf(array.getJSONObject(i).get("roomLvl"))));

                                recentGameRooms.setId((String) array.getJSONObject(i).get("roomId"));
                                //
                                recentGameRooms.setTeemWiner(Integer.parseInt(String.valueOf(array.getJSONObject(i).get("teemWiner"))));

                                recentGameRooms.setPointTeemOne(Integer.parseInt(String.valueOf(array.getJSONObject(i).get("pointTeemOne"))));

                                recentGameRooms.setPointTeemTwo(Integer.parseInt(String.valueOf(array.getJSONObject(i).get("pointTeemTwo"))));

                                recentGameRooms.setGame((String) array.getJSONObject(i).getString("game"));

                                if (String.valueOf(array.getJSONObject(i).get("flagBet")).equals("true"))
                                    recentGameRooms.setType("betRoom");
                                else
                                    recentGameRooms.setType("ezRoom");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            recentGameRooms.setPerson1(person1);
                            recentGameRooms.setPerson2(person2);
                            recentGameRooms.setPerson3(person3);
                            recentGameRooms.setPerson4(person4);
                            recentGameRoomsList.add(recentGameRooms);
                        }
                        onGetrecentGame.onRevived(recentGameRoomsList);
                    }
                });
            }
        });
    }

    //////////////////////////

    public interface onRoomsRecived{
        void onReciced (List<Room> rooms);
    }

    public interface onShelemStartGameLIstener{
        void onRecived (JSONObject onRecived) throws JSONException;
    }

    public interface onLobbyRecived{
        void onReciced (JSONObject jsonObject) throws JSONException;
        void onRecivedExitRoom(JSONObject json) throws JSONException;
    }

    public  interface onGetUserMove{
        void ononRecived(JSONObject updateMove);
    }

    public interface onGetmyCards{
        public void ononRecivedCards(JSONObject myCards);
    }

    public interface onGetrecentGame{
        public void onRevived(List<RecentGameRooms> recentGameRoomList);
    }

}
