package com.example.martin.shelem.customViews;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.martin.shelem.R;
import com.example.martin.shelem.utils.UnitHandler;

public class CurvedLine extends View {

    private float startAngle, sweapAngle;
    UnitHandler unitHandler;

    public CurvedLine(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        unitHandler = new UnitHandler((Activity) context);
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public void setSweapAngle(float sweapAngle) {
        this.sweapAngle = sweapAngle;
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        float diameter = getWidth() - unitHandler.getPixels(100);
        float radius = diameter / 2;

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.light_orange));
        paint.setStrokeWidth(12);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);

        float center_x, center_y;
        final RectF oval = new RectF();

        center_x = diameter / 2 + unitHandler.getPixels(50);
        center_y = diameter / 2 + unitHandler.getPixels(80);

        oval.set(center_x - radius, center_y - radius, center_x + radius, center_y + radius);
        canvas.drawArc(oval, startAngle, sweapAngle, false, paint);
    }



}
