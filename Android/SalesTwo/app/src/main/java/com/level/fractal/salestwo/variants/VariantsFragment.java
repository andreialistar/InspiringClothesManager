package com.level.fractal.salestwo.variants;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.level.fractal.salestwo.R;
import com.level.fractal.salestwo.helpers.ResourcesLoader;
import com.level.fractal.salestwo.helpers.SalesConstants;

public class VariantsFragment extends Fragment {
    private int variantsNumber;
    private int pageNumber;
    public static VariantsFragment create(int variantsNumber, int pageNumber) {
        VariantsFragment fragment = new VariantsFragment();
        Bundle args = new Bundle();
        args.putInt(SalesConstants.BUNDLE_VARIANTS_NUMBER, variantsNumber);
        args.putInt(SalesConstants.BUNDLE_PAGE_NUMBER, pageNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_variants, container, false);

        VariantsImageView imageView = (VariantsImageView)rootView.findViewById(R.id.variantsImageView);

        int resID = ResourcesLoader.GetImagesIds("Variants" + variantsNumber)[pageNumber];
        imageView.image = ResourcesCompat.getDrawable(getResources(), resID, null);

        imageView.postInvalidate();

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            setVariantsNumber(getArguments().getInt(SalesConstants.BUNDLE_VARIANTS_NUMBER));
            setPageNumber(getArguments().getInt(SalesConstants.BUNDLE_PAGE_NUMBER));
        }
    }

    public void setVariantsNumber(int number) {
        this.variantsNumber = number;
    }

    public void setPageNumber(int number) {
        this.pageNumber = number;
    }
}
