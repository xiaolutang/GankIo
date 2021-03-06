package com.example.txl.gankio.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;



/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public abstract class BaseFragment extends Fragment implements IBaseView{
    protected String TAG = this.getClass().getSimpleName();

    protected String fragmentName;

    protected int navigationBgAlpha = 0xff;

    /**
     * 根布局
     * */
    protected View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public abstract String getFragmentName();

    protected Bundle getFragmentArguments()
    {
        return getArguments();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 当被选中的时候导航的背景透明度,默认不透明
     * */
    public int getNavigationBgAlpha(){
        return navigationBgAlpha;
    }

    public void setNavigationBgAlpha(@IntRange(from=0,to=255) int navigationBgAlpha){
        this.navigationBgAlpha = navigationBgAlpha;
    }

    public void setViewBackgroundAlpha(View view, int alpha) {
        if (view == null) return;

        Drawable drawable = view.getBackground();
        drawable = drawable.mutate();
        if (drawable != null) {
            drawable.setAlpha(alpha);
            view.setBackground( drawable );
        }
    }

    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
