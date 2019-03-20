package com.example.txl.redesign.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/19
 * description：基于今日头条的屏幕适配方案
 */
public class ScreenUtils {
    /**
     * 默认基于360dp的屏幕宽度适配
     * */
    private static final int DEFAULT_SCREEN_WIDTH = 360;
    private static float sNoncompatDensity;
    private static float sNoncompatScaleDensity;
    public static void setCustomDensity(@NonNull Activity activity, @NonNull Application application){
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaleDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks( new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            } );
        }
        final float targetDensity = appDisplayMetrics.widthPixels / DEFAULT_SCREEN_WIDTH;
        final int targetDensityDpi = (int) (160 * targetDensity);
        final float targetScaleDensity = targetDensity * (sNoncompatScaleDensity / sNoncompatDensity);

        appDisplayMetrics.density = appDisplayMetrics.scaledDensity = targetDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;
        appDisplayMetrics.scaledDensity = targetScaleDensity;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = activityDisplayMetrics.scaledDensity = targetDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
    }
}
