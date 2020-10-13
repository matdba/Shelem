package com.example.martin.playingcards.customViews;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.martin.playingcards.R;

public class ImageViewCustom extends RelativeLayout {

    public ImageView up,down;

    public ImageViewCustom(Context context) {
        super(context);
        init(context);
    }

    public ImageViewCustom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_imageview,this,true);
        up = view.findViewById(R.id.img_card_up);
        down = view.findViewById(R.id.img_card_down);
    }


    public final OnTouchListener mOnTouchListener = (v, event) -> false;
}
