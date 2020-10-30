package com.example.martin.shelem.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.shelem.R;
import com.example.martin.shelem.activities.DashboardActivity;
import com.example.martin.shelem.handlers.APIHandler;
import com.example.martin.shelem.handlers.AnimationHandler;
import com.example.martin.shelem.interfaces.IFragmentResponse;
import com.example.martin.shelem.utils.FullScreenModeSwitch;
import com.example.martin.shelem.utils.SpringInterpolator;
import com.example.martin.shelem.utils.UnitHandler;
import com.example.martin.shelem.utils.Validation;

import java.util.Objects;

public class SignupFragment extends Fragment {
    private View view;
    private TextView signupLbl;
    private CardView usernameEtContainer, passwordEtContainer, reenterPasswordEtContainer, emailEtContainer, inviteCodeEtContainer;
    private EditText usernameEt, passwordEt, reenterPasswordEt, emailEt, inviteCodeEt;
    private ImageView usernameEtStatusImg, passwordEtStatusImg, reenterPasswordEtStatusImg, emailEtStatusImg, inviteCodeEtStatusImg;
    private ProgressBar usernameEtProgressbar;
    private TextView usernameEtErrorMessageTxt, passwordEtErrorMessageTxt, reenterPasswordEtErrorMessageTxt, emailEtErrorMessageTxt, inviteCodeEtErrorMessageTxt;
    private ImageView nextBtn, backBtn;
    private RelativeLayout nextBackBtnContainer;
    private TextView signupBtn;

    private AnimationHandler animationHandler;
    private UnitHandler unitHandler;

    private IFragmentResponse iFragmentResponse;

    private int step = 1;
    boolean usernameOk = false, passwordOk = false, reenterPasswordOk = false, emailOk = false, inviteCodeOk = false;
;

