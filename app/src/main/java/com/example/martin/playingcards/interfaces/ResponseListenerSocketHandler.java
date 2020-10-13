package com.example.martin.playingcards.interfaces;

import org.json.JSONArray;

public interface ResponseListenerSocketHandler {

    public void onResponseCreateRoomGame(String response);
    public void onResponseJoinRoomGame(String response);
    public void onResponseUserDisconnect(String response);
    public void onResponseUpdateMove(String response);
    public void onResponseExitRoom(String response);
    public void onResponseRoomsData(JSONArray response);

}
