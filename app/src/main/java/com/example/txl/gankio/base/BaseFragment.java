package com.example.txl.gankio.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public abstract class BaseFragment extends Fragment implements IBaseView{
    protected String TAG = this.getClass().getSimpleName();

    protected String fragmentName;

    /**
     * 根布局
     * */
    protected View rootView;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected abstract String getFragmentName();

    protected Bundle getFragmentArguments()
    {
        return getArguments();
    }

}
