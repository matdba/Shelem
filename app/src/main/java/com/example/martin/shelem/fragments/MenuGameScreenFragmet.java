package com.example.martin.shelem.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.martin.shelem.R;

public class MenuGameScreenFragmet extends Fragment {
    View view;
    RelativeLayout root, root2;
    ImageView closeImg;
    CloseMenuListener closeMenuListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_game_screen, container, false);

        root = view.findViewById(R.id.root);
        root2 = view.findViewById(R.id.root2);
        closeImg = view.findViewById(R.id.btn_close);

        closeMenuListener = (CloseMenuListener) getActivity();
        closeImg.setVisibility(View.GONE);

        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setScaleAnimationReverse(closeImg);

            }
        });


        setScaleAnimation(root2, 300);


        return view;
    }

    private void setScaleAnimationReverse(final View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        scaleAnimation.setDuration(150);
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                closeImg.setVisibility(View.INVISIBLE);
                ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
                scaleAnimation.setDuration(150);
                scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

                scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ScaleAnimation scaleAnimation = new ScaleAnimation(1.1f, 0, 1.1f, 0, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
                        scaleAnimation.setDuration(300);
                        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                        root2.startAnimation(scaleAnimation);
                        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                getActivity().getSupportFragmentManager().beginTransaction().remove(MenuGameScreenFragmet.this).commit();
                                closeMenuListener.onCloseMenu();

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                root2.startAnimation(scaleAnimation);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(scaleAnimation);
    }


    private void setScaleAnimation(final View view, int duration) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1.1f, 0, 1.1f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.1f, 1, 1.1f, 1, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
                scaleAnimation.setDuration(150);
                scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

                scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        closeImg.setVisibility(View.VISIBLE);
                        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
                        scaleAnimation.setDuration(150);
                        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                        closeImg.startAnimation(scaleAnimation);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(scaleAnimation);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(scaleAnimation);
    }


    public interface CloseMenuListener {
        public void onCloseMenu();
    }
}
