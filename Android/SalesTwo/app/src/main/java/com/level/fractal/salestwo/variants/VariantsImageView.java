package com.level.fractal.salestwo.variants;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;

import com.level.fractal.salestwo.endless.EndlessActivity;

public class VariantsImageView extends View {
    public Drawable image;

    private GestureDetector panDetector;
    private ScaleGestureDetector scaleDetector;

    private int viewWidth;
    private int viewHeight;

    private int size = 200;
    private int padding = 0;

    private float panDx = padding;
    private float panDy = padding;
    private float scaleFactor = 1;

    private boolean hasIntent;

    public VariantsImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(scaleFactor, scaleFactor, viewWidth / 2, viewHeight / 2);
        canvas.translate(panDx, panDy);

        int left, top, right, bottom;

        left = 0;
        top = 0;
        right = left + size;
        bottom = top + size;

        if (image != null)
        {
            image.setBounds(left, top, right, bottom);
            image.draw(canvas);
        }

        canvas.restore();
    }
    public void UpdateScroll(float dx, float dy)
    {
        panDx -= dx;
        panDy -= dy;

        invalidate();
    }

    public void UpdateScale(float ds)
    {
        scaleFactor *= ds;
        float maxScaleFactor = 4f;
        scaleFactor = Math.min(maxScaleFactor, scaleFactor);
        float minScaleFactor = 0.6f;
        if (scaleFactor < minScaleFactor) {
            if (!hasIntent) {
                hasIntent = true;
                Intent myIntent = new Intent(getContext(), EndlessActivity.class);
                getContext().startActivity(myIntent);
            }
        }
        else {
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        panDetector.onTouchEvent(event);
        scaleDetector.onTouchEvent(event);

        return true;
    }

    private void init() {
        panDetector = new GestureDetector(VariantsImageView.this.getContext(), new VariantsImageView.GestureListener());
        scaleDetector = new ScaleGestureDetector(VariantsImageView.this.getContext(), new VariantsImageView.ScaleListener());

        final View view = this;
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    viewWidth = view.getWidth();
                    viewHeight = view.getHeight();

                    size = Math.min(viewWidth, viewHeight) - 2 * padding;
                }
            });
        }

        invalidate();
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            UpdateScroll(distanceX, distanceY);

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            hasIntent = false;

            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            UpdateScale(detector.getScaleFactor());

            return true;
        }
    }
}
