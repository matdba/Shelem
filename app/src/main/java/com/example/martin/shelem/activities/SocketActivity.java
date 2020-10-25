package com.example.martin.shelem.activities;


import android.os.Bundle;

import com.example.martin.shelem.handlers.SocketHandler2;
import com.example.martin.shelem.handlers.SocketService;
import com.example.martin.shelem.handlers.UserDetails;
import com.example.martin.shelem.instances.User;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SocketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SocketService.init(new UserDetails(this).getUserID());

    }
}
