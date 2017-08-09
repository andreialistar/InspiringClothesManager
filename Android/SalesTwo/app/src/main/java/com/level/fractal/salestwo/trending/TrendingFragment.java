package com.level.fractal.salestwo.trending;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.level.fractal.salestwo.R;
import com.level.fractal.salestwo.helpers.ImagesFactory;
import com.level.fractal.salestwo.helpers.IntentManager;
import com.level.fractal.salestwo.helpers.ResourcesLoader;
import com.level.fractal.salestwo.helpers.SalesConstants;
import com.level.fractal.salestwo.variants.VariantsActivity;

public class TrendingFragment extends Fragment {
    private int pageNumber;
    public static TrendingFragment create(int pageNumber) {
        TrendingFragment fragment = new TrendingFragment();
        Bundle args = new Bundle();
        args.putInt(SalesConstants.BUNDLE_PAGE_NUMBER, pageNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_trending, container, false);

        LinearLayoutCompat layout = (LinearLayoutCompat)rootView.findViewById(R.id.trendingLayout);

        if (pageNumber == 0) {
            int[] imagesIds = ResourcesLoader.GetImagesIds("Trending1");
            for (int imageId : imagesIds) {
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentManager.OpenActivity(v.getContext(), VariantsActivity.class, null);
                    }
                };
                ImagesFactory.AddSimpleImageToLinearLayout(this.getContext(), layout, imageId, listener, LinearLayoutCompat.LayoutParams.MATCH_PARENT, 500);
            }
        }
        else if (pageNumber == 1) {
            int[] imagesIds = ResourcesLoader.GetImagesIds("Trending2");
            for (int imageId : imagesIds) {
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentManager.OpenActivity(v.getContext(), VariantsActivity.class, null);
                    }
                };
                ImagesFactory.AddSimpleImageToLinearLayout(this.getContext(), layout, imageId, listener, LinearLayoutCompat.LayoutParams.MATCH_PARENT, 400);
            }
        }

        layout.postInvalidate();

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

    public void setPageNumber(int mPageNumber) {
        this.pageNumber = mPageNumber;
    }
}
