package com.example.txl.redesign.fragment.wanandroid;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.txl.redesign.data.XmlyFmData;
import com.example.txl.redesign.fragment.BaseRefreshFragment;

import java.util.List;

/**
 * @author TXL
 * description :
 */
public class WanAndroidFragment extends BaseRefreshFragment<WanAndroidAdapter,WanAndroidPresenter> implements WanAndroidContract.View<List<XmlyFmData>>{

    @Override
    protected WanAndroidAdapter getAdapter() {
        return new WanAndroidAdapter(getContext());
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager( getContext(),LinearLayoutManager.VERTICAL,false );
    }

    @Override
    protected WanAndroidPresenter getPresenter() {
        return new WanAndroidPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        rootView.setPadding(0,getStatusHeight(getContext()),0,0);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initData() {
        presenter.refresh();
    }

    @Override
    public void onRefreshSuccess(List<XmlyFmData> data) {
        adapter.addNewsData(data);
        closeLoadingView();
    }

    @Override
    public void onRefreshFailed() {
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMoreSuccess(List<XmlyFmData> data, boolean hasMore) {
        adapter.addNewsData(data);
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadMoreFailed() {
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    public void setPresenter(WanAndroidContract.Presenter presenter) {

    }

    @Override
    public void onBannerSuccess(XmlyFmData data) {
        adapter.addNewsData(0,data);
        closeLoadingView();
    }

    @Override
    public void onBannerFailed() {

    }
}
