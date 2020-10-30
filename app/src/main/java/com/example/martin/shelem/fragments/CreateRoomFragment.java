package com.example.martin.shelem.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.martin.shelem.R;
import com.example.martin.shelem.activities.RoomsActivity;
import com.example.martin.shelem.handlers.APIHandler;
import com.example.martin.shelem.handlers.SocketHandler2;
import com.example.martin.shelem.handlers.UserDetails;
import com.example.martin.shelem.instances.Room;
import com.example.martin.shelem.interfaces.CloseFragmentListener;
import com.example.martin.shelem.utils.UnitHandler;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import carbon.widget.TextView;

public class CreateRoomFragment extends Fragment {
    private View view;
    private ImageView backImg;
    private RelativeLayout root;
    private TextView point300Txt, point600Txt, point900Txt;
    private TextView jokerOnTxt, jokerOffTxt;
    private TextView minLevelTxt;
    private ImageView addLevelBtn, subtractLevelBtn, createRoombtn;



    private UnitHandler unitHandler;
    private CloseFragmentListener closeFragmentListener;

    private int point = 300;
    private boolean joker = true;
    private int minLevel = 1;

    private void init() {

        root = view.findViewById(R.id.root);

        backImg = view.findViewById(R.id.img_back);

        point300Txt = view.findViewById(R.id.txt_300_point);
        point600Txt = view.findViewById(R.id.txt_600_point);
        point900Txt = view.findViewById(R.id.txt_900_point);
        jokerOnTxt = view.findViewById(R.id.txt_joker_on);
        jokerOffTxt = view.findViewById(R.id.txt_joker_off);
        minLevelTxt = view.findViewById(R.id.txt_min_level);
        addLevelBtn = view.findViewById(R.id.btn_add_level);
        subtractLevelBtn = view.findViewById(R.id.btn_subtract_level);

        createRoombtn = view.findViewById(R.id.btn_create_room);



        unitHandler = new UnitHandler(getActivity());
        closeFragmentListener = (CloseFragmentListener) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_craete_room, container, false);

        init();


        root.setOnClickListener(v -> {});



        backImg.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
            closeFragmentListener.onFragmentClosed();
        });



        point300Txt.setOnClickListener(v -> {
            point = 300;
            point300Txt.setBackgroundResource(R.color.yellow);
            point300Txt.setCornerRadius(unitHandler.getPixels(8));
            point600Txt.setBackgroundResource(R.drawable.background_border_4dp_yellow_corner_8dp);
            point900Txt.setBackgroundResource(R.drawable.background_border_4dp_yellow_corner_8dp);
        });



        point600Txt.setOnClickListener(v -> {
            point = 600;
            point600Txt.setBackgroundResource(R.color.yellow);
            point600Txt.setCornerRadius(unitHandler.getPixels(8));
            point300Txt.setBackgroundResource(R.drawable.background_border_4dp_yellow_corner_8dp);
            point900Txt.setBackgroundResource(R.drawable.background_border_4dp_yellow_corner_8dp);
        });



        point900Txt.setOnClickListener(v -> {
            point = 900;
            point900Txt.setBackgroundResource(R.color.yellow);
            point900Txt.setCornerRadius(unitHandler.getPixels(8));
            point300Txt.setBackgroundResource(R.drawable.background_border_4dp_yellow_corner_8dp);
            point600Txt.setBackgroundResource(R.drawable.background_border_4dp_yellow_corner_8dp);
        });





        jokerOnTxt.setOnClickListener(v -> {
            joker = true;
            jokerOnTxt.setBackgroundResource(R.color.carbon_lightGreen_a400);
            jokerOnTxt.setCornerRadius(unitHandler.getPixels(8));
            jokerOffTxt.setBackgroundResource(R.drawable.background_border_4dp_red_corner_8dp);
        });



        jokerOffTxt.setOnClickListener(v -> {
            joker = false;
            jokerOffTxt.setBackgroundResource(R.color.orange);
            jokerOffTxt.setCornerRadius(unitHandler.getPixels(8));
            jokerOnTxt.setBackgroundResource(R.drawable.background_border_4dp_green_corner_8dp);
        });



        addLevelBtn.setOnClickListener(v -> {
            if (minLevel < 99) {
                minLevel++;
                minLevelTxt.setText(String.valueOf(minLevel));
            }
        });



        subtractLevelBtn.setOnClickListener(v -> {
            if (minLevel > 1) {
                minLevel--;
                minLevelTxt.setText(String.valueOf(minLevel));
            }
        });





        createRoombtn.setOnClickListener(v -> {
            createRoombtn.setClickable(false);

            SocketHandler2.createShelemRoom(UserDetails.getAllDetails(), String.valueOf(point), String.valueOf(joker), new SocketHandler2.onCreateShelemRoomListener() {

                @Override
                public void onRoomRecived(Room onRecived) {

                    LobbyFragment lobbyFragment = new LobbyFragment();
                    Bundle bundle = new Bundle();
                    Gson gson = new Gson();
                    bundle.putString("room", gson.toJson(onRecived, Room.class));
                    bundle.putString("fromCreateRoom","true");
                    lobbyFragment.setArguments(bundle);
                    getActivity().findViewById(R.id.container_fragment_lobby).setVisibility(View.VISIBLE);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment_lobby, lobbyFragment).addToBackStack("lobby").commit();

                }

                @Override
                public void onCreateRoomRecived(Boolean onRecived) {

                }


            });
        });




        return view;
    }
}
