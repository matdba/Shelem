package com.example.martin.playingcards.activities;


import android.os.Bundle;

import com.example.martin.playingcards.handlers.SocketService;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SocketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SocketService.init();
    }
}
