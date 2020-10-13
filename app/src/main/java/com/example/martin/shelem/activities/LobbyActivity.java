package com.example.martin.shelem.activities;

import androidx.annotation.RequiresApi;
import carbon.widget.ImageView;
import carbon.widget.TextView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.martin.shelem.R;
import com.example.martin.shelem.handlers.AvatarHandler;
import com.example.martin.shelem.handlers.SocketService;
import com.example.martin.shelem.handlers.UserDetails;
import com.example.martin.shelem.instances.Player;
import com.example.martin.shelem.services.ClosingLobby;
import com.example.martin.shelem.utils.PlayersMatchingHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LobbyActivity extends SocketActivity {

    private ImageView closeImg;


    private ImageView[] playersAvatars = new ImageView[4];
    private TextView[] playersUsernames = new TextView[4];



    UserDetails userDetails;

    private int socketRoomID, playerNumber;

    Player[] players;


    private void init() {

        closeImg = findViewById(R.id.btn_close);

        playersAvatars[0] = findViewById(R.id.img_avatar_player_one);
        playersAvatars[1] = findViewById(R.id.img_avatar_player_two);
        playersAvatars[2] = findViewById(R.id.img_avatar_player_three);
        playersAvatars[3] = findViewById(R.id.img_avatar_player_four);

        playersUsernames[0] = findViewById(R.id.txt_username_player_one);
        playersUsernames[1] = findViewById(R.id.txt_username_player_two);
        playersUsernames[2] = findViewById(R.id.txt_username_player_three);
        playersUsernames[3] = findViewById(R.id.txt_username_player_four);



        userDetails = new UserDetails(this);




        players = new Player[4];





        SocketService.findRoom(userDetails.getUserID(), userDetails.getUsername(), String.valueOf(userDetails.getAvatarNumber()),
                (socketRoomID, playerNumber, jsonArray) -> runOnUiThread(() -> {
            players[0] = new Player();
            this.socketRoomID = socketRoomID;
            this.playerNumber = playerNumber;
            startClosingService();
            try {
                jsonArrayToPlayers(jsonArray);
            } catch (JSONException e) { e.printStackTrace(); }
            handlePlayersSeats();
        }));
    }





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        init();



        closeImg.setOnClickListener(v -> {
            SocketService.leaveRoom(String.valueOf(socketRoomID), String.valueOf(playerNumber), response -> { });
            SocketService.socketDisconnect();
            finish();
        });



        SocketService.userJoined(player -> runOnUiThread(() -> {
            addNewPlayerToPlayers(player);
            handlePlayersSeats();
         }));



        SocketService.userLeft(playerNumber -> runOnUiThread(() -> {
            deletePlayerFromPlayers(playerNumber);
            handlePlayersSeats();
        }));



        SocketService.readyToGo(() -> runOnUiThread(() -> {
            Intent intent = new Intent(LobbyActivity.this, GameScreenActivity.class);
            intent.putExtra("player_one", players[0]);
            intent.putExtra("player_two", players[1]);
            intent.putExtra("player_three", players[2]);
            intent.putExtra("player_four", players[3]);
            startActivity(intent);
            finish();
        }));




    }




    private void handlePlayersSeats() {
        for (int i = 0; i <players.length ; i++) {
            if (players[i] != null) {

                playersUsernames[i].setText(players[i].getUsername());
                playersAvatars[i].setImageResource(AvatarHandler.fetchAvatar(this, players[i].getAvatarNumber()));
                if (i == 0) playersUsernames[i].setTextColor(getResources().getColor(R.color.carbon_lightGreen_a400));
                else playersUsernames[i].setTextColor(getResources().getColor(R.color.black));

            } else {

                playersUsernames[i].setText("");
                playersAvatars[i].setImageResource(R.drawable.avatar_empy);

            }
        }
    }





    private void jsonArrayToPlayers(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int index = PlayersMatchingHandler.handle(playerNumber, jsonObject.getInt("playerNumber"));

            players[index] = new Player();
            players[index].setUserID(jsonObject.getString("userID"));
            players[index].setUsername(jsonObject.getString("username"));
            players[index].setAvatarNumber(jsonObject.getInt("avatarNumber"));
            players[index].setPlayerNumber(jsonObject.getInt("playerNumber"));

        }

        if (jsonArray.length() == 4) {
            Intent intent = new Intent(LobbyActivity.this, GameScreenActivity.class);
            intent.putExtra("player_one", players[0]);
            intent.putExtra("player_two", players[1]);
            intent.putExtra("player_three", players[2]);
            intent.putExtra("player_four", players[3]);
            startActivity(intent);
            finish();
        }
    }


    private void addNewPlayerToPlayers(Player player) {
        int index = PlayersMatchingHandler.handle(playerNumber, player.getPlayerNumber());
        players[index] = new Player();
        players[index].setUserID(player.getUserID());
        players[index].setUsername(player.getUsername());
        players[index].setAvatarNumber(player.getAvatarNumber());
        players[index].setPlayerNumber(player.getPlayerNumber());
    }






    private void deletePlayerFromPlayers(int playerNumber) {
        for (int i = 0; i < players.length; i++)
            if (players[i] != null && players[i].getPlayerNumber() == playerNumber)
                players[i] = null;
    }


    private void startClosingService() {
        Intent intent = new Intent(this, ClosingLobby.class);
        intent.putExtra("socketRoomID", socketRoomID);
        intent.putExtra("playerNumber", playerNumber);
        startService(intent);
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SocketService.leaveRoom(String.valueOf(socketRoomID), String.valueOf(playerNumber), response -> { });
        SocketService.socketDisconnect();
    }
}
