package com.level.fractal.salestwo.messy;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.level.fractal.salestwo.R;

public class FirstPageActivity extends AppCompatActivity {
    private static Context firstPageContext;

    public static String[] articlePrefixes = new String[] { "dress", "hat", "jeans", "shirt", "tshirt" };
    public static int[] articleNumSamples = new int[] { 5, 5, 4, 5, 5 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        firstPageContext = this;

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.firstPageLayout);

        int[] imgIds = new int[] { R.drawable.dress1, R.drawable.hat1, R.drawable.jeans2, R.drawable.shirt1, R.drawable.tshirt1, R.drawable.dress2 };
        int sizeSmall = 300;
        int sizeBig = 600;
        int[] imgBtnIds = new int[imgIds.length];

        int transitionSuffix = 1;
        imgBtnIds[0] = AddButtonImage(layout, transitionSuffix++, articlePrefixes[0], sizeSmall, sizeSmall, imgIds[0], -layout.getId(), -layout.getId());
        imgBtnIds[1] = AddButtonImage(layout, transitionSuffix++, articlePrefixes[1], sizeBig, sizeBig, imgIds[1], -layout.getId(), imgBtnIds[0]);
        imgBtnIds[2] = AddButtonImage(layout, transitionSuffix++, articlePrefixes[2], sizeSmall, sizeSmall, imgIds[2], imgBtnIds[0], -layout.getId());
        imgBtnIds[3] = AddButtonImage(layout, transitionSuffix++, articlePrefixes[3], sizeBig, sizeBig, imgIds[3], imgBtnIds[2], -layout.getId());
        imgBtnIds[4] = AddButtonImage(layout, transitionSuffix++, articlePrefixes[4], sizeSmall, sizeSmall, imgIds[4], imgBtnIds[1], imgBtnIds[3]);
        imgBtnIds[5] = AddButtonImage(layout, transitionSuffix++, articlePrefixes[0], sizeSmall, sizeSmall, imgIds[5], imgBtnIds[4], imgBtnIds[3]);


//        AppCompatImageButton buttonDress = new AppCompatImageButton(this);
//        buttonDress.setLayoutParams(new ActionBar.LayoutParams(100, 100));
//        buttonDress.setImageResource(R.drawable.dress1);
//        buttonDress.setAdjustViewBounds(true);
//        buttonDress.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        layout.addView(buttonDress);
//
//        ConstraintSet csDress = new ConstraintSet();
//        csDress.clone(layout);
//        csDress.constrainHeight(buttonDress.getId(), (int)(200));
//        csDress.constrainWidth(buttonDress.getId(), (int)(200));
//        csDress.connect(buttonDress.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, 8);
//        csDress.connect(buttonDress.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT, 8);
//        csDress.applyTo(layout);
//
//        AppCompatImageButton buttonHat = new AppCompatImageButton(this);
//        buttonHat.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        buttonHat.setImageResource(R.drawable.hat1);
//        layout.addView(buttonHat);
//
//        ConstraintSet csHat = new ConstraintSet();
//        csHat.clone(layout);
//        csHat.constrainHeight(buttonHat.getId(), (int)(200));
//        csHat.constrainWidth(buttonHat.getId(), (int)(200));
//        csHat.connect(buttonHat.getId(), ConstraintSet.START, buttonDress.getId(), ConstraintSet.END, 8);
//        csHat.applyTo(layout);
    }

    private int AddButtonImage(final ConstraintLayout parent, int transitionSuffix, final String articlePrefix, int sizeX, int sizeY, final int imgId, int topId, int leftId)
    {
        AppCompatImageButton imgBtn = new AppCompatImageButton(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            imgBtn.setId(View.generateViewId());
        }
        imgBtn.setLayoutParams(new ActionBar.LayoutParams(sizeX, sizeY));
        imgBtn.setImageResource(imgId);
        imgBtn.setAdjustViewBounds(true);
        imgBtn.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imgBtn.setTransitionName("transition " + transitionSuffix);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstPageActivity.OpenBigImageActivity(parent, articlePrefix, 5, v);
            }
        });
        parent.addView(imgBtn);

        ConstraintSet cs = new ConstraintSet();
        cs.clone(parent);
        cs.constrainWidth(imgId, sizeX);
        cs.constrainHeight(imgId, sizeY);
        cs.connect(imgBtn.getId(), ConstraintSet.TOP, Math.abs(topId), topId > 0 ? ConstraintSet.BOTTOM : ConstraintSet.TOP, 8);
        cs.connect(imgBtn.getId(), ConstraintSet.LEFT, Math.abs(leftId), leftId > 0 ? ConstraintSet.RIGHT : ConstraintSet.LEFT, 8);
        cs.applyTo(parent);

        return imgBtn.getId();
    }

    public static void OpenBigImageActivity(View parent, String articlePrefix, int numPages, final View view){
        final Intent intent = new Intent(firstPageContext, BigImageActivity.class);
        Bundle b = new Bundle();
        b.putString(BigImageActivity.BUNDLE_BIG_ARTICLE, articlePrefix);
        b.putInt(BigImageActivity.BUNDLE_BIG_NUM_PAGES, numPages);
        intent.putExtras(b);
//        Animation zoomAnim = AnimationUtils.loadAnimation(firstPageContext, R.anim.zoomimage);
        AnimationSet zoomAnimSet = new AnimationSet(true);
        zoomAnimSet.setDuration(700);

        if (parent == null || view == null)
        {
            firstPageContext.startActivity(intent);
            return;
        }

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int[] parentLocation = new int[2];
        parent.getLocationOnScreen(parentLocation);
        Log.d("Locations", String.format("lx: %d, ly: %d, px: %d, py: %d", location[0], location[1], parentLocation[0], parentLocation[1]));
        int offsetX = -location[0] + parentLocation[0];
        int offsetY = -location[1] + parentLocation[1];
        int widthBefore = view.getWidth();
        int widthAfter = parent.getWidth();
        int heightBefore = view.getHeight();
        int heightAfter = parent.getWidth();
        Log.d("Locations", String.format("hx: %d, hy: %d, phx: %d, phy: %d", widthBefore, heightBefore, widthAfter, heightAfter));
        float scaleWidth = 1.0f * widthAfter / widthBefore;
        float scaleHeight = 1.0f * heightAfter / heightBefore;
        TranslateAnimation ta = new TranslateAnimation(
                0,
                offsetX / scaleWidth,
                0,
                offsetY / scaleHeight);
        zoomAnimSet.addAnimation(ta);

        ScaleAnimation sa = new ScaleAnimation(
                1, scaleWidth,
                1, scaleHeight);
//        ScaleAnimation sa = new ScaleAnimation(1, parent.getWidth() / view.getWidth(), 1, parent.getHeight() / view.getHeight());
        zoomAnimSet.addAnimation(sa);

        view.bringToFront();
        view.startAnimation(zoomAnimSet);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] location2 = new int[2];
                view.getLocationOnScreen(location2);
                Log.d("Locations", String.format("lx: %f, ly: %f", view.getTranslationX(), view.getTranslationY()));
                firstPageContext.startActivity(intent);
            }
        }, zoomAnimSet.getDuration() * 9 / 10);
    }
}
