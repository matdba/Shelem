package com.example.martin.shelem.activities;


import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.martin.shelem.fragments.CreateRoomFragment;
import com.example.martin.shelem.fragments.LobbyFragment;
import com.example.martin.shelem.handlers.SocketHandler2;
import com.example.martin.shelem.R;
import com.example.martin.shelem.handlers.UserDetails;
import com.example.martin.shelem.instances.Room;
import com.example.martin.shelem.interfaces.CloseFragmentListener;
import com.google.gson.Gson;


public class RoomsActivity extends BaseActivity implements CloseFragmentListener {

    ImageView backImg, addRoomImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);


        backImg = findViewById(R.id.img_back);
        addRoomImg = findViewById(R.id.img_add_room);


        SocketHandler2.getRoom("70" , room -> RoomsActivity.this.runOnUiThread(() -> {
            LobbyFragment lobbyFragment = new LobbyFragment();
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            bundle.putString("room", gson.toJson(room, Room.class));

            lobbyFragment.setArguments(bundle);
            findViewById(R.id.container_fragment_lobby).setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment_lobby, lobbyFragment).addToBackStack("lobby").commit();
        }));


        backImg.setOnClickListener(v -> finish());


        addRoomImg.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment_lobby, new CreateRoomFragment()).addToBackStack("add-room").commit();
        });


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void onFragmentClosed() {

    }



    @Override
    public void onFragmentClosedAction(Room room) {
        LobbyFragment lobbyFragment = new LobbyFragment();
        Bundle bundle = new Bundle();

        Gson gson = new Gson();
        bundle.putString("room", gson.toJson(room, Room.class));
        bundle.putBoolean("fromCreateRoomFragment", true);

        lobbyFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment_lobby, lobbyFragment).addToBackStack("lobby").commit();
    }
}