    private void init() {

        signupLbl = view.findViewById(R.id.lbl_signup);

        usernameEtContainer = view.findViewById(R.id.container_et_username);
        passwordEtContainer = view.findViewById(R.id.container_et_password);
        reenterPasswordEtContainer = view.findViewById(R.id.container_et_reenter_password);
        emailEtContainer = view.findViewById(R.id.container_et_email);
        inviteCodeEtContainer = view.findViewById(R.id.container_et_invite_code);

        usernameEtProgressbar = view.findViewById(R.id.progressbar_et_username);

        usernameEt = view.findViewById(R.id.et_username);
        passwordEt = view.findViewById(R.id.et_password);
        reenterPasswordEt = view.findViewById(R.id.et_reenter_password);
        emailEt = view.findViewById(R.id.et_email);
        inviteCodeEt = view.findViewById(R.id.et_invite_code);

        usernameEtStatusImg = view.findViewById(R.id.img_status_et_username);
        passwordEtStatusImg = view.findViewById(R.id.img_status_et_password);
        reenterPasswordEtStatusImg = view.findViewById(R.id.img_status_et_reenter_password);
        emailEtStatusImg = view.findViewById(R.id.img_status_et_email);
        inviteCodeEtStatusImg = view.findViewById(R.id.img_status_et_invite_code);

        usernameEtErrorMessageTxt = view.findViewById(R.id.txt_error_message_et_username);
        passwordEtErrorMessageTxt = view.findViewById(R.id.txt_error_message_et_password);
        reenterPasswordEtErrorMessageTxt = view.findViewById(R.id.txt_error_message_et_reenter_password);
        emailEtErrorMessageTxt = view.findViewById(R.id.txt_error_message_et_email);
        inviteCodeEtErrorMessageTxt = view.findViewById(R.id.txt_error_message_et_invide_code);

        nextBackBtnContainer = view.findViewById(R.id.container_btn_next_back);
        nextBtn = view.findViewById(R.id.btn_next);
        backBtn = view.findViewById(R.id.btn_back);
        signupBtn = view.findViewById(R.id.btn_signup);



        unitHandler = new UnitHandler(getActivity());
        animationHandler = new AnimationHandler(getActivity());



        iFragmentResponse = (IFragmentResponse) getActivity();


        unitHandler.setViewsHeightAndWidth(signupLbl, unitHandler.getPixels(200), unitHandler.getPixels(50));
        unitHandler.setViewTopMargin(signupLbl, unitHandler.getPixels(16));

        unitHandler.setViewsHeightAndWidth(usernameEtContainer, unitHandler.screenWidth, unitHandler.getPixels(50));
        unitHandler.setViewTopMargin(usernameEtContainer, unitHandler.getPixels(32));
        unitHandler.setViewLeftMargin(usernameEtContainer, unitHandler.getPixels(24));
        unitHandler.setViewRightMargin(usernameEtContainer, unitHandler.getPixels(24));


        unitHandler.setViewsHeightAndWidth(passwordEtContainer, unitHandler.screenWidth, unitHandler.getPixels(50));
        unitHandler.setViewTopMargin(passwordEtContainer, unitHandler.getPixels(32));
        unitHandler.setViewLeftMargin(passwordEtContainer, unitHandler.screenWidth + unitHandler.getPixels(24));
        unitHandler.setViewRightMargin(passwordEtContainer, - unitHandler.screenWidth - unitHandler.getPixels(24));


        unitHandler.setViewsHeightAndWidth(reenterPasswordEtContainer, unitHandler.screenWidth, unitHandler.getPixels(50));
        unitHandler.setViewTopMargin(reenterPasswordEtContainer, unitHandler.getPixels(24));
        unitHandler.setViewLeftMargin(reenterPasswordEtContainer, unitHandler.screenWidth + unitHandler.getPixels(24));
        unitHandler.setViewRightMargin(reenterPasswordEtContainer, - unitHandler.screenWidth - unitHandler.getPixels(24));


        unitHandler.setViewsHeightAndWidth(emailEtContainer, unitHandler.screenWidth, unitHandler.getPixels(50));
        unitHandler.setViewTopMargin(emailEtContainer, unitHandler.getPixels(32));
        unitHandler.setViewLeftMargin(emailEtContainer, 2 * unitHandler.screenWidth + unitHandler.getPixels(24));
        unitHandler.setViewRightMargin(emailEtContainer, - 2 * unitHandler.screenWidth - unitHandler.getPixels(24));


        unitHandler.setViewsHeightAndWidth(inviteCodeEtContainer, unitHandler.screenWidth, unitHandler.getPixels(50));
        unitHandler.setViewTopMargin(inviteCodeEtContainer, unitHandler.getPixels(32));
        unitHandler.setViewLeftMargin(inviteCodeEtContainer, 3 * unitHandler.screenWidth + unitHandler.getPixels(24));
        unitHandler.setViewRightMargin(inviteCodeEtContainer, - 3 * unitHandler.screenWidth - unitHandler.getPixels(24));


        unitHandler.setViewsHeightAndWidth(signupBtn, unitHandler.screenWidth, unitHandler.getPixels(40));
        unitHandler.setViewTopMargin(signupBtn, unitHandler.getPixels(32));
        unitHandler.setViewLeftMargin(signupBtn, 3 * unitHandler.screenWidth + unitHandler.getPixels(120));
        unitHandler.setViewRightMargin(signupBtn, - 3 * unitHandler.screenWidth - unitHandler.getPixels(120));


        unitHandler.setViewTopMargin(nextBackBtnContainer, unitHandler.getPixels(32));
        unitHandler.setViewsHeightAndWidth(nextBtn, unitHandler.getPixels(60), unitHandler.getPixels(60));
        unitHandler.setViewsHeightAndWidth(backBtn, unitHandler.getPixels(60), unitHandler.getPixels(60));
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);

        init();

        usernameEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                FullScreenModeSwitch.off(Objects.requireNonNull(getActivity()));
                usernameEt.setBackgroundResource(R.drawable.background_et_focus_on_solid_white_corner_8dp);
            } else {
                FullScreenModeSwitch.on(Objects.requireNonNull(getActivity()));
            }
        });

        passwordEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                FullScreenModeSwitch.off(Objects.requireNonNull(getActivity()));
                passwordEt.setBackgroundResource(R.drawable.background_et_focus_on_solid_white_corner_8dp);
            } else {
                FullScreenModeSwitch.on(Objects.requireNonNull(getActivity()));
            }
        });

        reenterPasswordEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                FullScreenModeSwitch.off(Objects.requireNonNull(getActivity()));
                reenterPasswordEt.setBackgroundResource(R.drawable.background_et_focus_on_solid_white_corner_8dp);
            } else {
                FullScreenModeSwitch.on(Objects.requireNonNull(getActivity()));
            }
        });

        emailEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                FullScreenModeSwitch.off(Objects.requireNonNull(getActivity()));
                emailEtStatusImg.setVisibility(View.INVISIBLE);
                emailEt.setBackgroundResource(R.drawable.background_et_focus_on_solid_white_corner_8dp);
                emailEtErrorMessageTxt.setVisibility(View.INVISIBLE);
            } else {
                FullScreenModeSwitch.on(Objects.requireNonNull(getActivity()));
            }
        });

        inviteCodeEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                FullScreenModeSwitch.off(Objects.requireNonNull(getActivity()));
                reenterPasswordEt.setBackgroundResource(R.drawable.background_et_focus_on_solid_white_corner_8dp);
            } else {
                FullScreenModeSwitch.on(Objects.requireNonNull(getActivity()));
            }
        });


        usernameEt.setOnEditorActionListener((v, actionId, event) ->  {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                FullScreenModeSwitch.on(Objects.requireNonNull(getActivity()));
                usernameEt.clearFocus();
            }
            return false;
        });


        passwordEt.setOnEditorActionListener((v, actionId, event) ->  {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                FullScreenModeSwitch.on(Objects.requireNonNull(getActivity()));
                passwordEt.clearFocus();
            }
            return false;
        });

        reenterPasswordEt.setOnEditorActionListener((v, actionId, event) ->  {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                FullScreenModeSwitch.on(Objects.requireNonNull(getActivity()));
                reenterPasswordEt.clearFocus();
            }
            return false;
        });

        emailEt.setOnEditorActionListener((v, actionId, event) ->  {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                FullScreenModeSwitch.on(Objects.requireNonNull(getActivity()));
                emailEt.clearFocus();
            }
            return false;
        });

        inviteCodeEt.setOnEditorActionListener((v, actionId, event) ->  {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                FullScreenModeSwitch.on(Objects.requireNonNull(getActivity()));
                inviteCodeEt.clearFocus();
            }
            return false;
        });






        usernameEt.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usernameEtProgressbar.setVisibility(View.VISIBLE);
                usernameEtStatusImg.setVisibility(View.INVISIBLE);
                usernameEtErrorMessageTxt.setVisibility(View.INVISIBLE);

                if (s.length() < 4) {
                    usernameOk = false;
                    usernameEtProgressbar.setVisibility(View.INVISIBLE);
                    usernameEtStatusImg.setVisibility(View.VISIBLE);
                    usernameEtStatusImg.setImageResource(R.drawable.ic_error);
                    usernameEtErrorMessageTxt.setVisibility(View.VISIBLE);
                    usernameEtErrorMessageTxt.setText("username can't be less than 4");
                } else {
                    APIHandler.checkUsernameAvailibility(s.toString(), response -> {
                        if (response.equals("error")) {
                            usernameOk = false;
                            usernameEtProgressbar.setVisibility(View.INVISIBLE);
                            usernameEtStatusImg.setVisibility(View.VISIBLE);
                            usernameEtStatusImg.setImageResource(R.drawable.ic_error);
                            usernameEtErrorMessageTxt.setVisibility(View.VISIBLE);
                            usernameEtErrorMessageTxt.setText("username isn't available");
                        } else {
                            usernameOk = true;
                            usernameEtStatusImg.setVisibility(View.VISIBLE);
                            usernameEtErrorMessageTxt.setVisibility(View.INVISIBLE);
                            usernameEtStatusImg.setImageResource(R.drawable.ic_check);
                            usernameEtProgressbar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }

            @Override public void afterTextChanged(Editable s) { }
        });



        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEtStatusImg.setVisibility(View.INVISIBLE);
                passwordEtErrorMessageTxt.setVisibility(View.INVISIBLE);

                if (s.length() < 8) {
                    passwordOk = false;
                    passwordEtStatusImg.setVisibility(View.VISIBLE);
                    passwordEtStatusImg.setImageResource(R.drawable.ic_error);
                    passwordEtErrorMessageTxt.setVisibility(View.VISIBLE);
                    passwordEtErrorMessageTxt.setText("password can't be less than 8");

                } else {
                    passwordOk = true;
                    passwordEtStatusImg.setVisibility(View.VISIBLE);
                    passwordEtErrorMessageTxt.setVisibility(View.INVISIBLE);
                    passwordEtStatusImg.setImageResource(R.drawable.ic_check);
                    passwordEtErrorMessageTxt.setVisibility(View.INVISIBLE);
                }

            }

            @Override public void afterTextChanged(Editable s) { }
        });



        reenterPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reenterPasswordEtStatusImg.setVisibility(View.INVISIBLE);
                reenterPasswordEtErrorMessageTxt.setVisibility(View.INVISIBLE);

                if (!passwordOk) {
                    reenterPasswordOk = false;
                    passwordEtStatusImg.setVisibility(View.VISIBLE);
                    passwordEtStatusImg.setImageResource(R.drawable.ic_error);
                    passwordEtErrorMessageTxt.setVisibility(View.VISIBLE);
                    passwordEtErrorMessageTxt.setText("password can't be less than 8");
                    reenterPasswordEtStatusImg.setVisibility(View.VISIBLE);
                    reenterPasswordEtStatusImg.setImageResource(R.drawable.ic_error);
                } else if (!s.toString().equals(passwordEt.getText().toString())) {
                    reenterPasswordOk = false;
                    reenterPasswordEtStatusImg.setVisibility(View.VISIBLE);
                    reenterPasswordEtStatusImg.setImageResource(R.drawable.ic_error);
                    reenterPasswordEtErrorMessageTxt.setVisibility(View.VISIBLE);
                    reenterPasswordEtErrorMessageTxt.setText("passwords are not match");
                } else {
                    reenterPasswordOk = true;
                    reenterPasswordEtErrorMessageTxt.setVisibility(View.INVISIBLE);
                    reenterPasswordEtStatusImg.setVisibility(View.VISIBLE);
                    reenterPasswordEtStatusImg.setImageResource(R.drawable.ic_check);
                    reenterPasswordEtErrorMessageTxt.setVisibility(View.INVISIBLE);
                }
            }

            @Override public void afterTextChanged(Editable s) { }
        });




        signupBtn.setOnClickListener(v -> APIHandler.signUp(usernameEt.getText().toString(), passwordEt.getText().toString(), emailEt.getText().toString(), inviteCodeEt.getText().toString(),
                response -> {
                    if (response.equals("success")) {
                        getActivity().startActivity(new Intent(getActivity(), DashboardActivity.class));
                    } else {
                        Toast.makeText(getActivity(), "You are not connected!", Toast.LENGTH_SHORT).show();
                    }
                }));



        nextBtn.setOnClickListener(v -> {
            switch (step) {
                case 1:

                    if (usernameOk) {
                        step++;
                        usernameEt.clearFocus();
                        iFragmentResponse.onFragmentResponse("true");
                        animationHandler.topMarginAnimation(nextBackBtnContainer, unitHandler.getPixels(106), 400,  new SpringInterpolator());
                        animationHandler.rightMarginAnimation(backBtn, unitHandler.getPixels(76), 1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(nextBtn, unitHandler.getPixels(76), 1000, new SpringInterpolator());
                        animationHandler.rightMarginAnimation(nextBtn, unitHandler.getPixels(4), 1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(backBtn, unitHandler.getPixels(4), 1000, new SpringInterpolator());


                        animationHandler.rightMarginAnimation(usernameEtContainer, unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(usernameEtContainer, - unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(passwordEtContainer, unitHandler.getPixels(24), 1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(passwordEtContainer, unitHandler.getPixels(24), 1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(reenterPasswordEtContainer, unitHandler.getPixels(24), 1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(reenterPasswordEtContainer, unitHandler.getPixels(24), 1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(emailEtContainer, - unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(emailEtContainer, unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(inviteCodeEtContainer, - 2 * unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(inviteCodeEtContainer, 2 * unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(signupBtn, - 2 * unitHandler.screenWidth + unitHandler.getPixels(96), 1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(signupBtn, 2 * unitHandler.screenWidth + unitHandler.getPixels(96), 1000, new SpringInterpolator());
                    } else {
                        usernameEtStatusImg.setVisibility(View.VISIBLE);
                        usernameEtStatusImg.setImageResource(R.drawable.ic_error);
                        usernameEtErrorMessageTxt.setVisibility(View.VISIBLE);
                        usernameEt.setBackgroundResource(R.drawable.background_red_border_6_px);
                        usernameEt.clearFocus();
                    }

                break;
                case 2:

                    if (passwordOk && reenterPasswordOk) {
                        step++;

                        animationHandler.rightMarginAnimation(usernameEtContainer, 2 * unitHandler.screenWidth + unitHandler.getPixels(24),  1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(usernameEtContainer, - 2 * unitHandler.screenWidth - unitHandler.getPixels(24),  1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(passwordEtContainer, unitHandler.screenWidth + unitHandler.getPixels(24),  1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(passwordEtContainer, - unitHandler.screenWidth - unitHandler.getPixels(24),  1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(reenterPasswordEtContainer, unitHandler.screenWidth + unitHandler.getPixels(24),  1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(reenterPasswordEtContainer, - unitHandler.screenWidth - unitHandler.getPixels(24),  1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(emailEtContainer, unitHandler.getPixels(24),  1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(emailEtContainer, unitHandler.getPixels(24),  1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(inviteCodeEtContainer, - unitHandler.screenWidth - unitHandler.getPixels(24),  1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(inviteCodeEtContainer, unitHandler.screenWidth + unitHandler.getPixels(24),  1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(signupBtn, - unitHandler.screenWidth - unitHandler.getPixels(96),  1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(signupBtn, unitHandler.screenWidth + unitHandler.getPixels(96),  1000, new SpringInterpolator());
                    } else if (!passwordOk) {
                        passwordEtStatusImg.setVisibility(View.VISIBLE);
                        passwordEtStatusImg.setImageResource(R.drawable.ic_error);
                        passwordEtErrorMessageTxt.setVisibility(View.VISIBLE);
                        passwordEt.setBackgroundResource(R.drawable.background_red_border_6_px);
                        passwordEt.clearFocus();
                    } else {
                        reenterPasswordEtStatusImg.setVisibility(View.VISIBLE);
                        reenterPasswordEtStatusImg.setImageResource(R.drawable.ic_error);
                        reenterPasswordEtErrorMessageTxt.setVisibility(View.VISIBLE);
                        reenterPasswordEt.setBackgroundResource(R.drawable.background_red_border_6_px);
                        reenterPasswordEt.clearFocus();
                    }

                    break;
                case 3:

                    if (Validation.emailIsValid(emailEt.getText().toString())) {
                        step++;
                        backBtn.bringToFront();
                        animationHandler.leftMarginAnimation(nextBtn, unitHandler.getPixels(32), 1000, new SpringInterpolator());
                        animationHandler.rightMarginAnimation(nextBtn, unitHandler.getPixels(32), 1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(backBtn, unitHandler.getPixels(32), 1000, new SpringInterpolator());
                        animationHandler.rightMarginAnimation(backBtn, unitHandler.getPixels(32), 1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(usernameEtContainer, 3 * unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(usernameEtContainer, -3 * unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(passwordEtContainer, 2 * unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(passwordEtContainer, -2 * unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(reenterPasswordEtContainer, 2 * unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(reenterPasswordEtContainer, -2 * unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(emailEtContainer, unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(emailEtContainer, -unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(inviteCodeEtContainer, unitHandler.getPixels(24), 1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(inviteCodeEtContainer, unitHandler.getPixels(24), 1000, new SpringInterpolator());

                        animationHandler.rightMarginAnimation(signupBtn, unitHandler.getPixels(96), 1000, new SpringInterpolator());
                        animationHandler.leftMarginAnimation(signupBtn, unitHandler.getPixels(96), 1000, new SpringInterpolator());
                    } else {
                        emailEtStatusImg.setVisibility(View.VISIBLE);
                        emailEt.setBackgroundResource(R.drawable.background_red_border_6_px);
                        emailEtErrorMessageTxt.setVisibility(View.VISIBLE);
                    }

                    break;
            }
        });





        backBtn.setOnClickListener(v -> {
            switch (step) {

                case 2:
                    step--;

                    iFragmentResponse.onFragmentResponse("false");
                    animationHandler.topMarginAnimation(nextBackBtnContainer, unitHandler.getPixels(32), 400, new SpringInterpolator());

                    animationHandler.leftMarginAnimation(nextBtn, unitHandler.getPixels(32), 1000, new SpringInterpolator());
                    animationHandler.rightMarginAnimation(nextBtn, unitHandler.getPixels(32), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(backBtn, unitHandler.getPixels(32), 1000, new SpringInterpolator());
                    animationHandler.rightMarginAnimation(backBtn, unitHandler.getPixels(32), 1000, new SpringInterpolator());


                    animationHandler.rightMarginAnimation(usernameEtContainer, unitHandler.getPixels(24), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(usernameEtContainer, unitHandler.getPixels(24), 1000, new SpringInterpolator());

                    animationHandler.rightMarginAnimation(passwordEtContainer, - unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(passwordEtContainer, unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());

                    animationHandler.rightMarginAnimation(reenterPasswordEtContainer, - unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(reenterPasswordEtContainer, unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());

                    animationHandler.rightMarginAnimation(emailEtContainer, - 2 * unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(emailEtContainer,2 * unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());

                    animationHandler.rightMarginAnimation(inviteCodeEtContainer, - 3 * unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(inviteCodeEtContainer,  3 * unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());

                    animationHandler.rightMarginAnimation(signupBtn, - 3 * unitHandler.screenWidth - unitHandler.getPixels(96), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(signupBtn,  3 * unitHandler.screenWidth + unitHandler.getPixels(96), 1000, new SpringInterpolator());

                break;
                case 3:
                    step--;

                    animationHandler.rightMarginAnimation(usernameEtContainer, unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(usernameEtContainer, - unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());

                    animationHandler.rightMarginAnimation(passwordEtContainer, unitHandler.getPixels(24), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(passwordEtContainer, unitHandler.getPixels(24), 1000, new SpringInterpolator());

                    animationHandler.rightMarginAnimation(reenterPasswordEtContainer, unitHandler.getPixels(24), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(reenterPasswordEtContainer, unitHandler.getPixels(24), 1000, new SpringInterpolator());

                    animationHandler.rightMarginAnimation(emailEtContainer, - unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(emailEtContainer, unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());

                    animationHandler.rightMarginAnimation(inviteCodeEtContainer, - 2 * unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(inviteCodeEtContainer, 2 * unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());

                    animationHandler.rightMarginAnimation(signupBtn, - 2 * unitHandler.screenWidth - unitHandler.getPixels(96), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(signupBtn, 2 * unitHandler.screenWidth + unitHandler.getPixels(96), 1000, new SpringInterpolator());

                break;
                case 4:
                    step--;

                    nextBtn.bringToFront();
                    animationHandler.rightMarginAnimation(backBtn, unitHandler.getPixels(76), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(nextBtn, unitHandler.getPixels(76), 1000, new SpringInterpolator());
                    animationHandler.rightMarginAnimation(nextBtn, unitHandler.getPixels(4), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(backBtn, unitHandler.getPixels(4), 1000, new SpringInterpolator());

                    animationHandler.rightMarginAnimation(usernameEtContainer, 2 * unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(usernameEtContainer, - 2 * unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());

                    animationHandler.rightMarginAnimation(passwordEtContainer, unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(passwordEtContainer, - unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());

                    
                    animationHandler.rightMarginAnimation(reenterPasswordEtContainer, unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(reenterPasswordEtContainer, - unitHandler.screenWidth -  unitHandler.getPixels(24), 1000, new SpringInterpolator());

                    animationHandler.rightMarginAnimation(emailEtContainer, unitHandler.getPixels(24), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(emailEtContainer, unitHandler.getPixels(24), 1000, new SpringInterpolator());

                    animationHandler.rightMarginAnimation(inviteCodeEtContainer, - unitHandler.screenWidth - unitHandler.getPixels(24), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(inviteCodeEtContainer, unitHandler.screenWidth + unitHandler.getPixels(24), 1000, new SpringInterpolator());

                    animationHandler.rightMarginAnimation(signupBtn, - unitHandler.screenWidth - unitHandler.getPixels(96), 1000, new SpringInterpolator());
                    animationHandler.leftMarginAnimation(signupBtn, unitHandler.screenWidth + unitHandler.getPixels(96), 1000, new SpringInterpolator());

                break;
            }
        });

        return view;
    }

}
