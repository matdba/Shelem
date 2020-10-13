package com.example.martin.playingcards.activities;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.martin.playingcards.fragments.LoginFragment;
import com.example.martin.playingcards.fragments.SignupFragment;
import com.example.martin.playingcards.handlers.UserDetails;
import com.example.martin.playingcards.R;
import com.example.martin.playingcards.utils.SpringInterpolator;
import com.example.martin.playingcards.utils.UnitHandler;

public class LoginSignupActivity extends AppCompatActivity implements SignupFragment.changeSignupContainerHeightListener {

    RelativeLayout mainActivity;
    FrameLayout signupFragmentContainer, loginFragmentContainer, otherFragmentContainer;
    clearFocusInFragmentListener clearFocusInFragmentListener;
    onBackPressed onBackPressed;

    UnitHandler unitHandler;




    double signupContainerClosedHeight, loginContainerClosedHeight, signupContainerOpenedHeight, loginContainerOpenedHeight;
    boolean isLoginOpen = false, isSignupOpen = false;



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


    private void init() {

        mainActivity = findViewById(R.id.main_activity);


        signupFragmentContainer = findViewById(R.id.container_signup_fragment);
        loginFragmentContainer = findViewById(R.id.container_login_fragment);
        otherFragmentContainer = findViewById(R.id.container_other_fragment);

        unitHandler = new UnitHandler(this);



        loginContainerClosedHeight = unitHandler.getPixels(76) * unitHandler.dpiRatio;
        loginContainerOpenedHeight = unitHandler.getPixels(374);

        signupContainerClosedHeight = unitHandler.getPixels(82) * unitHandler.dpiRatio;
        signupContainerOpenedHeight = unitHandler.getPixels(274);


        signupFragmentContainer.bringToFront();
        otherFragmentContainer.bringToFront();




        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) signupFragmentContainer.getLayoutParams();

        layoutParams.width = unitHandler.screenWidth;
        layoutParams.height = unitHandler.baseScreenHeight;
        layoutParams.bottomMargin = - (int) (unitHandler.baseScreenHeight - signupContainerClosedHeight);
        signupFragmentContainer.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) loginFragmentContainer.getLayoutParams();

        layoutParams1.width = unitHandler.screenWidth;
        layoutParams1.height = unitHandler.baseScreenHeight;
        layoutParams1.bottomMargin = - (int) (unitHandler.baseScreenHeight - (2 * loginContainerClosedHeight));
        loginFragmentContainer.setLayoutParams(layoutParams1);

        getSupportFragmentManager().beginTransaction().add(R.id.container_login_fragment, new LoginFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container_signup_fragment, new SignupFragment()).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        if (new UserDetails(this).isLoggedIN()) {
            startActivity(new Intent(LoginSignupActivity.this, DashboardActivity.class));
            finish();
        }



        init();



        loginFragmentContainer.setOnClickListener(v -> {
            hideKeyboard();
            if (!isLoginOpen) {
                animateBottomMargin(loginFragmentContainer, (int) (- unitHandler.baseScreenHeight + signupContainerClosedHeight + loginContainerOpenedHeight), 1250, new SpringInterpolator());
                animateBottomMargin(signupFragmentContainer, - unitHandler.baseScreenHeight + (int) (signupContainerClosedHeight), 200, null);
                isLoginOpen = true;
                isSignupOpen = false;
            } else {
                clearFocusInFragmentListener.onFocusCleared();
                animateBottomMargin(loginFragmentContainer, - (unitHandler.baseScreenHeight - (int) (2 * loginContainerClosedHeight)), 1250, new SpringInterpolator());
                animateBottomMargin(signupFragmentContainer, - (unitHandler.baseScreenHeight - (int) (signupContainerClosedHeight)), 200, null);
                isLoginOpen = false;
            }
        });



        signupFragmentContainer.setOnClickListener(v -> {
            hideKeyboard();

            if (!isSignupOpen) {
                clearFocusInFragmentListener.onFocusCleared();
                animateBottomMargin(signupFragmentContainer, (int) (- unitHandler.baseScreenHeight + signupContainerOpenedHeight), 1250, new SpringInterpolator());
                animateBottomMargin(loginFragmentContainer, (int) (- unitHandler.baseScreenHeight + signupContainerOpenedHeight + loginContainerClosedHeight), 200, null);
                isLoginOpen = false;
                isSignupOpen = true;
            } else {
                animateBottomMargin(signupFragmentContainer, - (unitHandler.baseScreenHeight - (int) (signupContainerClosedHeight)), 1250, new SpringInterpolator());
                animateBottomMargin(loginFragmentContainer, - (unitHandler.baseScreenHeight - (int) (2 * loginContainerClosedHeight)), 200, null);
                isSignupOpen = false;
            }
        });

    }





    public void animateBottomMargin(final View view, int val, int duration, Interpolator interpolator) {
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(params.bottomMargin, val);
        animator.setInterpolator(interpolator);
        animator.addUpdateListener(valueAnimator -> {
            params.bottomMargin = (Integer) valueAnimator.getAnimatedValue();
            view.requestLayout();
        });
        animator.setDuration(duration);
        animator.start();
    }






    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();

        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }





    public void getLoginFragment(LoginFragment loginFragment) {
        clearFocusInFragmentListener =  loginFragment;
        onBackPressed = loginFragment;
    }



    @Override
    public void onHeightChanged(boolean doExpand) {
        if (doExpand) {
            animateBottomMargin(signupFragmentContainer, (int) (-unitHandler.baseScreenHeight + signupContainerOpenedHeight + loginContainerClosedHeight), 200, null);
            signupFragmentContainer.setClickable(false);
            loginFragmentContainer.setClickable(false);
        } else {
            animateBottomMargin(signupFragmentContainer, (int) (-unitHandler.baseScreenHeight + signupContainerOpenedHeight), 200, null);
            signupFragmentContainer.setClickable(true);
            loginFragmentContainer.setClickable(true);
        }

    }


    @Override
    public void onBackPressed() {
        onBackPressed.onBackPressed();
//        super.onBackPressed();
    }





    public interface clearFocusInFragmentListener {
        void onFocusCleared();
    }


    public interface onBackPressed {
        void onBackPressed();
    }


}
