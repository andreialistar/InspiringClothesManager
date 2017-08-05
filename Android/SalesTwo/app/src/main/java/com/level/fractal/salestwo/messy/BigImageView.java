package com.level.fractal.salestwo.messy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;

import com.level.fractal.salestwo.R;

public class BigImageView extends View {

    public Drawable bigImage;
    public int bigImagePrefixIndex;

    private GestureDetector panDetector;
    private ScaleGestureDetector scaleDetector;

    private int size = 200;
    private int padding = 8;

    private float panDx = padding;
    private float panDy = padding;

    private float scaleFactor = 1;
    private float minScaleFactor = 0.5f;
    private float maxScaleFactor = 4f;

    public BigImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("TrendingLog", "BigImageView onDraw " + size + ", " + bigImage.getBounds());


        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);
        canvas.translate(panDx, panDy);

        int left, top, right, bottom;

        left = 0;
        top = 0;
//        left = (int)panDx;
//        top = (int)panDy;
        right = left + size;
        bottom = top + size;

        if (bigImage != null)
        {
            bigImage.setBounds(left, top, right, bottom);
            bigImage.draw(canvas);
        }

        canvas.restore();
    }

    public void UpdateScroll(float dx, float dy)
    {
        panDx -= dx;
        panDy -= dy;
////        scrollDx = Math.max(GetScaledScrollMaxX(), Math.min(scrollMinX, scrollDx));
////        scrollDy = Math.max(GetScaledScrollMaxY(), Math.min(scrollMinY, scrollDy));
//        Log.d("Scroll update", String.format("dx: %f, dy: %f", scrollDx, scrollDy));
//
        invalidate();
    }

    public void UpdateFling(float dx, float dy)
    {
        if (Math.abs(dy) > 2 * Math.abs(dx))
        {
            int neighborIndex = bigImagePrefixIndex - (int)Math.signum(dy);
            neighborIndex = Math.max(0, neighborIndex);
            neighborIndex = Math.min(FirstPageActivity.articlePrefixes.length - 1, neighborIndex);
            Log.d("ChosenIndex", String.valueOf(neighborIndex));
            FirstPageActivity.OpenBigImageActivity(null, FirstPageActivity.articlePrefixes[neighborIndex], FirstPageActivity.articleNumSamples[neighborIndex], null);
        }
    }

    public void UpdateTouchedImage(int x, int y)
    {
//        int ux = (int)(1.0f * (x - scrollDx) / scaleFactor);
//        int uy = (int)(1.0f * (y - scrollDy) / scaleFactor);
//        int ox = ux / 100;
//        int oy = uy / 100;
//        Log.d("Touch update", String.format("dx: %d, dy: %d, sx: %d, sy: %d, px: %d, py: %d", x, y, ux, uy, ox, oy));
    }

    public void UpdateScale(float ds)
    {
        float beforeScale = scaleFactor;
        scaleFactor *= ds;
        scaleFactor = Math.min(maxScaleFactor, scaleFactor);
//        Log.d("Scale update", String.format("ds: %f", scaleFactor));
        if (scaleFactor < minScaleFactor) {
            Intent myIntent = new Intent(getContext(), BigGridActivity.class);
            getContext().startActivity(myIntent);
        }
        else {
            float change = -size * scaleFactor * (1 - scaleFactor / beforeScale) * 0.5f;
            if (change != 0) {
                UpdateScroll(change, change);
            }
            else {
                invalidate();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Let the GestureDetector interpret this event
        Log.d("Got touched", "Detailed view");

        boolean resultPan = panDetector.onTouchEvent(event);
        boolean resultScale = scaleDetector.onTouchEvent(event);

        boolean result = resultPan && resultScale;

        // If the GestureDetector doesn't want this event, do some custom processing.
        // This code just tries to detect when the user is done scrolling by looking
        // for ACTION_UP events.
        if (!result) {
            return true;
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                // User is done scrolling, it's now safe to do things like autocenter
//
//                result = true;
//            }
        }
        return result;
    }

    private void init() {
        Log.d("TrendingLog", "BigImageView init ");

        panDetector = new GestureDetector(BigImageView.this.getContext(), new GestureListener());
        scaleDetector = new ScaleGestureDetector(BigImageView.this.getContext(), new ScaleListener());

        bigImage = ResourcesCompat.getDrawable(getResources(), R.drawable.dress1, null);
        bigImage.setBounds(100, 100, 300, 300);

        final View view = this;
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    size = Math.min(view.getWidth(), view.getHeight()) - 2 * padding;
                    Log.d("SizeBe", String.valueOf(size));
                    Log.d("TrendingLog", "BigImageView init " + size + ", " + bigImage.getBounds());

                }
            });
        }

        invalidate();
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("BigImageScroll", String.format("e1: %d, e2: %d, dx: %f, dy: %f", e1.getAction(), e2.getAction(), distanceX, distanceY));
            UpdateScroll(distanceX, distanceY);

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("BigImageFling", String.format("e1: %d, e2: %d, dx: %f, dy: %f", e1.getAction(), e2.getAction(), velocityX, velocityY));
            UpdateFling(velocityX, velocityY);

            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("BigImageDown", String.format("e: %d", e.getAction()));

            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("BigImageTap", String.format("e: %d", e.getAction()));
            UpdateTouchedImage((int)e.getX(), (int)e.getY());

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
