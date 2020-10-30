package com.example.martin.shelem.activities;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import carbon.widget.ImageView;

import android.view.View;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.martin.shelem.R;
import com.example.martin.shelem.fragments.AvatarSelectionFragment;
import com.example.martin.shelem.handlers.AvatarHandler;
import com.example.martin.shelem.handlers.UserDetails;
import com.example.martin.shelem.instances.Room;
import com.example.martin.shelem.interfaces.CloseFragmentListener;
import com.example.martin.shelem.utils.SpringInterpolator;
import com.example.martin.shelem.utils.UnitHandler;

public class ProfileActivity extends BaseActivity implements CloseFragmentListener {

    ScrollView scrollView;
    RelativeLayout header;
    TextView usernameTxt;
    ImageView backImg, avatarImg;
    CardView editContainer;
    RelativeLayout editAvatarBtn;
    RelativeLayout avatarFragmentContainer;


    UnitHandler unitHandler;
    double heightEdit , heightEditClose;

    int preScrollY = 0;
    boolean isElevation = false, isEditVisible = true, isEditOpen = false;




    private void init() {
        scrollView = findViewById(R.id.scroll_statics);
        header = findViewById(R.id.header);
        usernameTxt = findViewById(R.id.txt_username);
        backImg = findViewById(R.id.img_back);
        avatarImg = findViewById(R.id.img_avatar);
        editContainer = findViewById(R.id.container_edit);
        editAvatarBtn = findViewById(R.id.btn_edit_avatar);
        avatarFragmentContainer = findViewById(R.id.container_avatars_fragment);

        unitHandler = new UnitHandler(this);

        heightEdit = unitHandler.getPixels(300);
        heightEditClose = unitHandler.getPixels(250);

        header.bringToFront();

        usernameTxt.setText(UserDetails.getUsername());
        avatarImg.setImageResource(AvatarHandler.fetchAvatar(this, UserDetails.getAvatarNumber()));





        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) editContainer.getLayoutParams();
        layoutParams.height = (int) heightEdit ;
        layoutParams.width = unitHandler.screenWidth;
        layoutParams.bottomMargin = - (int) heightEditClose;
    }






    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        init();


        backImg.setOnClickListener(v -> finish());


        editAvatarBtn.setOnClickListener(v -> {
            avatarFragmentContainer.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.container_avatars_fragment, new AvatarSelectionFragment()).addToBackStack("avatar-fragment").commit();
        });




        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = scrollView.getScrollY();

            if(scrollY < preScrollY && !isEditVisible && !isEditOpen) {
                animateBottomMargin(editContainer, -(int) heightEditClose, null, 200);
                isEditVisible = true;
            } else if (scrollY >= preScrollY && isEditVisible && !isEditOpen) {
                animateBottomMargin(editContainer, - (int) heightEdit, null, 200);
                isEditVisible = false;
            }

            if (scrollY > 0 && !isElevation) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(header, "elevation", 0f, 20f);
                objectAnimator.setDuration(150);
                objectAnimator.start();
                isElevation = true;
            } else if (scrollY <= 0 && isElevation) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(header, "elevation", 20f, 0f);
                objectAnimator.setDuration(150);
                objectAnimator.start();
                isElevation = false;
            }
            preScrollY = scrollY;
        });





        editContainer.setOnClickListener(v -> {
            if (!isEditOpen) {
                animateBottomMargin(editContainer, - unitHandler.getPixels(70), new SpringInterpolator(), 1200);
                animateTopMargin(scrollView, - unitHandler.getPixels(82), new SpringInterpolator(), 1200);
                isEditOpen = true;
            } else {
                animateBottomMargin(editContainer, -(int) heightEditClose, new SpringInterpolator(), 1200);
                animateTopMargin(scrollView, 0, new SpringInterpolator(), 1200);

                isEditOpen = false;
            }
        });
    }





    public void animateBottomMargin(final View view, int bottomMargin, Interpolator interPolator, int duration) {
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(layoutParams.bottomMargin, bottomMargin);
        valueAnimator.setInterpolator(interPolator);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            layoutParams.bottomMargin = (Integer) valueAnimator1.getAnimatedValue();
            view.requestLayout();
        });
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }





    public void animateTopMargin(final View view, int bottomMargin, Interpolator interPolator, int duration) {
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(layoutParams.topMargin, bottomMargin);
        valueAnimator.setInterpolator(interPolator);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            layoutParams.topMargin = (Integer) valueAnimator1.getAnimatedValue();
            view.requestLayout();
        });
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        avatarFragmentContainer.setVisibility(View.INVISIBLE);
    }



    @Override
    public void onFragmentClosed() {
        avatarFragmentContainer.setVisibility(View.INVISIBLE);
        avatarImg.setImageResource(AvatarHandler.fetchAvatar(this, UserDetails.getAvatarNumber()));
    }

    @Override
    public void onFragmentClosedAction(Room room) {

    }
}
