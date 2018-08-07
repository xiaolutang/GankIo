package com.example.txl.gankio.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/7
 * description：按页滑动的RecyclerView
 */
public class PageScrollerRecyclerView extends RecyclerView {

    IPullRefreshListener listener;

    public PageScrollerRecyclerView(Context context) {
        super( context );
    }

    public PageScrollerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super( context, attrs );
    }

    public PageScrollerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super( context, attrs, defStyle );
    }

    public void setPullRefreshListener(IPullRefreshListener listener) {
        this.listener = listener;
    }

    //当前滑动距离
    private int offsetY = 0;
    private int offsetX = 0;
    //按下屏幕点
    private int startY = 0;
    private int startX = 0;
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
//手指按下的开始坐标
            startY = offsetY;
            startX = offsetX;
        }
        return super.onTouchEvent( e );
    }

    @Override
    public void setOnFlingListener(@Nullable OnFlingListener onFlingListener) {
        super.setOnFlingListener( onFlingListener );
    }
}
