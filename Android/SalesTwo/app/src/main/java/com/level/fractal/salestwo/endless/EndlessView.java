package com.level.fractal.salestwo.endless;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;

import com.level.fractal.salestwo.helpers.MathUtils;
import com.level.fractal.salestwo.helpers.ResourcesLoader;
import com.level.fractal.salestwo.variants.VariantsActivity;

public class EndlessView extends View {
    private GestureDetector panDetector;
    private ScaleGestureDetector scaleDetector;

    private Drawable[] smallImages;

    private int viewWidth;
    private int viewHeight;

    private int padding = 8;

    private float panDx = padding;
    private float panDy = padding;
    private float scaleFactor = 1;

    private boolean hasIntent;

    public EndlessView(Context context, AttributeSet attrs) {
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
        int minLeft, minTop, maxRight, maxBottom;
        minLeft = Integer.MAX_VALUE;
        minTop = Integer.MAX_VALUE;
        maxRight = Integer.MIN_VALUE;
        maxBottom = Integer.MIN_VALUE;

        int sqrt = (int)(Math.sqrt(smallImages.length));

        for (int i = 0; i < sqrt; i++)
        {
            for (int j = 0; j < sqrt; j++)
            {
                int size = 100;
                int interval = size * sqrt;

                left = size * j;
                top = size * i;
                int offsetX = MathUtils.ClosestIntervalStepToPoint(interval, left - (-(int)panDx + viewWidth / 2));
                int offsetY = MathUtils.ClosestIntervalStepToPoint(interval, top - (-(int)panDy + viewHeight / 2));
                left -= offsetX;
                top -= offsetY;
                right = left + size;
                bottom = top + size;

                if (minLeft > left) minLeft = left;
                if (minTop > top) minTop = top;
                if (maxRight < right) maxRight = right;
                if (maxBottom < bottom) maxBottom = bottom;

                Drawable d = smallImages[i * sqrt + j];
                d.setBounds(left, top, right, bottom);
                d.draw(canvas);
            }
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
        float minScaleFactor = 0.9f;
        scaleFactor = Math.max(minScaleFactor, scaleFactor);
        float maxScaleFactor = 3f;
        if (scaleFactor > maxScaleFactor) {
            if (!hasIntent) {
                hasIntent = true;
                Intent myIntent = new Intent(getContext(), VariantsActivity.class);
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
        panDetector = new GestureDetector(EndlessView.this.getContext(), new EndlessView.GestureListener());
        scaleDetector = new ScaleGestureDetector(EndlessView.this.getContext(), new EndlessView.ScaleListener());

        smallImages = new Drawable[1000];
        for (int i = 0; i < smallImages.length; i++)
        {
            int count = ResourcesLoader.allImages.length;
            int resID = ResourcesLoader.allImages[i % count];
            smallImages[i] = ResourcesCompat.getDrawable(getResources(), resID, null);
        }

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
                }
            });
        }
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
