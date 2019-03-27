package com.example.txl.redesign.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.redesign.IRefreshPresenter;
import com.example.txl.redesign.adpter.BaseAdapter;
import com.example.txl.redesign.widget.GankSmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/27
 * description：
 */
public abstract class BaseRefreshFragment<A extends BaseAdapter,T extends IRefreshPresenter> extends BaseFragment {
    protected GankSmartRefreshLayout smartRefreshLayout;
    protected RecyclerView recyclerView;
    protected ViewGroup loadingView;

    protected T presenter;
    protected A adapter;

    @Override
    protected String getFragmentName() {
        return TAG;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_base_news,container,false);
        return rootView;
    }

    @Override
    protected void initView() {
        loadingView = rootView.findViewById( R.id.loading_root );
        smartRefreshLayout = rootView.findViewById( R.id.smart_refresh_layout );
        presenter = getPresenter();
        presenter.start();
        recyclerView = rootView.findViewById( R.id.recycler_view );
        RecyclerView.LayoutManager linearLayoutManager = getLayoutManager();
        recyclerView.setLayoutManager( linearLayoutManager );
        adapter = getAdapter();
        recyclerView.setAdapter( adapter );
        smartRefreshLayout.setOnRefreshListener( new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.refresh();
            }
        } );
        smartRefreshLayout.setOnLoadMoreListener( new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadMore();
            }
        } );
    }

    protected abstract A getAdapter();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract T getPresenter();

    protected void showLoadingView(){
        loadingView.setVisibility( View.VISIBLE );
    }

    protected void closeLoadingView(){
        loadingView.setVisibility( View.GONE );
    }

}
