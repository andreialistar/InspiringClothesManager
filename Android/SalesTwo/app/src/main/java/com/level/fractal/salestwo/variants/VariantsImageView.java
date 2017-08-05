package com.level.fractal.salestwo.variants;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
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
    private float minScaleFactor = 0.6f;
    private float maxScaleFactor = 4f;

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

    public void UpdateFling(float dx, float dy)
    {
//        if (Math.abs(dy) > 2 * Math.abs(dx))
//        {
//            int neighborIndex = bigImagePrefixIndex - (int)Math.signum(dy);
//            neighborIndex = Math.max(0, neighborIndex);
//            neighborIndex = Math.min(FirstPageActivity.articlePrefixes.length - 1, neighborIndex);
//            Log.d("ChosenIndex", String.valueOf(neighborIndex));
//            FirstPageActivity.OpenBigImageActivity(null, FirstPageActivity.articlePrefixes[neighborIndex], FirstPageActivity.articleNumSamples[neighborIndex], null);
//        }
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
        if (scaleFactor < minScaleFactor) {
            if (!hasIntent) {
                hasIntent = true;
                Intent myIntent = new Intent(getContext(), EndlessActivity.class);
                getContext().startActivity(myIntent);
            }
        }
        else {
            float change = -size * scaleFactor * (1 - scaleFactor / beforeScale) * 0.5f;
            if (change != 0) {
//                UpdateScroll(change, change);
            }
            else {
                invalidate();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean resultPan = panDetector.onTouchEvent(event);
        boolean resultScale = scaleDetector.onTouchEvent(event);

        boolean result = resultPan && resultScale;

        if (!result) {
            return true;
        }
        return result;
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

                    Log.d("Kiki", String.format("%d-%d", viewWidth, viewHeight));

                    size = Math.min(viewWidth, viewHeight) - 2 * padding;
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
            hasIntent = false;

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
