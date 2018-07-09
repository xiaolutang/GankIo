package com.example.txl.gankio.viewimpl.video;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.txl.gankio.R;
import com.example.txl.gankio.adapter.IdelInfoAdapter;
import com.example.txl.gankio.adapter.VideoAdapter;
import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.gankio.bean.BeautyGirls;
import com.example.txl.gankio.presenter.IdelInfoPersenter;
import com.example.txl.gankio.presenter.VideoPresenter;
import com.example.txl.gankio.viewimpl.IdelInfoActivity;
import com.example.txl.gankio.viewinterface.IGetFuLiData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：福利图片
 */
public class VideoFragment extends BaseFragment implements IGetFuLiData,SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;

    VideoPresenter videoPresenter;
    VideoAdapter videoAdapter;

    int defaultCount = 20;
    int currentPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.model_swiperefreshlayout, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL);
//        StaggeredGridLayoutManager recyclerViewLayoutManager =
//                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        videoAdapter = new VideoAdapter();
        videoPresenter = new VideoPresenter( getContext() );
        recyclerview.setAdapter(videoAdapter  );
        swiperefreshlayout.setOnRefreshListener( this );
        recyclerview.addOnScrollListener( new RecyclerView.OnScrollListener(){
            int lastVisibleItem ;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1 == videoAdapter.getItemCount()){
                    videoPresenter.getFuLiData( defaultCount,++currentPage,VideoFragment.this,false );
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //最后一个可见的ITEM
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        } );
    }

    private void initData(){
        videoPresenter.getFuLiData( defaultCount, currentPage, this,true);
    }

    @Override
    protected String getFragmentName() {
        return "休息视频";
    }

    @Override
    public boolean hasMultiFragment() {
        return false;
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        videoPresenter.getFuLiData( defaultCount, currentPage, this,true);
    }

    @Override
    public void onAddIdelInfoSuccess(List<BeautyGirls.Girl> results) {
        videoAdapter.addData( results );
    }

    @Override
    public void onAddIdelInfoFailed() {

    }

    @Override
    public void updateIdelInfoSuccess(List<BeautyGirls.Girl> results) {
        videoAdapter.updateData( results );
    }

    @Override
    public void updateIdelInfoFailed() {

    }
}
