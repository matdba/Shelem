package com.example.martin.shelem.utils;

import android.view.animation.Interpolator;

public class SpringInterpolator implements Interpolator {

    private float factor = 0.55f;

    public SpringInterpolator() {}
    @Override

    public float getInterpolation(float input) {
        return (float) (Math.pow(2, (-10 * input)) * Math.sin(((2 * Math.PI) * (input - (factor / 4))) / factor) + 1);
    }
}
