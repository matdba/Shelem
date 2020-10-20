package com.example.martin.shelem.activities;

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

import com.example.martin.shelem.fragments.LoginFragment;
import com.example.martin.shelem.fragments.SignupFragment;
import com.example.martin.shelem.handlers.UserDetails;
import com.example.martin.shelem.R;
import com.example.martin.shelem.interfaces.IActivityResponse;
import com.example.martin.shelem.interfaces.IFragmentResponse;
import com.example.martin.shelem.utils.UnitHandler;

public class LoginSignupActivity extends AppCompatActivity implements IFragmentResponse {

    RelativeLayout mainActivity;
    FrameLayout signupFragmentContainer, loginFragmentContainer, otherFragmentContainer;
    IActivityResponse iActivityResponseToLogin;
    LoginFragment loginFragment;
    SignupFragment signupFragment;

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

        loginFragment = new LoginFragment();
        signupFragment = new SignupFragment();

        iActivityResponseToLogin = loginFragment;



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

        getSupportFragmentManager().beginTransaction().add(R.id.container_login_fragment, loginFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container_signup_fragment, signupFragment).commit();
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
                animateBottomMargin(loginFragmentContainer, (int) (- unitHandler.baseScreenHeight + signupContainerClosedHeight + loginContainerOpenedHeight), 200, null);
                animateBottomMargin(signupFragmentContainer, - unitHandler.baseScreenHeight + (int) (signupContainerClosedHeight), 150, null);
                isLoginOpen = true;
                isSignupOpen = false;
            } else {
                iActivityResponseToLogin.onActivityResponse("clear focus");
                animateBottomMargin(loginFragmentContainer, - (unitHandler.baseScreenHeight - (int) (2 * loginContainerClosedHeight)), 200, null);
                animateBottomMargin(signupFragmentContainer, - (unitHandler.baseScreenHeight - (int) (signupContainerClosedHeight)), 150, null);
                isLoginOpen = false;
            }
        });



        signupFragmentContainer.setOnClickListener(v -> {
            hideKeyboard();

            if (!isSignupOpen) {
                iActivityResponseToLogin.onActivityResponse("clear focus");
                animateBottomMargin(signupFragmentContainer, (int) (- unitHandler.baseScreenHeight + signupContainerOpenedHeight), 150, null);
                animateBottomMargin(loginFragmentContainer, (int) (- unitHandler.baseScreenHeight + signupContainerOpenedHeight + loginContainerClosedHeight), 200, null);
                isLoginOpen = false;
                isSignupOpen = true;
            } else {
                animateBottomMargin(signupFragmentContainer, - (unitHandler.baseScreenHeight - (int) (signupContainerClosedHeight)), 150, null);
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




    @Override
    public void onBackPressed() {
        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive() && getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            getCurrentFocus().clearFocus();
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public void onFragmentResponse(String... args) {
        if (args[0].equals("true")) {
            animateBottomMargin(signupFragmentContainer, (int) (-unitHandler.baseScreenHeight + signupContainerOpenedHeight + loginContainerClosedHeight), 200, null);
            signupFragmentContainer.setClickable(false);
            loginFragmentContainer.setClickable(false);
        } else {
            animateBottomMargin(signupFragmentContainer, (int) (-unitHandler.baseScreenHeight + signupContainerOpenedHeight), 200, null);
            signupFragmentContainer.setClickable(true);
            loginFragmentContainer.setClickable(true);
        }
    }
}
