package com.example.txl.gankio.change.mvp.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.txl.gankio.R;
import com.example.txl.gankio.adapter.VideoAdapter;
import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.gankio.change.mvp.data.VideoBean;
import com.example.txl.gankio.widget.PageScrollerRecyclerView;

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

    @BindView(R.id.RecyclerView)
    PageScrollerRecyclerView recyclerview;
    @BindView(R.id.fragment_video_swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;

    VideoAdapter videoAdapter;
    VideoContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d( TAG,"onCreateView" );
        View view = inflater.inflate( R.layout.fragment_video, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d( TAG,"onViewCreated" );
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
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d( TAG,"onStart" );
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d( TAG,"onResume" );
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d( TAG,"onPause" );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d( TAG,"onStop" );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d( TAG,"onDestroyView" );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d( TAG,"onDestroy" );
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
    }

    @Override
    public void loadMoreFinish(List<VideoBean.VideoInfo> videoInfoList) {
        videoAdapter.addData( videoInfoList );
    }

}
