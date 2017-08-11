package com.level.fractal.salestwo.helpers;

import android.content.Context;
import android.graphics.Color;
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
    public static void AddImageCardToLinearLayout(Context context, LinearLayoutCompat parent, int resId,
                                                  @Nullable View.OnClickListener clickListener,
                                                  int width, int height) {
        ConstraintLayout layout = new ConstraintLayout(context);
        layout.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

        AppCompatImageButton imgBtn = AddImageToCard(context, resId,
                clickListener, MathUtils.GetRandomColor(256, 256, 128, 128),
                0, height, layout);
        int imgId = imgBtn.getId();
        int layoutId = layout.getId();


        AppCompatImageButton cartBtn = AddImageToCard(context, R.mipmap.icon_cart,
                null, Color.TRANSPARENT,
                128, 128, layout);
        int cartId = cartBtn.getId();

        AppCompatImageButton favBtn = AddImageToCard(context, R.drawable.icon_favorite,
                null, Color.TRANSPARENT,
                128, 128, layout);
        int favId = favBtn.getId();

        AppCompatImageButton faceBtn = AddImageToCard(context, R.mipmap.icon_facebook,
                null, Color.TRANSPARENT,
                128, 128, layout);
        int faceId = faceBtn.getId();

        ConstraintSet cs1 = new ConstraintSet();
        cs1.clone(layout);
        cs1.constrainWidth(imgId, ViewGroup.LayoutParams.MATCH_PARENT);
        cs1.constrainHeight(imgId, height);
        cs1.connect(imgId, ConstraintSet.TOP, layoutId, ConstraintSet.TOP, 0);
        cs1.connect(imgId, ConstraintSet.BOTTOM, layoutId, ConstraintSet.BOTTOM, 0);
        cs1.connect(imgId, ConstraintSet.LEFT, layoutId, ConstraintSet.LEFT, 0);
        cs1.connect(imgId, ConstraintSet.RIGHT, layoutId, ConstraintSet.RIGHT, 0);
        cs1.applyTo(layout);

        ConstraintSet cs2 = new ConstraintSet();
        cs2.clone(layout);
        cs2.constrainWidth(cartId, 128);
        cs2.constrainHeight(cartId, 128);
        cs2.connect(cartId, ConstraintSet.RIGHT, favId, ConstraintSet.LEFT, 64);
        cs2.connect(cartId, ConstraintSet.TOP, layoutId, ConstraintSet.TOP, 32);
        cs2.applyTo(layout);

        ConstraintSet cs3 = new ConstraintSet();
        cs3.clone(layout);
        cs3.constrainWidth(favId, 128);
        cs3.constrainHeight(favId, 128);
        cs3.connect(favId, ConstraintSet.RIGHT, faceId, ConstraintSet.LEFT, 64);
        cs3.connect(favId, ConstraintSet.TOP, layoutId, ConstraintSet.TOP, 32);
        cs3.applyTo(layout);

        ConstraintSet cs4 = new ConstraintSet();
        cs4.clone(layout);
        cs4.constrainWidth(faceId, 128);
        cs4.constrainHeight(faceId, 128);
        cs4.connect(faceId, ConstraintSet.RIGHT, imgId, ConstraintSet.RIGHT, 64);
        cs4.connect(faceId, ConstraintSet.TOP, layoutId, ConstraintSet.TOP, 32);
        cs4.applyTo(layout);

        parent.addView(layout);
    }

    private static AppCompatImageButton AddImageToCard(Context context, int resId,
                                                       @Nullable View.OnClickListener clickListener, int bkgColor,
                                                       int width, int height, ConstraintLayout layout) {
        AppCompatImageButton imgBtn = new AppCompatImageButton(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            imgBtn.setId(View.generateViewId());
        }
        imgBtn.setLayoutParams(new LinearLayoutCompat.LayoutParams(width, height));
        imgBtn.setImageResource(resId);
        imgBtn.setBackgroundColor(bkgColor);
//        imgBtn.setBackgroundTintMode(Mode.CLEAR);
//        imgBtn.setAdjustViewBounds(true);
        imgBtn.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgBtn.setOnClickListener(clickListener);

        layout.addView(imgBtn);

        return imgBtn;
    }
}
