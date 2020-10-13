package com.example.martin.playingcards.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.martin.playingcards.R;
import com.example.martin.playingcards.adapters.AvatarsAdapter;
import com.example.martin.playingcards.handlers.APIHandler;
import com.example.martin.playingcards.handlers.AvatarHandler;
import com.example.martin.playingcards.handlers.UserDetails;
import com.example.martin.playingcards.interfaces.CloseFragmentListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class AvatarSelectionFragment extends Fragment implements AvatarsAdapter.UpdateSelectedAvatarListener {

    private View view;
    private RecyclerView avatarsRv;
    private ImageView closeBtn, submitBtn, selectedAvatarImg;
    private CloseFragmentListener closeFragmentListener;


    private APIHandler apiHandler;
    private UserDetails userDetails;
    private AvatarsAdapter avatarsAdapter;

    private String selectedAvatarNumber = "0";




    private void init() {
        avatarsRv = view.findViewById(R.id.rv_avatars);
        closeBtn = view.findViewById(R.id.btn_close);
        submitBtn = view.findViewById(R.id.btn_submit);
        selectedAvatarImg = view.findViewById(R.id.img_selected_avatar);

        closeFragmentListener = (CloseFragmentListener) getActivity();

        apiHandler = new APIHandler(getActivity());
        userDetails = new UserDetails(getActivity());


        avatarsAdapter = new AvatarsAdapter(getActivity(), this, userDetails.getAvatarNumber());
        avatarsRv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        avatarsRv.setAdapter(avatarsAdapter);
        selectedAvatarImg.setImageResource(AvatarHandler.fetchAvatar(getActivity(), userDetails.getAvatarNumber()));
        OverScrollDecoratorHelper.setUpOverScroll(avatarsRv, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_avatar_selection, container, false);

        init();



        closeBtn.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
            closeFragmentListener.onFragmentClosed();
        });



        submitBtn.setOnClickListener(v ->
            apiHandler.updateAvatarNumber(userDetails.getUserID(), selectedAvatarNumber, response -> {
                if (response.equals("success")) {
                    getFragmentManager().popBackStack();
                    userDetails.setAvatarNumber(Integer.parseInt(selectedAvatarNumber));
                    closeFragmentListener.onFragmentClosed();
                } else { Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show(); }
            }));
        return view;
    }





    @Override
    public void onSelected(int position) {
        selectedAvatarNumber = String.valueOf(position);
        selectedAvatarImg.setImageResource(AvatarHandler.fetchAvatar(getActivity(), position));
    }
}
