package com.example.txl.gankio.base;

import android.content.Context;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public abstract class BasePresenter {
    protected String TAG = this.getClass().getSimpleName();
    public Context mContext;

    public BasePresenter(Context mContext) {
        this.mContext = mContext;
    }
}
