package com.example.txl.gankio.change.mvp.wan.android;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/19
 * description：为wan android准备的数据结构
 */
public interface IWanAndroidDataModel {
    public static final int TYPE_BANNER = 0;
    public static final int TYPE_ARTICLE = 1;

   int getDataModelType();
}
