package com.example.martin.playingcards.fragments;

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

import com.example.martin.playingcards.handlers.SocketHandler2;
import com.example.martin.playingcards.handlers.UserDetails;
import com.example.martin.playingcards.instances.Room;
import com.example.martin.playingcards.interfaces.CloseFragmentListener;
import com.example.martin.playingcards.R;


public class LobbyFragment extends Fragment {

    private View view;
    private CardView root;

    private ImageView closeImg;


    private TextView maxPointTxt, jokerStatusTxt, minLevelTxt, jokerStatusLbl;
    private ImageView[] playersAvatars = new ImageView[4];
    private TextView[] playersUsernames = new TextView[4];

    private TextView readyBtn, getupBtn;


    private SocketHandler2 socketHandler;
    private UserDetails userDetails;
    private CloseFragmentListener closeFragmentListener;
    private Room room;



    private boolean fromCreateRoomFragment;
    private int joinedPlayersNum = 1;





    private void init() {

        root = view.findViewById(R.id.root);

        closeImg = view.findViewById(R.id.btn_close);

        playersAvatars[0] = view.findViewById(R.id.img_avatar_player_one);
        playersAvatars[1] = view.findViewById(R.id.img_avatar_player_two);
        playersAvatars[2] = view.findViewById(R.id.img_avatar_player_three);
        playersAvatars[3] = view.findViewById(R.id.img_avatar_player_four);

        playersUsernames[0] = view.findViewById(R.id.txt_username_player_one);
        playersUsernames[1] = view.findViewById(R.id.txt_username_player_two);
        playersUsernames[2] = view.findViewById(R.id.txt_username_player_three);
        playersUsernames[3] = view.findViewById(R.id.txt_username_player_four);

        playersUsernames[3].setText("hfhtf");

        readyBtn = view.findViewById(R.id.btn_ready);
        getupBtn = view.findViewById(R.id.btn_getup);

        maxPointTxt = view.findViewById(R.id.txt_max_point);
        jokerStatusTxt = view.findViewById(R.id.txt_joker_status);
        minLevelTxt = view.findViewById(R.id.txt_min_level);
        jokerStatusLbl = view.findViewById(R.id.lbl_joker_status);



//        room = getArguments().getParcelable("room");
        room = new Room();


        socketHandler = new SocketHandler2(getActivity());
        userDetails = new UserDetails(getContext());

        closeFragmentListener = (CloseFragmentListener) getActivity();



//        fromCreateRoomFragment = getArguments().getBoolean("fromCreateRoomFragment", false);



        maxPointTxt.setText(String.valueOf(room.getMaxPoints()));
        if (room.isJoker()) {
            jokerStatusTxt.setText("On");
            jokerStatusTxt.setTextColor(getResources().getColor(R.color.carbon_lightGreen_a400));
            jokerStatusLbl.setTextColor(getResources().getColor(R.color.carbon_lightGreen_a400));
        } else {
            jokerStatusTxt.setText("Off");
            jokerStatusTxt.setTextColor(getResources().getColor(R.color.orange));
            jokerStatusLbl.setTextColor(getResources().getColor(R.color.orange));
        }
        minLevelTxt.setText("+" + room.getMinimumLevel());


        if (fromCreateRoomFragment) {
            playersUsernames[0].setText(userDetails.getUsername());
            playersUsernames[0].setTextColor(getActivity().getResources().getColor(R.color.carbon_lightGreen_a400));
            readyBtn.setBackgroundResource(R.color.blue);
            readyBtn.setText("Unready");
            readyBtn.setElevationShadowColor(R.color.blue);
        }

    }





    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lobby, container, false);

        init();



        root.setOnClickListener(v -> { });



        closeImg.setOnClickListener(v -> {
            closeImg.setClickable(false);
            if (joinedPlayersNum == 1) {
                socketHandler.deleteRoom(room.getId(), response -> {
                    if (response.equals("done")) {
                        getFragmentManager().popBackStack();
                        closeFragmentListener.onFragmentClosed();
                    } else {
                        closeImg.setClickable(true);
                    }
                });
            } else {
                getFragmentManager().popBackStack();
                closeFragmentListener.onFragmentClosed();
            }
        });








        return view;
    }






}
