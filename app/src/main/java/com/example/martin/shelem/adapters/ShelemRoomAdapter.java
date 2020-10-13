package com.example.martin.shelem.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.martin.shelem.R;
import com.example.martin.shelem.instances.Room;
import com.example.martin.shelem.fragments.LobbyFragment;

import java.util.List;

public class ShelemRoomAdapter extends RecyclerView.Adapter<ShelemRoomAdapter.RoomsViewHolder> {

    private Context context;
    private List<Room> rooms;



    public ShelemRoomAdapter(Context context, List<Room> rooms) {
        this.context = context;
        this.rooms = rooms;
    }



    @NonNull
    @Override
    public RoomsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_room,viewGroup,false);
        return new RoomsViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RoomsViewHolder holder, final int i) {
        final Room room = rooms.get(i);

        holder.maxPointTxt.setText(String.valueOf(room.getMaxPoints()));

        if (room.isJoker()) {
            holder.jokerStatusTxt.setText("On");
            holder.jokerStatusTxt.setTextColor(context.getResources().getColor(R.color.carbon_lightGreen_a400));
            holder.jokerStatusLbl.setTextColor(context.getResources().getColor(R.color.carbon_lightGreen_a400));
        } else {
            holder.jokerStatusTxt.setText("Off");
            holder.jokerStatusTxt.setTextColor(context.getResources().getColor(R.color.orange));
            holder.jokerStatusLbl.setTextColor(context.getResources().getColor(R.color.orange));
        }

        holder.minLevelTxt.setText("+" + room.getMinimumLevel());

        for (int j = 0; j < room.playerList.size(); j++) {
            holder.playersUsername[room.playerList.get(j).getPlayerNumber() - 1].setText(room.playerList.get(j).getUsername());
            holder.playersUsername[room.playerList.get(j).getPlayerNumber() - 1].setTextColor(context.getResources().getColor(R.color.black));
        }

        holder.item.setOnClickListener(v -> {
            LobbyFragment lobbyFragment = new LobbyFragment();
            Bundle bundle = new Bundle();

            bundle.putParcelable("room", room);

            lobbyFragment.setArguments(bundle);
            ((Activity) context).findViewById(R.id.container_fragment_lobby).setVisibility(View.VISIBLE);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment_lobby, lobbyFragment).addToBackStack("lobby").commit();

            });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    class RoomsViewHolder extends RecyclerView.ViewHolder {
        CardView item;

        ImageView[] playersPic =new ImageView[4];
        TextView[] playersUsername =new TextView[4];

        TextView maxPointTxt, jokerStatusTxt, minLevelTxt, jokerStatusLbl;

        RoomsViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);

            playersPic[0] = itemView.findViewById(R.id.img_player_one);
            playersPic[1] = itemView.findViewById(R.id.img_player_two);
            playersPic[2] = itemView.findViewById(R.id.img_player_three);
            playersPic[3] = itemView.findViewById(R.id.img_player_four);

            playersUsername[0] = itemView.findViewById(R.id.txt_username_player_one);
            playersUsername[1] = itemView.findViewById(R.id.txt_username_player_two);
            playersUsername[2] = itemView.findViewById(R.id.txt_username_player_three);
            playersUsername[3] = itemView.findViewById(R.id.txt_username_player_four);

            maxPointTxt = itemView.findViewById(R.id.txt_max_point);
            jokerStatusTxt = itemView.findViewById(R.id.txt_joker_status);
            minLevelTxt = itemView.findViewById(R.id.txt_min_level);
            jokerStatusLbl = itemView.findViewById(R.id.lbl_joker_status);
        }
    }



    public void updateData (List<Room> items) {
        if (items != null && items.size() > 0) {
            rooms.clear();
            rooms.addAll(items);
            notifyDataSetChanged();
        }
    }
}

