package com.level.fractal.salestwo.messy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.level.fractal.salestwo.R;

import java.util.Arrays;

public class BigImageSlideFragment extends Fragment {
    private String articlePrefix;
    private int pageNumber;

    public static BigImageSlideFragment create(int pageNumber, String articlePrefix) {
        BigImageSlideFragment fragment = new BigImageSlideFragment();
        Bundle args = new Bundle();
        args.putInt(BigImageActivity.BUNDLE_BIG_PAGE_INDEX, pageNumber);
        args.putString(BigImageActivity.BUNDLE_BIG_ARTICLE, articlePrefix);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_big_image_slide, container, false);

        BigImageView biv = (BigImageView)rootView.findViewById(R.id.bigImageView);

        int resID = getResources().getIdentifier(articlePrefix + (pageNumber + 1), "drawable", "com.level.fractal.salestwo");
        Log.d("FoundId", articlePrefix + " - " + pageNumber + " - " + String.valueOf(resID));
        biv.bigImage = ResourcesCompat.getDrawable(getResources(), resID, null);
        biv.bigImagePrefixIndex = Arrays.asList(FirstPageActivity.articlePrefixes).indexOf(articlePrefix);

        biv.postInvalidate();

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            setPageNumber(getArguments().getInt(BigImageActivity.BUNDLE_BIG_PAGE_INDEX));
            setArticleNumber(getArguments().getString(BigImageActivity.BUNDLE_BIG_ARTICLE));
        }
    }

    public String getArticleNumber() {
        return articlePrefix;
    }

    public void setArticleNumber(String articlePrefix) {
        this.articlePrefix = articlePrefix;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int mPageNumber) {
        this.pageNumber = mPageNumber;
    }
}
