package com.example.martin.shelem.customViews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.martin.shelem.R;

public class CircularProgressBar extends View {
    private int viewWidth;
    private int viewHeight;

    private final float startAngle = 90;
    private float sweepAngle = 0;
    private float maxSweepAngle = 360;
    private int strokeWidth = 12;
    private int maxProgress = 100;
    private boolean roundedCorners = true;
    private int progressColor = getResources().getColor(R.color.MediumSkyBlue);
    private int progressContainerColor = getResources().getColor(R.color.white);
    private final Paint paint, paint1;

    public CircularProgressBar(Context context) {
        this(context, null);
    }

    public CircularProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initMeasurments();
        drawOutlineArc(canvas);

    }

    private void initMeasurments() {
        viewWidth = getWidth();
        viewHeight = getHeight();
    }

    private void drawOutlineArc(Canvas canvas) {

        final int diameter = Math.min(viewWidth, viewHeight);
        final float pad = (float) (strokeWidth / 2.0);
        final RectF outerOval = new RectF(pad, pad, diameter - pad, diameter - pad);

        paint.setColor(progressColor);
        paint.setStrokeWidth(strokeWidth);
        paint.setAntiAlias(true);
        paint.setStrokeCap(roundedCorners ? Paint.Cap.ROUND : Paint.Cap.BUTT);
        paint.setStyle(Paint.Style.STROKE);

        paint1.setColor(progressContainerColor);
        paint1.setStrokeWidth(strokeWidth);
        paint1.setAntiAlias(true);
        paint1.setStrokeCap(Paint.Cap.BUTT);
        paint1.setStyle(Paint.Style.STROKE);

        canvas.drawArc(outerOval, startAngle, 360, false, paint1);
        canvas.drawArc(outerOval, startAngle, sweepAngle, false, paint);
    }

    private float calcSweepAngleFromProgress(int progress) {
        return (maxSweepAngle / maxProgress) * progress;
    }

    private int calcProgressFromSweepAngle(float sweepAngle) {
        return (int) ((sweepAngle * maxProgress) / maxSweepAngle);
    }

    public void setProgress(int progress, int duration) {
        ValueAnimator animator = ValueAnimator.ofFloat(sweepAngle, calcSweepAngleFromProgress(progress));
        animator.setDuration(duration);
        animator.addUpdateListener(valueAnimator -> {
            sweepAngle = (float) valueAnimator.getAnimatedValue();
            invalidate();
        });
        animator.start();
    }

}
