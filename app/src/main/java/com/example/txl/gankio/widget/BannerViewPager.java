package com.example.txl.gankio.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/9/13
 * description：
 */
public class BannerViewPager extends ViewPager {
    public BannerViewPager(Context context) {
        super( context );
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super( context, attrs );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout( changed, l, t, r, b );
    }
}
