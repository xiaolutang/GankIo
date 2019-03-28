package com.example.txl.redesign.fm;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.txl.redesign.fragment.BaseRefreshFragment;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/27
 * description：
 */
public class FmFragment extends BaseRefreshFragment<FMAdapter,FmPresenter> implements FmContract.View<AlbumList>{
    private boolean loadCategoryError = false;
    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    protected FMAdapter getAdapter() {
        return new FMAdapter( getContext() );
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager( getContext(),LinearLayoutManager.VERTICAL,false );
    }

    @Override
    protected FmPresenter getPresenter() {
        return new FmPresenter(this);
    }



    @Override
    protected void initData() {
        presenter.getFmCategory();
    }

    @Override
    protected void initView() {
        super.initView();
        rootView.setPadding(0,getStatusHeight(getContext()),0,0);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onCategorySuccess(CategoryList categoryList) {
        loadCategoryError = false;
        List<XmlyFmData> xmlyFmData = new ArrayList<>();
        XmlyFmData xmlyFmData1 = new XmlyFmData(XmlyFmData.TYPE_CATEGORY_LIST);
        xmlyFmData1.setCategoryList(categoryList);
        xmlyFmData.add(xmlyFmData1);
        adapter.addNewsData( xmlyFmData);
        closeLoadingView();
    }

    @Override
    public void onCategoryFailed() {
        loadCategoryError = true;
        Toast.makeText( getContext(), "喜马拉雅音乐栏目加载出错！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefreshSuccess(AlbumList albumList) {
        List<XmlyFmData> list = new ArrayList<>(  );
        if(albumList != null){
            for (Album album:albumList.getAlbums()){
                XmlyFmData xmlyFmData = new XmlyFmData( XmlyFmData.TYPE_ALBUN_ITEM );
                xmlyFmData.setAlbum( album );
                list.add( xmlyFmData );
            }
        }
        adapter.addNewsData( list );
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void onRefreshFailed() {
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMoreSuccess(AlbumList albumList,boolean hasMore) {
        List<XmlyFmData> list = new ArrayList<>(  );
        if(albumList != null){
            for (Album album:albumList.getAlbums()){
                XmlyFmData xmlyFmData = new XmlyFmData( XmlyFmData.TYPE_ALBUN_ITEM );
                xmlyFmData.setAlbum( album );
                list.add( xmlyFmData );
            }
        }
        adapter.addNewsData( list );
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadMoreFailed() {
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    public void setPresenter(FmContract.Presenter presenter) {

    }

    @Override
    public void onRefresh() {
        if(loadCategoryError){
            presenter.getFmCategory();
        }else {
            presenter.refresh();
        }
    }

    @Override
    public void onLoadMore() {
        presenter.loadMore();
    }

}
