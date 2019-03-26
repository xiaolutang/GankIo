package com.example.txl.redesign.fragment.fuli;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.txl.redesign.adpter.BaseNewsAdapter;
import com.example.txl.redesign.adpter.FuLiAdapter;
import com.example.txl.redesign.fragment.BaseNewsFragment;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/25
 * description：
 */
public class FuliFragment extends BaseNewsFragment {

    @Override
    protected void initView() {
        super.initView();
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null);
    }

    @Override
    protected BaseNewsAdapter getAdapter() {
        return new FuLiAdapter( getContext() );
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return  new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }
}
