package com.example.martin.shelem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.martin.shelem.R;
import com.example.martin.shelem.adapters.AvatarsAdapter;
import com.example.martin.shelem.handlers.APIHandler;
import com.example.martin.shelem.handlers.AvatarHandler;
import com.example.martin.shelem.handlers.UserDetails;
import com.example.martin.shelem.interfaces.CloseFragmentListener;

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


    private AvatarsAdapter avatarsAdapter;

    private String selectedAvatarNumber = "0";




    private void init() {
        avatarsRv = view.findViewById(R.id.rv_avatars);
        closeBtn = view.findViewById(R.id.btn_close);
        submitBtn = view.findViewById(R.id.btn_submit);
        selectedAvatarImg = view.findViewById(R.id.img_selected_avatar);

        closeFragmentListener = (CloseFragmentListener) getActivity();



        avatarsAdapter = new AvatarsAdapter(getActivity(), this, UserDetails.getAvatarNumber());
        avatarsRv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        avatarsRv.setAdapter(avatarsAdapter);
        selectedAvatarImg.setImageResource(AvatarHandler.fetchAvatar(getActivity(), UserDetails.getAvatarNumber()));
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
            APIHandler.updateAvatarNumber(UserDetails.getUserID(), selectedAvatarNumber, response -> {
                if (response.equals("success")) {
                    getFragmentManager().popBackStack();
                    UserDetails.setAvatarNumber(Integer.parseInt(selectedAvatarNumber));
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
