package com.level.fractal.salestwo.messy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;

import com.level.fractal.salestwo.helpers.MathUtils;

public class BigGridView extends View {


    private GestureDetector mDetector;
    private ScaleGestureDetector mScaleDetector;


    private float scrollDx;
    private float scrollDy;
    private float scrollMinX;
    private float scrollMinY;
    private float scrollMaxX;
    private float scrollMaxY;
    private float GetScaledScrollMaxX()
    {
        return scrollMaxX * scaleFactor;
    };
    private float GetScaledScrollMaxY()
    {
        return scrollMaxY * scaleFactor;
    };
    private int size = 100;
    private int extension = 16;
    private int viewWidth;
    private int viewHeight;

    private float scaleFactor = 1;
    private float minScaleFactor = 0.25f;
    private float maxScaleFactor = 4f;

    private Drawable[] smallImages;
    private String[] imagesPrefixes;
    private int[] imagesOffsets;

    public BigGridView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);

        Log.d("BigGridDrawUpdate", String.format("dx: %f, dy: %f", scrollDx, scrollDy));

        int left, top, right, bottom;
        int sqrt = (int)(Math.sqrt(smallImages.length));

        for (int i = 0; i < sqrt; i++)
        {
            for (int j = 0; j < sqrt; j++)
            {
                left = (int)scrollDx + size * j;
                top = (int)scrollDy + size * i;
                right = left + size;
                bottom = top + size;
                Drawable d = smallImages[i * sqrt + j];
                d.setBounds(left, top, right, bottom);
                d.draw(canvas);
            }
        }

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Let the GestureDetector interpret this event
        boolean resultTouch = mDetector.onTouchEvent(event);
        boolean resultScale = mScaleDetector.onTouchEvent(event);
        boolean result = resultTouch && resultScale;

        if (!result) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // User is done scrolling, it's now safe to do things like autocenter

                result = true;
            }
        }
        return result;
    }

    public void UpdateScroll(float dx, float dy)
    {
        scrollDx -= dx;
        scrollDy -= dy;
        scrollDx = Math.max(GetScaledScrollMaxX(), Math.min(scrollMinX, scrollDx));
        scrollDy = Math.max(GetScaledScrollMaxY(), Math.min(scrollMinY, scrollDy));
        Log.d("BigGridScrollUpdate", String.format("dx: %f, dy: %f", scrollDx, scrollDy));

        invalidate();
    }

    public void UpdateTouchedImage(int x, int y)
    {
        int ux = (int)(1.0f * (x - scrollDx) / scaleFactor);
        int uy = (int)(1.0f * (y - scrollDy) / scaleFactor);
        int ox = ux / 100;
        int oy = uy / 100;
        int sqrt = (int)(Math.sqrt(smallImages.length));
        int imageIndex = oy * sqrt + ox;
        String articlePrefix = imagesPrefixes[imageIndex];
        int pageNumber = imagesOffsets[imageIndex];
        Log.d("BigGridTouchUpdate", String.format("dx: %d, dy: %d, sx: %d, sy: %d, px: %d, py: %d, sqrt: %d, idx: %d, article: %s, num: %d",
                x, y, ux, uy, ox, oy, sqrt, imageIndex, articlePrefix, pageNumber));


        FirstPageActivity.OpenBigImageActivity(null, articlePrefix, pageNumber, null);
    }

    public void UpdateScale(float ds)
    {
        float beforeScale = scaleFactor;
        scaleFactor *= ds;
        scaleFactor = Math.max(minScaleFactor, Math.min(maxScaleFactor, scaleFactor));
        Log.d("BigGridScaleUpdate", String.format("ds: %f", scaleFactor));

        float change = -size * scaleFactor * (1 - scaleFactor / beforeScale) * 0.5f;
        if (change != 0) {
            UpdateScroll(change, change);
        }
        else {
            invalidate();
        }
    }

    private void init() {
        mDetector = new GestureDetector(BigGridView.this.getContext(), new GestureListener());
        mScaleDetector = new ScaleGestureDetector(BigGridView.this.getContext(), new ScaleListener());

        int numImages = 0;
        for (int i = 0; i < FirstPageActivity.articlePrefixes.length; i++) {
            numImages += FirstPageActivity.articleNumSamples[i];
        }
        numImages = MathUtils.IncrementToSquare(numImages);
        smallImages = new Drawable[numImages * extension];
        imagesPrefixes = new String[smallImages.length];
        imagesOffsets = new int[smallImages.length];
        for (int i = 0; i < smallImages.length; i++)
        {
            int radix = i % numImages;
            int counter = 0;
            String foundPrefix = FirstPageActivity.articlePrefixes[0];
            int foundBatchIndex = 1;
            for (int j = 0; j < FirstPageActivity.articlePrefixes.length; j++) {
                int batchSize = FirstPageActivity.articleNumSamples[j];
                if (radix < counter + batchSize)
                {
                    foundPrefix = FirstPageActivity.articlePrefixes[j];
                    foundBatchIndex = radix - counter + 1;
                    break;
                }
                counter += batchSize;
            }
            int resID = getResources().getIdentifier(foundPrefix + foundBatchIndex, "drawable", "com.level.fractal.salestwo");
            Log.d("FoundId", foundPrefix + " - " + foundBatchIndex + " - " + String.valueOf(resID));
            smallImages[i] = ResourcesCompat.getDrawable(getResources(), resID, null);
            imagesPrefixes[i] = foundPrefix;
            imagesOffsets[i] = foundBatchIndex;
//            smallImages[i].o
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

                    int sqrt = (int)(Math.sqrt(smallImages.length));
                    scrollMinX = 0;
                    scrollMinY = 0;
                    scrollMaxX = -size * sqrt + viewWidth;
                    scrollMaxY = -size * sqrt + viewHeight;
                }
            });
        }

    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("BigGridScroll", String.format("e1: %d, e2: %d, dx: %f, dy: %f", e1.getAction(), e2.getAction(), distanceX, distanceY));
            UpdateScroll(distanceX, distanceY);

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("BigGridFling", String.format("e1: %d, e2: %d, dx: %f, dy: %f", e1.getAction(), e2.getAction(), velocityX, velocityY));

            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("BigGridDown", String.format("e: %d", e.getAction()));

            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("BigGridTap", String.format("e: %d", e.getAction()));
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
