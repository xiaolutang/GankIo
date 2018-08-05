package com.example.txl.gankio.viewimpl.idelread;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.txl.gankio.R;
import com.example.txl.gankio.adapter.CommonSubIdelReaderAdapter;
import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.gankio.bean.CommonIdelReaderSubclassification;
import com.example.txl.gankio.bean.IdelReaderCategoryRoot;
import com.example.txl.gankio.presenter.IdelReaderPresenter;
import com.example.txl.gankio.viewinterface.IGetIdelReadView;
import com.example.txl.gankio.widget.PullRefreshRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public class CommonSubIdelFragment extends BaseFragment implements IGetIdelReadView,SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerview)
    PullRefreshRecyclerView recyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    IdelReaderPresenter idelReaderPresenter;

    CommonSubIdelReaderAdapter adapter;
    IdelReaderCategoryRoot.IdelReaderCategory category;

    public static CommonSubIdelFragment newInsatance(IdelReaderCategoryRoot.IdelReaderCategory category){
        CommonSubIdelFragment subIdelFragment = new CommonSubIdelFragment();
        subIdelFragment.fragmentName = category.getName();
        subIdelFragment.category = category;
        return subIdelFragment;
    }

    public CommonSubIdelFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d( TAG, "onCreate");
        super.onCreate( savedInstanceState );
        fragmentName = getArguments() != null ? getArguments().getString("fragmentName") : "违背";
        Log.d( TAG, "onCreate  "+fragmentName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d( TAG, "onCreateView");
        View view = inflater.inflate( R.layout.model_swiperefreshlayout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        initView();
        fragmentName = getArguments() != null ? getArguments().getString("fragmentName") : "aaa";
    }

    @Override
    public boolean hasMultiFragment() {
        return false;
    }

    @Override
    protected String getFragmentName() {
        Log.d( TAG, "getFragmentName  "+fragmentName);
        return fragmentName;
    }

    public void setFragmentname(String fragmentname){
        this.fragmentName = fragmentname;
    }

    private void initView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL);
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(getActivity(),R.drawable.list_item_divider);
        decoration.setDrawable( drawable );
        recyclerview.addItemDecoration( new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL) );
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter( adapter );
        recyclerview.setEnablePullRefresh( false );
        idelReaderPresenter = new IdelReaderPresenter( getContext() );
        idelReaderPresenter.getIdelReaderSubCategory(category.getEn_name(),this  );
        swiperefreshlayout.setOnRefreshListener( this );
    }

    public void setRecyclerViewAdapter(CommonSubIdelReaderAdapter adapter){
        this.adapter = adapter;
        if(recyclerview != null){
            recyclerview.setAdapter( adapter );
        }
    }


    @Override
    public void onUpdateSuccess(CommonIdelReaderSubclassification subclassification) {
        adapter.updateContent( subclassification.getResults() );
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed() {

    }

    @Override
    public void loadDataSuccess(CommonIdelReaderSubclassification subclassification) {
        int start = adapter.getItemCount() - 1;
        adapter.addContent( subclassification.getResults());
        adapter.notifyItemRangeInserted(start,subclassification.getResults().size());
    }

    @Override
    public void onRefresh() {
        swiperefreshlayout.setRefreshing( false );
    }
}
