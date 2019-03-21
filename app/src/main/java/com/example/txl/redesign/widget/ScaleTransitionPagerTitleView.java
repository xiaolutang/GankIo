package com.example.txl.redesign.widget;

import android.content.Context;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/**
 * 带颜色渐变和缩放的指示器标题
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
public class ScaleTransitionPagerTitleView extends ColorTransitionPagerTitleView {
    /**
     * 放大比例
     * */
    private float mScale = 1.2f;

    public ScaleTransitionPagerTitleView(Context context) {
        super(context);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        super.onEnter(index, totalCount, enterPercent, leftToRight);    // 实现颜色渐变
        setScaleX(1.0f + (mScale -1 ) * enterPercent);
        setScaleY(1.0f + (mScale -1 )  * enterPercent);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        super.onLeave(index, totalCount, leavePercent, leftToRight);    // 实现颜色渐变
        setScaleX(mScale - (mScale -1) * leavePercent);
        setScaleY(mScale - (mScale -1) * leavePercent);
    }

    /**
     * 设置放大比例
     * */
    public void setmScale(float mScale) {
        this.mScale = mScale;
    }
}
