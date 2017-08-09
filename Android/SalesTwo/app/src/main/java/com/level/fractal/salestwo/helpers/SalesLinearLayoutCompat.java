package com.level.fractal.salestwo.helpers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
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
        return super.onTouchEvent(e);
    }
}
