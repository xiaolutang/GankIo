package com.example.txl.gankio.viewimpl.alldata;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.example.txl.gankio.R;
import com.example.txl.gankio.adapter.AllDataAdapter;
import com.example.txl.gankio.adapter.VideoAdapter;
import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.gankio.presenter.VideoPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public class AllDataFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;

    AllDataAdapter allDataAdapter;

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
        allDataAdapter = new AllDataAdapter();
        recyclerview.setAdapter(allDataAdapter  );
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
    public void onRefresh() {

    }
}
