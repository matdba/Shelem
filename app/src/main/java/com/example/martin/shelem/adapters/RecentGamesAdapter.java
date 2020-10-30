package com.example.martin.shelem.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.martin.shelem.R;
import com.example.martin.shelem.instances.RecentGameRooms;
import com.example.martin.shelem.handlers.UserDetails;

import java.util.List;

public class RecentGamesAdapter extends RecyclerView.Adapter<RecentGamesAdapter.RecentGamesHoldler> {


    private Context context;
    int green,liver;
    private List<RecentGameRooms> recentGameRoomsList;

    public RecentGamesAdapter(Context context, List<RecentGameRooms>recentGameRoomsList){
        this.context = context;
        this.recentGameRoomsList = recentGameRoomsList;
        green = context.getResources().getColor(R.color.green);
        liver = context.getResources().getColor(R.color.liver);
    }
    @NonNull
    @Override
    public RecentGamesHoldler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         return new RecentGamesHoldler(LayoutInflater.from(context).inflate(R.layout.item_recent_game,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecentGamesHoldler recentGamesHoldler, int i) {

        if (i % 2 == 0) {
            recentGamesHoldler.condition.setImageResource(R.drawable.ic_win);
            recentGamesHoldler.layout.setBackgroundResource(R.drawable.background_recent_games_win);
            recentGamesHoldler.game.setTextColor(green);
            recentGamesHoldler.point.setTextColor(green);
        } else {
            recentGamesHoldler.condition.setImageResource(R.drawable.ic_lose);
            recentGamesHoldler.layout.setBackgroundResource(R.drawable.background_recent_games_lost);
            recentGamesHoldler.game.setTextColor(liver);
            recentGamesHoldler.point.setTextColor(liver);
        }





    }

    @Override
    public int getItemCount() {
        return recentGameRoomsList.size();
    }

    public class RecentGamesHoldler extends RecyclerView.ViewHolder {
        private ImageView playerOne,playerTwo,playerThree,playerFour,condition;
        private TextView point,game;
        private RelativeLayout layout;
        public RecentGamesHoldler(@NonNull View itemView) {
            super(itemView);
            playerOne = itemView.findViewById(R.id.img_player_one);
            playerTwo = itemView.findViewById(R.id.img_player_two);
            playerThree = itemView.findViewById(R.id.img_player_three);
            playerFour = itemView.findViewById(R.id.img_player_four);
            condition = itemView.findViewById(R.id.img_condition);
            point = itemView.findViewById(R.id.txt_point);
            game = itemView.findViewById(R.id.txt_time);
            layout = itemView.findViewById(R.id.layout_item_recent_game);
        }
    }
}
