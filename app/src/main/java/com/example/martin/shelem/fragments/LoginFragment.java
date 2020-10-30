package com.example.martin.shelem.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.shelem.R;
import com.example.martin.shelem.activities.DashboardActivity;
import com.example.martin.shelem.handlers.APIHandler;
import com.example.martin.shelem.interfaces.IActivityResponse;
import com.example.martin.shelem.utils.FullScreenModeSwitch;
import com.example.martin.shelem.utils.UnitHandler;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import carbon.widget.RelativeLayout;

public class LoginFragment extends Fragment implements IActivityResponse {
    private View view;
    private CardView usernameEtContainer, passwordEtContainer;
    private EditText usernameEt, passwordEt;
    private ImageView usernameEtErrorImg, passwordEtErrorImg;
    private TextView forgetPasswordBtn;
    private TextView errorMessageTxt;
    private RelativeLayout loginBtn;
    private TextView loginTxt;
    private TextView loginLbl;
    private SpinKitView loginProgressbar;


    private UnitHandler unitHandler;

    private final String usernamePattern = "^[a-zA-Z0-9][a-zA-Z0-9_.]*";


    void init() {

        loginLbl = view.findViewById(R.id.lbl_login);
        usernameEtContainer = view.findViewById(R.id.container_et_username);
        passwordEtContainer = view.findViewById(R.id.container_et_password);
        forgetPasswordBtn = view.findViewById(R.id.btn_forget_password);
        usernameEt = view.findViewById(R.id.et_username);
        passwordEt = view.findViewById(R.id.et_password);
        errorMessageTxt = view.findViewById(R.id.txt_error_message);
        loginBtn = view.findViewById(R.id.btn_login);
        loginTxt = view.findViewById(R.id.txt_login);
        loginProgressbar = view.findViewById(R.id.progress_login);

        usernameEtErrorImg = view.findViewById(R.id.img_error_et_username);
        passwordEtErrorImg = view.findViewById(R.id.img_error_et_password);


        unitHandler = new UnitHandler(getActivity());

        unitHandler.setViewsHeightAndWidth(loginLbl, unitHandler.getPixels(200), unitHandler.getPixels(50));
        unitHandler.setViewTopMargin(loginLbl, unitHandler.getPixels(16));

        unitHandler.setViewsHeightAndWidth(usernameEtContainer, unitHandler.screenWidth, unitHandler.getPixels(50));
        unitHandler.setViewTopMargin(usernameEtContainer, unitHandler.getPixels(32));
        unitHandler.setViewLeftMargin(usernameEtContainer, unitHandler.getPixels(24));
        unitHandler.setViewRightMargin(usernameEtContainer, unitHandler.getPixels(24));

        unitHandler.setViewsHeightAndWidth(passwordEtContainer, unitHandler.screenWidth, unitHandler.getPixels(50));
        unitHandler.setViewTopMargin(passwordEtContainer, unitHandler.getPixels(24));
        unitHandler.setViewLeftMargin(passwordEtContainer, unitHandler.getPixels(24));
        unitHandler.setViewRightMargin(passwordEtContainer, unitHandler.getPixels(24));

        unitHandler.setViewsHeightAndWidth(forgetPasswordBtn, unitHandler.getPixels(220), unitHandler.getPixels(32));
        unitHandler.setViewTopMargin(forgetPasswordBtn, unitHandler.getPixels(16));

        unitHandler.setViewsHeightAndWidth(loginBtn, unitHandler.getPixels(140), unitHandler.getPixels(40));
        unitHandler.setViewTopMargin(loginBtn, unitHandler.getPixels(32));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        init();


        forgetPasswordBtn.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_other_fragment, new ForgetPasswordFragment()).commit());


        loginBtn.setOnClickListener(v -> {
            if (usernameEt.getText().toString().length() < 4 && passwordEt.getText().toString().length() < 8) {
                showErrorEditText(usernameEt, usernameEtErrorImg, "4");
                showErrorEditText(passwordEt, passwordEtErrorImg, "8");
            } else if (usernameEt.getText().toString().length() < 4) {
                showErrorEditText(usernameEt, usernameEtErrorImg, "4");
            } else if (passwordEt.getText().toString().length() < 8) {
                showErrorEditText(passwordEt, passwordEtErrorImg, "8");
            } else {
                usernameEt.clearFocus();
                passwordEt.clearFocus();
                errorMessageTxt.setVisibility(View.INVISIBLE);
                loginTxt.setVisibility(View.INVISIBLE);
                loginProgressbar.setVisibility(View.VISIBLE);
                APIHandler.login(usernameEt.getText().toString(), passwordEt.getText().toString(), response -> {
                    loginTxt.setVisibility(View.VISIBLE);
                    loginProgressbar.setVisibility(View.INVISIBLE);
                    if (response.equals("success")) {
                        Objects.requireNonNull(getActivity()).startActivity(new Intent(getContext(), DashboardActivity.class));
                        getActivity().finish();
                    } else if (response.equals("error")) {
                        errorMessageTxt.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getActivity(), "You are not connected!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




        usernameEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                FullScreenModeSwitch.off(Objects.requireNonNull(getActivity()));
                errorMessageTxt.setVisibility(View.INVISIBLE);
                usernameEt.setHint("username");
                usernameEt.setHintTextColor(getActivity().getResources().getColor(R.color.light_grey));
                usernameEt.setBackgroundResource(R.drawable.background_et_focus_on_solid_blue_corner_8dp);
                usernameEtErrorImg.setVisibility(View.INVISIBLE);
            } else {
                FullScreenModeSwitch.on(Objects.requireNonNull(getActivity()));
            }
        });



        passwordEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                FullScreenModeSwitch.off(Objects.requireNonNull(getActivity()));
                errorMessageTxt.setVisibility(View.INVISIBLE);
                passwordEt.setHint("password");
                passwordEt.setHintTextColor(getActivity().getResources().getColor(R.color.light_grey));
                passwordEt.setBackgroundResource(R.drawable.background_et_focus_on_solid_blue_corner_8dp);
                passwordEtErrorImg.setVisibility(View.INVISIBLE);
            } else {
                FullScreenModeSwitch.on(Objects.requireNonNull(getActivity()));
            }
        });



        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    passwordEt.setSelection(passwordEt.getText().toString().length());
                    passwordEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                    passwordEt.setTypeface(ResourcesCompat.getFont(getContext(), R.font.baloobhai_regular));
                }
            }
        });


        passwordEt.setOnEditorActionListener((v, actionId, event) ->  {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                FullScreenModeSwitch.on(Objects.requireNonNull(getActivity()));
                passwordEt.clearFocus();
            }
            return false;
        });

        return view;
    }



    private void showErrorEditText(EditText et, ImageView img, String lenght) {
        et.setText("");

        if (lenght.equals("4"))
            et.setHint("username can't be less than " + lenght);
        else if (lenght.equals("8"))
            et.setHint("password can't be less than " + lenght);

        et.setHintTextColor(getActivity().getResources().getColor(R.color.orange));
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        et.setTypeface(ResourcesCompat.getFont(getContext(), R.font.baloobhai_regular));
        et.clearFocus();
        et.setBackgroundResource(R.drawable.background_red_border_6_px);

        img.setVisibility(View.VISIBLE);
    }




    @Override
    public void onActivityResponse(String... args) {
        if (args[0].equals("clear focus")) {
            usernameEt.clearFocus();
            passwordEt.clearFocus();
            errorMessageTxt.setVisibility(View.INVISIBLE);
        }
    }
}
