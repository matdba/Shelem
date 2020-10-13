package com.example.martin.playingcards.activities;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.annotation.RequiresApi;
import carbon.widget.ImageView;
import carbon.widget.TextView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.martin.playingcards.customViews.CircularProgressBar;
import com.example.martin.playingcards.handlers.CardViewHandler;
import com.example.martin.playingcards.handlers.CardsHandler;
import com.example.martin.playingcards.fragments.MenuGameScreenFragmet;
import com.example.martin.playingcards.handlers.SocketService;
import com.example.martin.playingcards.handlers.UserDetails;
import com.example.martin.playingcards.R;

public class GameScreenActivity extends SocketActivity implements MenuGameScreenFragmet.CloseMenuListener {

    //views-----------------------------------------------------
    RelativeLayout root;
    ImageView menuIconImg;
    FrameLayout container;

    RelativeLayout determineClaimContainer;
    ImageView addClaimBtn, subtractClaimBtn;
    TextView claimTxt, submitClaimBtn, passClaimBtn, continueBtn;
    CircularProgressBar timerProgress;
    TextView timerTxt;
    // end of views



    // custom classes-----------------------------------------------------
    CardsHandler cardsHandler;
    UserDetails userDetails;
    CardViewHandler cardViewHandler;
    SocketService socketService;
    CountDownTimer countDownTimer;
    //end of custom classes



    //primitives----------------------------------------------
    // end of primitives


    private void init() {

        root = findViewById(R.id.root);

        menuIconImg = findViewById(R.id.img_menu_icon);
        container = findViewById(R.id.container);

        determineClaimContainer = findViewById(R.id.container_determine_claim);
        addClaimBtn = findViewById(R.id.btn_add_claim);
        subtractClaimBtn = findViewById(R.id.btn_subtract_claim);
        submitClaimBtn = findViewById(R.id.btn_submit_claim);
        passClaimBtn = findViewById(R.id.btn_pass_claim);
        continueBtn = findViewById(R.id.btn_continue);

        timerProgress = findViewById(R.id.progress_timer);
        timerTxt = findViewById(R.id.txt_timer);

        userDetails = new UserDetails(this);
        cardsHandler = new CardsHandler();

        cardViewHandler = new CardViewHandler(this);

        timerProgress.setProgress(100, 0);


        countDownTimer = new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTxt.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timerProgress.setVisibility(View.GONE);
                timerTxt.setVisibility(View.GONE);
                continueBtn.setVisibility(View.GONE);
                cardViewHandler.putAsideLeftoverCards(true);
            }
        };

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        init();

        new Handler().postDelayed(() -> determineClaimContainer.setVisibility(View.VISIBLE), 10000);



        submitClaimBtn.setOnClickListener(v -> {
            cardViewHandler.distributeLeftoverCards();
            cardViewHandler.handleCardsTouch();
            determineClaimContainer.setVisibility(View.GONE);
            continueBtn.setVisibility(View.VISIBLE);
            timerProgress.setVisibility(View.VISIBLE);
            timerTxt.setVisibility(View.VISIBLE);
            timerProgress.setProgress(0, 10000);
            countDownTimer.start();
        });


        continueBtn.setOnClickListener(v -> {

        });


    }















    private void disableEnableView(View v) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                vg.getChildAt(i).setEnabled(true);
            }
        }
    }





    @Override
    public void onCloseMenu() {
        container.setVisibility(View.GONE);
        menuIconImg.setEnabled(true);
        disableEnableView(root);
    }


}
