package com.example.martin.shelem.activities;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import androidx.annotation.Nullable;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.martin.shelem.handlers.APIHandler;
import com.example.martin.shelem.handlers.AvatarHandler;
import com.example.martin.shelem.instances.RecentGameRooms;
import com.example.martin.shelem.R;
import com.example.martin.shelem.adapters.RecentGamesAdapter;
import com.example.martin.shelem.handlers.UserDetails;
import com.example.martin.shelem.utils.SpringInterpolator;
import com.example.martin.shelem.utils.UnitHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    //declare views-----------------------------------------------------------------------
    CardView headerContainer, profilePicImgContainer;
    RelativeLayout levelBadgeContainer, levelProgressContainer;
    ImageView avatarImg, trophyImg;
    TextView usernameTxt, levelTxt, levelProgressTxt, likesTxt, trophyTxt;

    ScrollView scrollView;
    TextView roomsBtn, soloBtn, withFriendsBtn;

    RecyclerView recentGamesRv;

    RelativeLayout moreContainer;
    RelativeLayout settingsBtn, guideBtn, contactusBtn, logoutBtn, exitBtn;
    //-----------------------------------------------------------------------------------



    //declare classes---------------------------------------------------------------------------
    DisplayMetrics displayMetrics;
    UserDetails userDetails;
    APIHandler apiHandler;
    RecentGamesAdapter recentGamesAdapter;
    RelativeLayout.LayoutParams moreContainerLayoutParams;
    UnitHandler unitHandler;
    Handler handler;
    Runnable runnable;
    //------------------------------------------------------------------------------------



    //declare primitives-----------------------------------------------------------------------------
    List<RecentGameRooms> recentGameRoomsList;
    double moreContainerHeight, moreContainerBottomMargin;
    boolean isMoreOpen = false, isAnimating = false;
    //-----------------------------------------------------------------------------------



    private void init() {

        headerContainer = findViewById(R.id.container_header);
        profilePicImgContainer = findViewById(R.id.container_img_profile_pic);
        levelBadgeContainer = findViewById(R.id.container_level_badge);
        levelProgressContainer = findViewById(R.id.container_level_progress);
        avatarImg = findViewById(R.id.img_avatar);
        trophyImg = findViewById(R.id.img_trophy);
        usernameTxt = findViewById(R.id.txt_username);
        levelTxt = findViewById(R.id.txt_level);
        levelProgressTxt = findViewById(R.id.txt_level_progress);
        likesTxt = findViewById(R.id.txt_likes);
        trophyTxt = findViewById(R.id.txt_trophy);

        scrollView = findViewById(R.id.scrollView);

        roomsBtn = findViewById(R.id.btn_online);
        soloBtn = findViewById(R.id.btn_solo);
        withFriendsBtn = findViewById(R.id.btn_with_friends);

        recentGamesRv = findViewById(R.id.rv_recent_games);

        moreContainer = findViewById(R.id.container_more);

        settingsBtn = findViewById(R.id.btn_settings);
        guideBtn = findViewById(R.id.btn_guide);
        contactusBtn = findViewById(R.id.btn_contact_us);
        logoutBtn = findViewById(R.id.btn_logout);
        exitBtn = findViewById(R.id.btn_exit);



        displayMetrics = new DisplayMetrics();
        userDetails = new UserDetails(this);
        apiHandler = new APIHandler(this);
        unitHandler = new UnitHandler(this);
        moreContainerLayoutParams = (RelativeLayout.LayoutParams) moreContainer.getLayoutParams();
        recentGameRoomsList = new ArrayList<>();

        recentGameRoomsList.add(new RecentGameRooms());
        recentGameRoomsList.add(new RecentGameRooms());
        recentGameRoomsList.add(new RecentGameRooms());
        recentGameRoomsList.add(new RecentGameRooms());
        recentGameRoomsList.add(new RecentGameRooms());
        recentGameRoomsList.add(new RecentGameRooms());
        recentGameRoomsList.add(new RecentGameRooms());


        recentGamesAdapter =new RecentGamesAdapter(DashboardActivity.this,recentGameRoomsList);
        recentGamesRv.setLayoutManager(new LinearLayoutManager(DashboardActivity.this,LinearLayoutManager.VERTICAL,false));
        recentGamesRv.setAdapter(recentGamesAdapter);
        recentGamesRv.setNestedScrollingEnabled(false);




        moreContainerHeight = unitHandler.getPixels(360);
        moreContainerBottomMargin = unitHandler.getPixels(310);


        moreContainerLayoutParams.height = (int) moreContainerHeight;
        moreContainerLayoutParams.width = unitHandler.screenWidth / 2;
        moreContainerLayoutParams.bottomMargin = - (int) moreContainerBottomMargin;


        profilePicImgContainer.bringToFront();
        levelBadgeContainer.bringToFront();

        recentGamesRv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);


        usernameTxt.setText(userDetails.getUsername());
        avatarImg.setImageResource(AvatarHandler.fetchAvatar(this, userDetails.getAvatarNumber()));
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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        handler = new Handler();
        runnable = () -> {
            if (isNetworkAvailable()) {
                new InternetCheck(internet -> Log.i("kireboz", "onCreate: " + internet));
            } else {
                Log.i("kireboz", "not connected");
            }
            handler.postDelayed(runnable, 2000);
        };




        init();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);




        avatarImg.setOnClickListener(v -> startActivityForResult(new Intent(DashboardActivity.this, ProfileActivity.class), 1));



        roomsBtn.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, LobbyActivity.class)));



        withFriendsBtn.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, RoomsActivity.class)));



        moreContainer.setOnClickListener(v -> {
            if (!isMoreOpen) {
                animateBottomMargin(moreContainer, - unitHandler.getPixels(70), new SpringInterpolator(), 1400);
                isMoreOpen = true;
            } else {
                animateBottomMargin(moreContainer, -(int) moreContainerBottomMargin, new SpringInterpolator(), 1400);
                isMoreOpen = false;
            }
        });



        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            float y = 0;

            @Override
            public void onScrollChanged() {
                if (scrollView.getScrollY() > y && !isAnimating) {
                    isAnimating = true;
                    animateLeftMargin(moreContainer, - unitHandler.screenWidth / 2, null, 200);
                } else if (scrollView.getScaleY() <= y && !isAnimating) {
                    isAnimating = true;
                    animateLeftMargin(moreContainer, 0, null, 200);
                }
                y = scrollView.getScrollY();
            }
        });



        logoutBtn.setOnClickListener(v -> {
            userDetails.removeUserInfo();
            startActivity(new Intent(DashboardActivity.this, LoginSignupActivity.class));
            finish();
        });



        exitBtn.setOnClickListener(v -> finish());
    }


    @Override
    protected void onResume() {
        handler.postDelayed(runnable, 2000);
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            avatarImg.setImageResource(AvatarHandler.fetchAvatar(this, userDetails.getAvatarNumber()));
        }
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





    public void animateLeftMargin(final View view, int bottomMargin, Interpolator interPolator, int duration) {
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(layoutParams.leftMargin, bottomMargin);
        valueAnimator.setInterpolator(interPolator);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            layoutParams.leftMargin = (Integer) valueAnimator1.getAnimatedValue();
            view.requestLayout();
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    class InternetCheck extends AsyncTask<Void,Void,Boolean> {

        private Consumer mConsumer;

        public  InternetCheck(Consumer consumer) { mConsumer = consumer; execute(); }

        @Override protected Boolean doInBackground(Void... voids) { try {
            Socket sock = new Socket();
            sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
            sock.close();
            return true;
        } catch (IOException e) { return false; } }

        @Override protected void onPostExecute(Boolean internet) { mConsumer.accept(internet); }
    }


    public interface Consumer { void accept(Boolean internet); }



}
