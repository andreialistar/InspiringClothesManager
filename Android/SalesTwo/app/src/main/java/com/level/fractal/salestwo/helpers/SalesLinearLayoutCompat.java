package com.level.fractal.salestwo.helpers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class SalesLinearLayoutCompat extends LinearLayoutCompat {
    public SalesLinearLayoutCompat(Context context) {
        super(context);
    }

    public SalesLinearLayoutCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SalesLinearLayoutCompat(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        Log.d("TouchingM", String.valueOf(y));

        return super.onTouchEvent(e);
    }

//    @Override
//    public boolean onInterceptTouchEvent (MotionEvent e) {
//        float x = e.getX();
//        float y = e.getY();
//
//        Log.d("TouchingI", String.valueOf(y));
//
//        return false;
//    }
}
