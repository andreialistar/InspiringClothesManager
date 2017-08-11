package com.level.fractal.salestwo.variants;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.level.fractal.salestwo.R;

public class VariantsActivity extends AppCompatActivity implements
        GestureDetector.OnGestureListener{
    private static Context currentContext;
    private int numPages;
    private GestureDetectorCompat mDetector;
    private boolean hasIntent;
    private SimilarViewPager similarPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variants);

        mDetector = new GestureDetectorCompat(this,this);

        similarPager = (SimilarViewPager) findViewById(R.id.similarPager);
        setNumPages(5);

        SimilarPagerAdapter bigAdapter = new SimilarPagerAdapter(getSupportFragmentManager());
        similarPager.setAdapter(bigAdapter);
    }

    private class SimilarPagerAdapter extends FragmentStatePagerAdapter {
        public SimilarPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SimilarFragment.create(position);
        }

        @Override
        public int getCount() {
            return getNumPages();
        }
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public void TrackScroll(MotionEvent event1, MotionEvent event2) {
        int longScrollLimit = 1000;
        if (Math.abs(event1.getY() - event2.getY()) > longScrollLimit && !hasIntent) {
            hasIntent = true;
            similarPager.setCurrentItem(similarPager.getCurrentItem() + (int)Math.signum(event1.getY() - event2.getY()));
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        this.mDetector.onTouchEvent(event);

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        hasIntent = false;

        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        TrackScroll(event1, event2);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }
}
