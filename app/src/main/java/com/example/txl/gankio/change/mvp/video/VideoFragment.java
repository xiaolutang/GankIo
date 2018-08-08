package com.example.txl.gankio.change.mvp.video;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.txl.gankio.App;
import com.example.txl.gankio.R;
import com.example.txl.gankio.adapter.VideoAdapter;
import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.gankio.change.mvp.data.VideoBean;
import com.example.txl.gankio.player.AndroidPlayer;
import com.example.txl.gankio.widget.PullRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public class VideoFragment extends BaseFragment implements VideoContract.View {

    @BindView(R.id.recyclerview)
    PullRefreshRecyclerView recyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;

    VideoAdapter videoAdapter;
    VideoContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.model_swiperefreshlayout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        initView();

    }

    private void initView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL);
//        StaggeredGridLayoutManager recyclerViewLayoutManager =
//                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        videoAdapter = new VideoAdapter();
        recyclerview.setAdapter(videoAdapter  );
        swiperefreshlayout.setEnabled( false );
        recyclerview.setOnPullRefreshListener( new PullRefreshRecyclerView.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }

            @Override
            public void loadMore() {
                presenter.loadMore();
            }
        } );
    }

    private void initData(){}

    @Override
    protected String getFragmentName() {
        return "所有数据";
    }

    @Override
    public boolean hasMultiFragment() {
        return false;
    }

    @Override
    public void setPresenter(VideoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void refreshFinish(List<VideoBean.VideoInfo> videoInfoList) {
        videoAdapter.updateData( videoInfoList );
        recyclerview.setRefreshFinish();
    }

    @Override
    public void loadMoreFinish(List<VideoBean.VideoInfo> videoInfoList) {
        videoAdapter.addData( videoInfoList );
        recyclerview.setLoadMoreFinish();
    }

}
