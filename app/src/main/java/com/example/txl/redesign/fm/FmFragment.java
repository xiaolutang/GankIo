package com.example.txl.redesign.fm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.txl.gankio.R;
import com.example.txl.redesign.fragment.BaseRefreshFragment;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/27
 * description：
 */
public class FmFragment extends BaseRefreshFragment<FMAdapter,FmPresenter> implements FmContract.View{
    private boolean loadCategoryError = false;
    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate( R.layout.fragment_base_refresh,container,false);
        return rootView;
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
    public void onCategorySuccess(CategoryList categoryList) {
        loadCategoryError = false;
        List<CategoryList> categoryLists = new ArrayList<>();
        categoryLists.add(categoryList);
        adapter.addNewsData( categoryLists);
    }

    @Override
    public void onCategoryFailed() {
        loadCategoryError = true;
        Toast.makeText( getContext(), "喜马拉雅音乐栏目加载出错！",Toast.LENGTH_SHORT).show();
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