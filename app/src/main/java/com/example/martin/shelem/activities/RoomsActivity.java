package com.example.martin.shelem.activities;

import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;
import android.widget.ImageView;

import com.example.martin.shelem.fragments.CreateRoomFragment;
import com.example.martin.shelem.fragments.LobbyFragment;
import com.example.martin.shelem.handlers.SocketHandler2;
import com.example.martin.shelem.R;
import com.example.martin.shelem.handlers.UserDetails;
import com.example.martin.shelem.instances.Room;
import com.example.martin.shelem.interfaces.CloseFragmentListener;
import com.google.gson.Gson;


public class RoomsActivity extends AppCompatActivity implements CloseFragmentListener {

    RecyclerView recyclerView;
    ImageView backImg, addRoomImg;
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
        //getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment_lobby, new LobbyFragment()).addToBackStack("lobby").commit();


        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        recyclerView = findViewById(R.id.rv_shelem_rooms);
        backImg = findViewById(R.id.img_back);
        addRoomImg = findViewById(R.id.img_add_room);

        lobbyFragmentContainer = findViewById(R.id.container_fragment_lobby);




        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));





        SocketHandler2.getRoom("70" ,new SocketHandler2.onGetRoomsRecived() {
            @Override
            public void onReciced(Room room) {
                RoomsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LobbyFragment lobbyFragment = new LobbyFragment();
                        Bundle bundle = new Bundle();
                        Gson gson = new Gson();
                        bundle.putString("room", gson.toJson(room, Room.class));

                        lobbyFragment.setArguments(bundle);
                        findViewById(R.id.container_fragment_lobby).setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment_lobby, lobbyFragment).addToBackStack("lobby").commit();


                    }
                });
            }
        });





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

        //bundle.putParcelable("room", room);
        Gson gson = new Gson();
        bundle.putString("room", gson.toJson(room, Room.class));
        bundle.putBoolean("fromCreateRoomFragment", true);

        lobbyFragment.setArguments(bundle);

        lobbyFragmentContainer.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment_lobby, lobbyFragment).addToBackStack("lobby").commit();
    }
}
