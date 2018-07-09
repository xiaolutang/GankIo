package com.example.txl.gankio.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.txl.gankio.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public abstract class BaseFragment extends Fragment implements IBaseView{
    protected String TAG = this.getClass().getSimpleName();

    protected String fragmentName;

    @Override
    public void onResume() {
        super.onResume();
        if(!hasMultiFragment()) {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(!hasMultiFragment()) {
        }
    }

    public abstract boolean hasMultiFragment();

    protected abstract String getFragmentName();

}
