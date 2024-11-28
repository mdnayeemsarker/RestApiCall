package com.abmn.library.restapicall.Core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RotateDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.abmn.library.restapicall.R;

import java.util.Objects;

public class ProgressDisplay {

    @SuppressLint("StaticFieldLeak")
    public static ProgressBar mProgressBar;
    private final Activity mActivity;

    @SuppressLint("UseCompatLoadingForDrawables")
    public ProgressDisplay(Activity activity) {
        this.mActivity = activity;

        try {
            ViewGroup layout = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();

            mProgressBar = new ProgressBar(activity, null, android.R.attr.progressBarStyle);
            mProgressBar.setIndeterminateDrawable(activity.getDrawable(R.drawable.progress_dialog));
            mProgressBar.setIndeterminate(true);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

            RelativeLayout rl = new RelativeLayout(activity);
            rl.setGravity(Gravity.CENTER);
            rl.addView(mProgressBar);
            layout.addView(rl, params);
            hideProgress();

            // Apply colors from Config
            applyProgressColors();
        } catch (Exception e) {
            if (Config.isDebugMode())
                Log.d("ABMN_Progress_Dialog", Objects.requireNonNull(e.getMessage()));
        }
    }

    public void showProgress() {
        if (mProgressBar.getVisibility() == View.GONE) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void applyProgressColors() {
        try {
            @SuppressLint("UseCompatLoadingForDrawables") RotateDrawable drawable = (RotateDrawable) mActivity.getDrawable(R.drawable.progress_dialog);
            if (drawable != null) {
                GradientDrawable gradient = (GradientDrawable) drawable.getDrawable();
                assert gradient != null;
                gradient.setColors(new int[]{Config.getStartColor(), Config.getCenterColor(), Config.getEndColor()});
                mProgressBar.setIndeterminateDrawable(drawable);
            }
        } catch (Exception e) {
            if (Config.isDebugMode())
                Log.d("ABMN_Progress_Dialog", Objects.requireNonNull(e.getMessage()));
        }
    }

}
