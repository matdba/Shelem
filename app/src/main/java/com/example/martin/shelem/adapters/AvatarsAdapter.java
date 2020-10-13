package com.example.martin.shelem.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.martin.shelem.R;
import com.example.martin.shelem.fragments.AvatarSelectionFragment;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AvatarsAdapter extends RecyclerView.Adapter<AvatarsAdapter.AvatarHolder> {

    private Activity activity;
    private String[] avatarsNumber = {"_", "_", "_", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17",
            "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38"};

    private int[] avatarsSelection = {View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
            View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
            View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
            View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
            View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
            View.INVISIBLE, View.INVISIBLE, View.INVISIBLE};

    private int previusSelection;
    private UpdateSelectedAvatarListener updateSelectedAvatarListener;




    public AvatarsAdapter(Activity activity, AvatarSelectionFragment avatarSelectionFragment, int currentAvatarNumber) {
        this.activity = activity;
        previusSelection = currentAvatarNumber + 3;
        avatarsSelection[previusSelection] = View.VISIBLE;
        updateSelectedAvatarListener = avatarSelectionFragment;
    }




    @NonNull
    @Override
    public AvatarHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         return new AvatarHolder(LayoutInflater.from(activity).inflate(R.layout.item_avatar,viewGroup,false));
    }





    @Override
    public void onBindViewHolder(@NonNull AvatarHolder holder, int i) {

        int id = activity.getResources().getIdentifier("avatar_" + avatarsNumber[i] , "drawable", activity.getPackageName());
        holder.avatar.setImageResource(id);

        holder.selected.setVisibility(avatarsSelection[i]);

        holder.root.setOnClickListener(v -> {
            if (i > 2) {
                avatarsSelection[previusSelection] = View.INVISIBLE;
                avatarsSelection[i] = View.VISIBLE;
                previusSelection = i;
                updateSelectedAvatarListener.onSelected(i - 3);
                AvatarsAdapter.this.notifyDataSetChanged();
            }
        });
    }





    @Override
    public int getItemCount() {
        return avatarsNumber.length;
    }





    class AvatarHolder extends RecyclerView.ViewHolder {
        private RelativeLayout root;
        private ImageView avatar, selected;


        AvatarHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            avatar = itemView.findViewById(R.id.img_avatar);
            selected = itemView.findViewById(R.id.img_selected);
        }
    }


    public interface UpdateSelectedAvatarListener {
        void onSelected(int position);
    }

}
