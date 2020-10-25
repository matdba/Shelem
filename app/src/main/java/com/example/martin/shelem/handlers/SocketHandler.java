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

    public SocketHandler(String socketUrl, Context context) {
        this.socketUrl = socketUrl;
        this.context = context;
    }

    public void socketConect() throws URISyntaxException {
        socket = IO.socket(socketUrl);
        socket.connect();
    }

    public void socketDisconnect() throws URISyntaxException {
        socket = IO.socket(socketUrl);
        socket.disconnect();
    }
}

//    public void setJoinRoomGame(String avatarId,String roomType,String username,String roomId,int playerNumber) throws JSONException {
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("playerUsername",username);
//        jsonObject.put("roomId", roomId);
//        jsonObject.put("roomType", roomType);
//        jsonObject.put("avatarId", avatarId);
//        jsonObject.put("playerNumber", String.valueOf(playerNumber));
//
//        socket.emit("joinRoom",jsonObject, new Ack() {
//            @Override
//            public void call(final Object... args) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if ((boolean) args[0] == false) {
//                            responseListener.onResponseJoinRoomGame("This Room Is Full");
//                        } else {
//                            responseListener.onResponseJoinRoomGame("Joined");
//                        }
//                    }
//                });
//            }
//        });
//
//    }
//
//    public void getCreateRoomGame(String roomMode,String roomName){
//
//        socket.emit("CreateRoomShelem",roomMode,roomName, new Ack() {
//            @Override
//            public void call(final Object... args) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if ((boolean) args[0] == false) {
//                            responseListener.onResponseCreateRoomGame("Room Already Exists");
//                        } else {
//                            responseListener.onResponseCreateRoomGame("Room Created");
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//    public void setShelemPlayerCards(String[] playerOne,String[] playerTwo,String[] playerThree,String[] playerFour,String[] leftOvers_Cards,String roomId,String roomType) {
//        String PlayerOneCards="";
//        String PlayerTwoCards="";
//        String PlayerThreeCards="";
//        String PlayerFourCards="";
//        String leftOversCards="";
//        JSONObject jsonObject = new JSONObject();
//        for (int i = 0; i < playerOne.length; i++) {
//            if (i == playerOne.length - 1){
//                PlayerOneCards+=playerOne[i];
//                PlayerTwoCards+=playerTwo[i];
//                PlayerThreeCards+=playerThree[i];
//                PlayerFourCards+=playerFour[i];
//            }
//            else {
//                PlayerOneCards+=playerOne[i]+",";
//                PlayerTwoCards+=playerTwo[i]+",";
//                PlayerThreeCards+=playerThree[i]+",";
//                PlayerFourCards+=playerFour[i]+",";
//            }
//        }
//        for (int i = 0; i < leftOvers_Cards.length; i++) {
//            if (i==leftOvers_Cards.length-1)
//                leftOversCards += leftOvers_Cards[i];
//            else
//                leftOversCards += leftOvers_Cards[i] + ",";
//        }
//        try {
//            jsonObject.put("playerOne",PlayerOneCards);
//            jsonObject.put("playerTwo",PlayerTwoCards);
//            jsonObject.put("playerThree",PlayerThreeCards);
//            jsonObject.put("playerFour",PlayerFourCards);
//            jsonObject.put("leftOversCards",leftOversCards);
//            jsonObject.put("roomId",roomId);
//            jsonObject.put("roomType",roomType);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        socket.emit("setPlayerCards",jsonObject);
//    }
//
//    public  void sendUserMove(String username,String roomId,String moveCard,String playerNumber) throws JSONException {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("moveCard",moveCard);
//        jsonObject.put("playerUsername", username);
//        jsonObject.put("roomId", roomId);
//        jsonObject.put("palyerNumber", playerNumber);
//        socket.emit("moveDetection",jsonObject);
//    }
//
//    public void setExitRoom(String playerId,String roomId,String roomType) throws JSONException {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("roomType",roomType);
//        jsonObject.put("roomId", roomId);
//        jsonObject.put("playerId", playerId);
//        socket.emit("exitRoom",jsonObject);
//    }
//
//    public void setExitShelemRoom(String username,String roomId,String roomType,String playerNumber) throws JSONException {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("roomType",roomType);
//        jsonObject.put("roomId", roomId);
//        jsonObject.put("playerUsername", username);
//        jsonObject.put("playerNumber", playerNumber);
//        socket.emit("exitPlayerRoom",jsonObject);
//    }
//
//    public void setCheckStart(String roomId,String roomType) throws JSONException {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("roomType",roomType);
//        jsonObject.put("roomId", roomId);
//        socket.emit("checkStartGame",jsonObject);
//    }
//
//    public void getExitRoom(){
//        socket.on("playerExitRoom", new Emitter.Listener() {
//            @Override
//            public void call(final Object... args) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String data = args[0].toString();
//                        responseListener.onResponseExitRoom(data);
//                    }
//                });
//            }
//        });
//    }
//
//    public void getUpdateMove(final onGetUserMove onGetUserMove){
//        socket.on("moveCardUpdate", new Emitter.Listener() {
//            @Override
//            public void call(final Object... args) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        onGetUserMove.ononRecived((JSONObject) args[0]);
//                    }
//                });
//            }
//        });
//    }
//
//    public void getUserDisconnect(){
//        socket.on("userdisconnect", new Emitter.Listener() {
//            @Override
//            public void call(final Object... args) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String data = (String) args[0];
//                        responseListener.onResponseUserDisconnect(data);
//                        //Toast.makeText(ChatBoxActivity.this,data,Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//    }
//

