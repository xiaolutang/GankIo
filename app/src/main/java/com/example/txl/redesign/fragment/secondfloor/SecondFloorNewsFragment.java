package com.example.txl.redesign.fragment.secondfloor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.txl.gankio.viewimpl.WebActivity;
import com.example.txl.redesign.adpter.BaseAdapter;
import com.example.txl.redesign.adpter.SecondFloorAdapter;
import com.example.txl.redesign.data.XmlyFmData;
import com.example.txl.redesign.fragment.BaseRefreshFragment;
import com.example.txl.redesign.fragment.NavigationFragment;
import com.example.txl.redesign.data.model.NewsData;
import com.example.txl.redesign.fragment.video.DouYinVideoActivity;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.List;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/25
 * description：有二楼效果的fragment
 */
public class SecondFloorNewsFragment extends BaseRefreshFragment<SecondFloorAdapter,SecondFloorContract.Presenter> implements SecondFloorContract.View {
    protected String categoryId;
    List<XmlyFmData> fuliDataList;
    /**
     * 二楼效果
     * */
    protected TwoLevelHeader twoLevelHeader;
    protected ImageView twoLevelContentImage;
    protected ImageView twoLevelImage;
    /**
     * RecyclerView Y方向的滚动距离
     * */
    private int recyclerViewDy;

    @Override
    public void refreshFinish(List<XmlyFmData> dataList, boolean hasMore) {
        //天气透不出来
//        dataList.add( 0,new XmlyFmData( XmlyFmData.TYPE_WEATHER ) );
        adapter.setNewsData( dataList );
        smartRefreshLayout.finishRefresh(true);
        closeLoadingView();
    }

    @Override
    public void refreshError() {
        smartRefreshLayout.finishRefresh(false);
    }

    @Override
    public void loadMoreFinish(List<XmlyFmData> dataList, boolean hasMore) {
        if(!hasMore){
            smartRefreshLayout.finishLoadMoreWithNoMoreData();
        }else {
            smartRefreshLayout.finishLoadMore();
        }
        adapter.addNewsData( dataList );
    }

    @Override
    public void loadMoreError() {
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    protected void initView() {
        categoryId = getFragmentArguments().getString("category_id");
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
        twoLevelContentImage.setClickable( false );

        twoLevelImage = new ImageView(getContext());
        twoLevelImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        twoLevelImage.setLayoutParams(params);

        twoLevelHeader.addView(twoLevelImage);
        twoLevelContentImage.setVisibility( View.INVISIBLE );
        twoLevelHeader.addView(twoLevelContentImage);
        // FIXME: 2019/4/1 点击事件在这个位置但是显示却在NavigationFragment不利于维护
        twoLevelImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), DouYinVideoActivity.class );
                getContext().startActivity( intent );
            }
        } );
        twoLevelHeader.setRefreshHeader(classicsHeader);
        smartRefreshLayout.setEnableLoadMore( false );
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
                if(fragment instanceof NavigationFragment && scrollY != 0){
                    NavigationFragment navigationFragment = (NavigationFragment) fragment;
                    navigationFragment.setNavigationAlpha(scrollY);
                    navigationBgAlpha = scrollY;
                }
            }
        });
        adapter.setItemClickListener( new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, NewsData newsData) {
                Intent intent = new Intent( getContext(), WebActivity.class );
                intent.putExtra( "url",newsData.getUrl() );
                intent.putExtra( "title",newsData.getDesc() );
                getContext().startActivity( intent );
            }
        } );
    }

    @Override
    protected void initData() {
        presenter.refresh();
    }

    @Override
    protected SecondFloorPresenter getPresenter() {
        return new SecondFloorPresenter(this,categoryId);
    }

    @Override
    protected SecondFloorAdapter getAdapter() {
        return new SecondFloorAdapter( getContext() );
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager( getContext(),LinearLayoutManager.VERTICAL ,false);
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

    @Override
    public void setPresenter(SecondFloorContract.Presenter presenter) {

    }

    /**
     *
     * */
    class SecondFloorMultiPurposeListener extends SimpleMultiPurposeListener {
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

        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            super.onRefresh( refreshLayout );
            if(fuliDataList != null && fuliDataList.size() != 0 ){
                int index = (int) (fuliDataList.size()*Math.random());
                Fragment fragment = getParentFragment();
                if(fragment instanceof NavigationFragment){
                    NavigationFragment navigationFragment = (NavigationFragment) fragment;
                    navigationFragment.onSecondFloorRefresh( fuliDataList.get( index ).getNewsData().getUrl() );
                }
            }
        }
    }

    @Override
    public void onFuliDataCallback(List<XmlyFmData> dataList) {

        fuliDataList = dataList;
    }
}
