package com.example.txl.gankio.viewimpl.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.txl.gankio.R;
import com.example.txl.gankio.adapter.VideoAdapter;
import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.gankio.bean.BeautyGirls;
import com.example.txl.gankio.presenter.VideoPresenter;
import com.example.txl.gankio.viewinterface.IGetFuLiData;
import com.example.txl.gankio.widget.PullRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：福利图片
 */
public class FuLiFragment extends BaseFragment implements IGetFuLiData,SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recyclerview)
    PullRefreshRecyclerView recyclerview;
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
        StaggeredGridLayoutManager recyclerViewLayoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerview.setLayoutManager(recyclerViewLayoutManager);
        videoAdapter = new VideoAdapter();
        videoPresenter = new VideoPresenter( getContext() );
        recyclerview.setAdapter(videoAdapter  );
        recyclerview.setOnRefreshListener( new PullRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                videoPresenter.getFuLiData( defaultCount, currentPage, FuLiFragment.this,true);
            }

            @Override
            public void onLoadMore() {
                videoPresenter.getFuLiData( defaultCount,++currentPage,FuLiFragment.this,false );
            }
        } );
        recyclerview.setmEnablePullLoad( true );
        recyclerview.setmEnablePullRefresh( true );
        swiperefreshlayout.setOnRefreshListener( this );
        swiperefreshlayout.setEnabled( false );
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
    public void onAddFuLiDataSuccess(List<BeautyGirls.Girl> results) {
        videoAdapter.addData( results );
        //fixme 这是一个不好的做法，应该是view自己能够判断什么时候数据刷新成功
        recyclerview.stopLoadMore();
    }

    @Override
    public void onAddFuLiDataFailed() {

    }

    @Override
    public void updateFuLiDataSuccess(List<BeautyGirls.Girl> results) {
        videoAdapter.updateData( results );
        recyclerview.stopRefresh();
    }

    @Override
    public void updateFuLiDataFailed() {

    }
}
