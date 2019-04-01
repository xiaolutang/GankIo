package com.example.txl.redesign.fragment.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.txl.gankio.R;
import com.example.txl.gankio.adapter.VideoAdapter;
import com.example.txl.redesign.data.VideoBean;
import com.example.txl.redesign.fragment.BaseRefreshFragment;
import com.example.txl.redesign.player.SimpleAndroidPlayer;
import com.example.txl.redesign.player.SimpleAndroidPlayerManager;

import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public class VideoFragment extends BaseRefreshFragment<VideoAdapter,VideoContract.Presenter> implements VideoContract.View<List<VideoBean.VideoInfo>> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d( TAG,"onCreateView" );
        rootView = inflater.inflate( R.layout.fragment_video_refresh, container, false);
        return rootView;
    }

    @Override
    protected VideoAdapter getAdapter() {
        return new VideoAdapter(getContext());
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Override
    protected VideoContract.Presenter getPresenter() {
        return new VideoPresenter( getContext(),this );
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
        stopPlay();
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
        presenter.destroy();
        super.onDestroy();
        Log.d( TAG,"onDestroy" );
    }

    protected void initData(){
        presenter.start();
    }

    @Override
    public String getFragmentName() {
        return "所有数据";
    }

    @Override
    public void setPresenter(VideoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void stopPlay(){
        try{
            SimpleAndroidPlayer simpleAndroidPlayer = SimpleAndroidPlayerManager.getCurrentSimpleAndroidPlayer();
            simpleAndroidPlayer.destroy();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRefreshSuccess(List<VideoBean.VideoInfo> data) {
        adapter.setNewsData( data );
        smartRefreshLayout.finishRefresh();
        closeLoadingView();
    }

    @Override
    public void onRefreshFailed() {
        smartRefreshLayout.finishRefresh();
        closeLoadingView();
    }

    @Override
    public void onLoadMoreSuccess(List<VideoBean.VideoInfo> data, boolean hasMore) {
        adapter.addNewsData(data );
        smartRefreshLayout.finishLoadMore();
        closeLoadingView();
    }

    @Override
    public void onLoadMoreFailed() {
        smartRefreshLayout.finishLoadMore();
        closeLoadingView();
    }
}
