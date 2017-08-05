package com.level.fractal.salestwo.variants;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.level.fractal.salestwo.R;
import com.level.fractal.salestwo.helpers.SalesConstants;

public class SimilarFragment extends Fragment {
    private int pageNumber;
    private int numPages;

    public static SimilarFragment create(int pageNumber) {
        Log.d("Intercept", String.valueOf(pageNumber));
        SimilarFragment fragment = new SimilarFragment();
        Bundle args = new Bundle();
        args.putInt(SalesConstants.BUNDLE_PAGE_NUMBER, pageNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_similar, container, false);

        ViewPager variantsPager = (ViewPager) rootView.findViewById(R.id.variantsPager);
        setNumPages(5);
//        Bundle bigPagerBundle = getIntent().getExtras();
//        if (bigPagerBundle != null) {
//            setNumPages(bigPagerBundle.getInt("numPages"));
//        }

        VariantsPagerAdapter bigAdapter = new VariantsPagerAdapter(getActivity(), getChildFragmentManager());
        variantsPager.setAdapter(bigAdapter);

        TabLayout bigDots = (TabLayout) rootView.findViewById(R.id.variantsDots);
        bigDots.setupWithViewPager(variantsPager, true);

        variantsPager.postInvalidate();

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            setPageNumber(getArguments().getInt(SalesConstants.BUNDLE_PAGE_NUMBER));
        }
    }

    public int getPageNumber() { return pageNumber; }

    public void setPageNumber(int mPageNumber) {
        this.pageNumber = mPageNumber;
    }

    private class VariantsPagerAdapter extends FragmentPagerAdapter {
        Context context=null;

        public VariantsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return VariantsFragment.create(getPageNumber(), position);
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
}
