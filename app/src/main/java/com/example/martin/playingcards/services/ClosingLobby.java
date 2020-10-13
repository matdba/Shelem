package com.example.martin.playingcards.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.martin.playingcards.handlers.SocketService;

import androidx.annotation.Nullable;

public class ClosingLobby extends Service {

    SocketService socketService;
    int socketRoomID, playerNumber;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        socketRoomID = intent.getIntExtra("socketRoomID", 0);
        playerNumber = intent.getIntExtra("playerNumber", 0);


        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.i("kireboz", "onTaskRemoved: " + socketRoomID);
        Log.i("kireboz", "onTaskRemoved: " + playerNumber);
        SocketService.leaveRoom(String.valueOf(socketRoomID), String.valueOf(playerNumber), response -> {  stopSelf(); });
        SocketService.socketDisconnect();
    }
}
