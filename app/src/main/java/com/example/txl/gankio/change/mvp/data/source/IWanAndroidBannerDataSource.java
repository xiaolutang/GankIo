package com.example.txl.gankio.change.mvp.data.source;

import com.example.txl.redesign.data.wanandroid.WanAndroidBanner;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/19
 * description：
 */
public interface IWanAndroidBannerDataSource {
    interface IBannerDataCallBack{
        void onBannerDataLoaded(WanAndroidBanner banner);
        void onBannerDataLoadFailed();
    }

    void getBannerData(IBannerDataCallBack callBack);

    void deleteAllBannerData();
}
