package com.example.martin.playingcards.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ProfileDrawable {
    Drawable [] objects;

    public ProfileDrawable(Context context){

        objects= new Drawable[100];
//        objects[1]= context.getResources().getDrawable(R.drawable.ic_firtavatar_boy);
//        objects[2]= context.getResources().getDrawable(R.drawable.ic_girl);
//        objects[3]= context.getResources().getDrawable(R.drawable.ic_girl1);
//        objects[4]= context.getResources().getDrawable(R.drawable.ic_girl2);
//        objects[5]= context.getResources().getDrawable(R.drawable.ic_boy);
//        objects[6]= context.getResources().getDrawable(R.drawable.ic_boy1);
//        objects[7]= context.getResources().getDrawable(R.drawable.ic_fag_boy);
//        objects[8]= context.getResources().getDrawable(R.drawable.ic_brithish_girl);
//        objects[9]= context.getResources().getDrawable(R.drawable.ic_japanese_girl);
//        objects[10]= context.getResources().getDrawable(R.drawable.ic_nerd_boy);
    }

    public Drawable getDrawable(int i){ return objects[i];}


}
