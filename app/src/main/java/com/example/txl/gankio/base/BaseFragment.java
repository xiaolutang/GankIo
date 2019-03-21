package com.example.txl.gankio.base;

import android.support.v4.app.Fragment;

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
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected abstract String getFragmentName();

}
