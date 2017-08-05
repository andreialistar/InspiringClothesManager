package com.level.fractal.salestwo.messy;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.level.fractal.salestwo.R;

public class BigImageActivity extends AppCompatActivity {

    public static final String BUNDLE_BIG_ARTICLE = "bundleArticle";
    public static final String BUNDLE_BIG_NUM_PAGES = "bundleNumPages";
    public static final String BUNDLE_BIG_PAGE_INDEX = "bundleBigPageIndex";

    private String articlePrefix;
    private int numPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);

        ViewPager bigPager = (ViewPager) findViewById(R.id.bigImagePager);
        Bundle bigPagerBundle = getIntent().getExtras();
        if (bigPagerBundle != null) {
            setArticleIndex(bigPagerBundle.getString(BUNDLE_BIG_ARTICLE));
            setNumPages(bigPagerBundle.getInt(BUNDLE_BIG_NUM_PAGES));
        }

        BigImagePagerAdapter bigAdapter = new BigImagePagerAdapter(getSupportFragmentManager());
        bigPager.setAdapter(bigAdapter);

        TabLayout bigDots = (TabLayout) findViewById(R.id.bigImageDots);
        bigDots.setupWithViewPager(bigPager, true);
    }

    private class BigImagePagerAdapter extends FragmentStatePagerAdapter {
        public BigImagePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return BigImageSlideFragment.create(position, getArticleIndex());
        }

        @Override
        public int getCount() {
            return getNumPages();
        }
    }

    public String getArticleIndex() {
        return articlePrefix;
    }

    public void setArticleIndex(String articlePrefix) {
        this.articlePrefix = articlePrefix;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }
}
