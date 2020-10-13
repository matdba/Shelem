package com.example.martin.playingcards.activities;

import android.app.Activity;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.martin.playingcards.fragments.CreateRoomFragment;
import com.example.martin.playingcards.fragments.LobbyFragment;
import com.example.martin.playingcards.handlers.SocketHandler2;
import com.example.martin.playingcards.R;
import com.example.martin.playingcards.handlers.UserDetails;
import com.example.martin.playingcards.instances.Room;
import com.example.martin.playingcards.adapters.ShelemRoomAdapter;
import com.example.martin.playingcards.interfaces.CloseFragmentListener;


public class RoomsActivity extends AppCompatActivity implements CloseFragmentListener {

    RecyclerView recyclerView;
    ImageView backImg, addRoomImg;
    public SocketHandler2 socketHandler;
    ShelemRoomAdapter shelemRoomAdapter;
    CardView lobbyFragmentContainer;




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
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        findViewById(R.id.container_fragment_lobby).setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment_lobby, new LobbyFragment()).addToBackStack("lobby").commit();


        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        recyclerView = findViewById(R.id.rv_shelem_rooms);
        backImg = findViewById(R.id.img_back);
        addRoomImg = findViewById(R.id.img_add_room);

        lobbyFragmentContainer = findViewById(R.id.container_fragment_lobby);




        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));



        socketHandler = new SocketHandler2(this);



        socketHandler.getRooms(shelemRooms -> runOnUiThread(() -> {

            shelemRoomAdapter = new ShelemRoomAdapter(RoomsActivity.this, shelemRooms);
            recyclerView.setLayoutManager(new LinearLayoutManager(RoomsActivity.this,LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(shelemRoomAdapter);

        }));



        socketHandler.updateRooms(roomList -> runOnUiThread(() -> {

            shelemRoomAdapter = new ShelemRoomAdapter(RoomsActivity.this, roomList);
            recyclerView.setLayoutManager(new LinearLayoutManager(RoomsActivity.this,LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(shelemRoomAdapter);

        }));




        backImg.setOnClickListener(v -> finish());



        addRoomImg.setOnClickListener(v -> {
            lobbyFragmentContainer.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment_lobby, new CreateRoomFragment()).addToBackStack("add-room").commit();
        });


    }



    @Override
    public void onBackPressed() {
        lobbyFragmentContainer.setVisibility(View.INVISIBLE);
        super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketHandler.socketDisconnect();
    }



    @Override
    public void onFragmentClosed() {
        lobbyFragmentContainer.setVisibility(View.INVISIBLE);
        lobbyFragmentContainer.setVisibility(View.INVISIBLE);
    }



    @Override
    public void onFragmentClosedAction(Room room) {
        LobbyFragment lobbyFragment = new LobbyFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelable("room", room);
        bundle.putBoolean("fromCreateRoomFragment", true);

        lobbyFragment.setArguments(bundle);

        lobbyFragmentContainer.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment_lobby, lobbyFragment).addToBackStack("lobby").commit();
    }
}
