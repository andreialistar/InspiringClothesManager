package com.level.fractal.salestwo.helpers;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImagesFactory {
    public static int AddSimpleImageToLinearLayout(Context context, LinearLayoutCompat parent, int resId, @Nullable View.OnClickListener clickListener, int width, int height) {
        AppCompatImageButton imgBtn = new AppCompatImageButton(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            imgBtn.setId(View.generateViewId());
        }
        imgBtn.setLayoutParams(new LinearLayoutCompat.LayoutParams(width, height));
        imgBtn.setImageResource(resId);
        imgBtn.setAdjustViewBounds(true);
        imgBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imgBtn.setOnClickListener(clickListener);

        parent.addView(imgBtn);

        return imgBtn.getId();
    }
}
