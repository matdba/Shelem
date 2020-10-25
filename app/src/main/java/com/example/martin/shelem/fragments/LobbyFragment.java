package com.example.martin.shelem.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import carbon.widget.ImageView;
import carbon.widget.TextView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.martin.shelem.customViews.CurvedLine;
import com.example.martin.shelem.handlers.AvatarHandler;
import com.example.martin.shelem.handlers.SocketHandler2;
import com.example.martin.shelem.handlers.UserDetails;
import com.example.martin.shelem.instances.Player;
import com.example.martin.shelem.instances.Room;
import com.example.martin.shelem.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class LobbyFragment extends Fragment {

    private View view;
    private RelativeLayout root;
    private TextView maxPointTxt, jokerStatusTxt, jokerStatusLbl;
    private ImageView[] playersAvatars = new ImageView[4];
    private TextView[] playersUsernames = new TextView[4];
    private CardView closeImg;

    private UserDetails userDetails;
    private Room room;

    private boolean sit;
    private int myNumber = 0;


    private void init() {

        root = view.findViewById(R.id.root);


        playersAvatars[0] = view.findViewById(R.id.img_avatar_player_one);
        playersAvatars[1] = view.findViewById(R.id.img_avatar_player_two);
        playersAvatars[2] = view.findViewById(R.id.img_avatar_player_three);
        playersAvatars[3] = view.findViewById(R.id.img_avatar_player_four);

        playersUsernames[0] = view.findViewById(R.id.txt_username_player_one);
        playersUsernames[1] = view.findViewById(R.id.txt_username_player_two);
        playersUsernames[2] = view.findViewById(R.id.txt_username_player_three);
        playersUsernames[3] = view.findViewById(R.id.txt_username_player_four);


        maxPointTxt = view.findViewById(R.id.txt_max_point);
        jokerStatusTxt = view.findViewById(R.id.txt_joker_status);
        jokerStatusLbl = view.findViewById(R.id.lbl_joker_status);

        closeImg = view.findViewById(R.id.btn_close);


        String strtext = getArguments().getString("room");
        Gson gson = new Gson();
        room = gson.fromJson(strtext, Room.class);

        userDetails = new UserDetails(getContext());

        for (int i = 0; i < 4; i++) {
            CurvedLine curvedLine = new CurvedLine(getActivity(), null);
            curvedLine.setStartAngle(i * 90 + 22.5f);
            curvedLine.setSweapAngle(45f);
            root.addView(curvedLine);
        }


//        fromCreateRoomFragment = getArguments().getBoolean("fromCreateRoomFragment", false);


        maxPointTxt.setText(String.valueOf(room.getMaxPoint()));
        if (room.getIsJoker()) {
            jokerStatusTxt.setText("On");
            jokerStatusTxt.setTextColor(getResources().getColor(R.color.carbon_lightGreen_a400));
            jokerStatusLbl.setTextColor(getResources().getColor(R.color.carbon_lightGreen_a400));
        } else {
            jokerStatusTxt.setText("Off");
            jokerStatusTxt.setTextColor(getResources().getColor(R.color.orange));
            jokerStatusLbl.setTextColor(getResources().getColor(R.color.orange));
        }


        initPlayers(room.getPlayers());



    }


    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lobby, container, false);

        init();


        root.setOnClickListener(v -> {
        });




        closeImg.setOnClickListener(v -> {
            closeImg.setClickable(false);
            getUp(myNumber);
            getFragmentManager().beginTransaction().remove(LobbyFragment.this).commit();
        });




        playersAvatars[0].setOnClickListener(view -> {
            sitPlayer(1);
        });

        playersAvatars[1].setOnClickListener(view -> {
            sitPlayer(2);
        });

        playersAvatars[2].setOnClickListener(view -> {
            sitPlayer(3);
        });

        playersAvatars[3].setOnClickListener(view -> {
            sitPlayer(4);
        });



        SocketHandler2.updatePlayerShelemLobby(player -> setPlayer(player));





        return view;
    }

    private void initPlayers(List<Player> players){

        for (int i = 0; i < players.size(); i++) {

            playersAvatars[players.get(i).getPlayerNumber() - 1].setImageResource(AvatarHandler.fetchAvatar(getActivity(), players.get(i).getProfilePictureNum()));
            playersUsernames[players.get(i).getPlayerNumber() - 1].setText(players.get(i).getUsername());

        }

    }

    private void setPlayer(Player player){

        if (player.getUserID() != 0) {
            playersAvatars[player.getPlayerNumber() - 1].setImageResource(AvatarHandler.fetchAvatar(getActivity(), player.getProfilePictureNum()));
            playersUsernames[player.getPlayerNumber() - 1].setText(player.getUsername());
        } else {
            playersAvatars[player.getPlayerNumber() - 1].setImageResource(AvatarHandler.fetchAvatar(getActivity(),0));
            playersUsernames[player.getPlayerNumber() - 1].setText("empty");
        }

    }

    private  boolean canSit(int i){
        if (!sit && playersUsernames[i - 1].getText().equals("")) return true;
        else return false;
    }

    private void sitPlayer(int playerNumber){

        if (canSit(playerNumber)) {
            myNumber = playerNumber;
            JSONObject jsonObject = userDetails.getAllDetails();
            try {
                jsonObject.put("playerStatus", 0);
                jsonObject.put("playerNumber", playerNumber);
                jsonObject.put("roomID", room.getRoomID());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SocketHandler2.joinShelmRoom(jsonObject, onReciced -> { });
            sit = true;
        }

    }

    private void getUp(int number){
        if (number != 0) {
            SocketHandler2.leftShelemRoom(room.getRoomID(), Integer.parseInt(userDetails.getUserID()), number, onReciced -> {
                
            });

        }
    }


}
