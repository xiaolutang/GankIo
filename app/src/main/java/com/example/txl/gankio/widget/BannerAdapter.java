package com.example.txl.gankio.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/9/13
 * description：
 */
public class BannerAdapter<T> extends PagerAdapter implements ViewPager.OnPageChangeListener {
    protected final String TAG = getClass().getSimpleName();


    private Context mContext;

    private ViewLoaderInterface viewLoaderInterface;
    private WeakReference<ViewPager> viewPagerWeakReference;

    private List<View> viewList;
    private List<T> data;
    private int realCount;
    private int currentItem;

    public BannerAdapter(Context mContext, ViewPager viewPager) {
        this.mContext = mContext;
        this.viewPagerWeakReference = new WeakReference<>( viewPager );
        viewPager.addOnPageChangeListener( this );
        init();
    }

    private void init(){
        viewList = new ArrayList<>(  );
        data = new ArrayList<>(  );
    }

    @Override
    public float getPageWidth(int position) {
        return 1f;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View pageView = viewList.get( position );
        container.addView( pageView );
        return pageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView( (View) object );
    }

    public void update(List<T> data){
        this.data.clear();
        this.data.addAll( data );
        realCount = data.size();
        start();
    }

    private void start(){
        initViewList(data);
        ViewPager pager = viewPagerWeakReference.get();
        if(pager != null){
            pager.setAdapter( this );
            pager.setPageMargin( 30 );
            pager.setOffscreenPageLimit( 3 );
            pager.setCurrentItem( 1 );
        }
    }

    public void setViewLoaderInterface(ViewLoaderInterface viewLoaderInterface) {
        this.viewLoaderInterface = viewLoaderInterface;
    }

    private void initViewList(List<T> data) {
        viewList.clear();
        for (int i=0; i<= realCount+1; i++){
            View pageView = null;
            if(viewLoaderInterface != null){
                pageView = viewLoaderInterface.createView( mContext );
            }
            if(pageView == null){
                pageView = new ImageView( mContext );
            }
            T itemData = null;
            if (i == 0){
                itemData = data.get( realCount - 1);
            }else if(i == realCount + 1){
                itemData = data.get( 0 );
            }else {
                itemData = data.get(i - 1);
            }
            viewList.add( pageView );
            if (viewLoaderInterface != null){
                viewLoaderInterface.displayView(mContext, itemData, pageView);
            } else{
                Log.e(TAG, "Please set images loader.");
            }

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentItem=position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        ViewPager pager = viewPagerWeakReference.get();
        if(pager == null){
            return;
        }
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE://No operation
                if (currentItem == 0) {
                    pager.setCurrentItem(realCount, false);
                } else if (currentItem == realCount + 1) {
                    pager.setCurrentItem(1, false);
                }
                break;
            case ViewPager.SCROLL_STATE_DRAGGING://start Sliding
                if (currentItem == realCount + 1) {
                    pager.setCurrentItem(1, false);
                } else if (currentItem == 0) {
                    pager.setCurrentItem(realCount, false);
                }
                break;
            case ViewPager.SCROLL_STATE_SETTLING://end Sliding
                break;
        }
    }

   public interface ViewLoaderInterface<T> extends Serializable {

        void displayView(Context context, T path, View imageView);

        View createView(Context context);
    }
}
