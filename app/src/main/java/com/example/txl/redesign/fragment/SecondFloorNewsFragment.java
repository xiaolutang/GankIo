package com.example.txl.redesign.fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/25
 * description：有二楼效果的fragment
 */
public class SecondFloorNewsFragment extends BaseNewsFragment {
    /**
     * RecyclerView Y方向的滚动距离
     * */
    private int recyclerViewDy;

    @Override
    protected void initView() {
        super.initView();
        //二楼效果
        navigationBgAlpha =0;
        twoLevelHeader = new TwoLevelHeader(getContext());
        ClassicsHeader classicsHeader = new ClassicsHeader(getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        classicsHeader.setLayoutParams(params);
        twoLevelContentImage = new ImageView(getContext());
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        twoLevelContentImage.setLayoutParams(params);
        twoLevelContentImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        twoLevelImage = new ImageView(getContext());
        twoLevelImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        twoLevelImage.setLayoutParams(params);

        twoLevelHeader.addView(twoLevelImage);
        twoLevelHeader.addView(twoLevelContentImage);
        twoLevelHeader.setRefreshHeader(classicsHeader);
        smartRefreshLayout.setRefreshHeader(twoLevelHeader);
        smartRefreshLayout.setOnMultiPurposeListener(new SecondFloorMultiPurposeListener());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int scrollY = getScrollY(dy);
                Log.d(TAG,"scrollY  ++++++++ "+scrollY);
                Fragment fragment = getParentFragment();
                if(fragment instanceof NavigationFragment){
                    NavigationFragment navigationFragment = (NavigationFragment) fragment;
                    navigationFragment.setNavigationAlpha(scrollY);
                    navigationBgAlpha = scrollY;
                }
            }
        });
    }

    /**
     * 获取RecyclerView的Y方向的滑动距离
     * */
    public int getScrollY(int dy) {
        recyclerViewDy += dy;
        if(recyclerViewDy > 255){
            return 255;
        }
        if(recyclerViewDy<0){
            return 0;
        }
        return recyclerViewDy;
    }

    /**
     *
     * */
    class SecondFloorMultiPurposeListener extends SimpleMultiPurposeListener {
        @Override
        public void onRefresh(RefreshLayout refreshLayout) {
            super.onRefresh(refreshLayout);
            presenter.refresh();
        }

        @Override
        public void onLoadMore(RefreshLayout refreshLayout) {
            super.onLoadMore(refreshLayout);
            presenter.loadMore();
        }

        @Override
        public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
            Fragment fragment = getParentFragment();
            if(fragment instanceof NavigationFragment){
                NavigationFragment navigationFragment = (NavigationFragment) fragment;
                navigationFragment.onChildHeadMoving( isDragging,percent,offset,headerHeight,maxDragHeight );
            }
//            twoLevelContentImage.setAlpha(1 - Math.min(percent, 1));
//            twoLevelImage.setTranslationY(Math.min(offset - twoLevelImage.getHeight() + twoLevelImage.getHeight(), smartRefreshLayout.getLayout().getHeight() - twoLevelImage.getHeight()));
        }

        @Override
        public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
            super.onStateChanged( refreshLayout, oldState, newState );
        }
    }
}
