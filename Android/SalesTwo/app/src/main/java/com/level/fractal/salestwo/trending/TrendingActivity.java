package com.level.fractal.salestwo.trending;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.level.fractal.salestwo.R;
import com.level.fractal.salestwo.helpers.InputTracker;
import com.level.fractal.salestwo.helpers.IntentManager;
import com.level.fractal.salestwo.variants.VariantsActivity;

import java.util.HashMap;
import java.util.Map;

public class TrendingActivity extends AppCompatActivity implements
        GestureDetector.OnGestureListener{
    private static final String DEBUG_TAG = "Touching";
    private static Context currentContext;
    private int numPages;
    private GestureDetectorCompat mDetector;
    private boolean hasIntent;
    private int longScrollLimit = 600;

    private Map<Integer, InputTracker> inputActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);
        currentContext = this;

        mDetector = new GestureDetectorCompat(this,this);

        inputActions = new HashMap<Integer, InputTracker>();

        TrendingViewPager trendingPager = (TrendingViewPager)findViewById(R.id.trendingPager);
        setNumPages(2);

        TrendingPagerAdapter trendingAdapter = new TrendingPagerAdapter(getSupportFragmentManager());
        trendingPager.setAdapter(trendingAdapter);
    }

    private class TrendingPagerAdapter extends FragmentStatePagerAdapter {
        public TrendingPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TrendingFragment.create(position);
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
        if (Math.abs(event1.getY() - event2.getY()) > longScrollLimit && !hasIntent) {
            hasIntent = true;
            IntentManager.OpenActivity(currentContext, VariantsActivity.class, null);
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
//        Log.d(DEBUG_TAG,"onDown: " + event.toString());
        hasIntent = false;

        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
//        Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
//        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
//        Log.d(DEBUG_TAG, "onScroll: " + distanceX + " " + distanceY);
        TrackScroll(event1, event2);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
//        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
//        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }
}
