package com.level.fractal.salestwo.helpers;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.level.fractal.salestwo.R;

public class ImagesFactory {
    public static void AddImageCardToLinearLayout(Context context, LinearLayoutCompat parent, int resId, @Nullable View.OnClickListener clickListener, int width, int height) {
        ConstraintLayout layout = new ConstraintLayout(context);
        layout.setLayoutParams(new LinearLayoutCompat.LayoutParams(width, height));

        AppCompatImageButton imgBtn = AddImageToCard(context, resId, clickListener, 150, 150, layout);
        int imgId = imgBtn.getId();

        ConstraintSet cs1 = new ConstraintSet();
        cs1.clone(layout);
        cs1.constrainWidth(imgId, 250);
        cs1.constrainHeight(imgId, 350);
        cs1.connect(imgId, ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, 8);
        cs1.connect(imgId, ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM, 8);
        cs1.connect(imgId, ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT, 8);
        cs1.connect(imgId, ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT, 8);
        cs1.applyTo(layout);


        AppCompatImageButton favBtn = AddImageToCard(context, R.mipmap.icon_favorite, null, R.dimen.detail_icon_size, R.dimen.detail_icon_size, layout);
        int favId = favBtn.getId();

        ConstraintSet cs2 = new ConstraintSet();
        cs2.clone(layout);
        cs2.constrainWidth(favId, 100);
        cs2.constrainHeight(favId, 100);
        cs2.connect(favId, ConstraintSet.TOP, imgId, ConstraintSet.TOP, 8);
        cs2.connect(favId, ConstraintSet.LEFT, imgId, ConstraintSet.RIGHT, 8);
        cs2.applyTo(layout);

        parent.addView(layout);
    }

    private static AppCompatImageButton AddImageToCard(Context context, int resId, @Nullable View.OnClickListener clickListener, int width, int height, ConstraintLayout layout) {
        AppCompatImageButton imgBtn = new AppCompatImageButton(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            imgBtn.setId(View.generateViewId());
        }
        imgBtn.setLayoutParams(new LinearLayoutCompat.LayoutParams(width, height));
        imgBtn.setImageResource(resId);
        imgBtn.setAdjustViewBounds(true);
        imgBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imgBtn.setOnClickListener(clickListener);

        layout.addView(imgBtn);

        return imgBtn;
    }
}
