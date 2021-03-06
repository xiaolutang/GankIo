package com.example.txl.gankio.change.mvp.wan.android;

import com.example.txl.gankio.change.mvp.BasePresenter;
import com.example.txl.gankio.change.mvp.BaseView;
import com.example.txl.gankio.change.mvp.login.LoginContract;

import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/18
 * description：
 */
public class WanAndroidContract {
    interface View extends BaseView<LoginContract.Presenter> {
        void loadMoreFinish(List<IWanAndroidDataModel> iWanAndroidDataModels);
        void loadMoreFailed();
        void loadBannerFinish(List<IWanAndroidDataModel> iWanAndroidDataModels);
    }

    interface Presenter extends BasePresenter {
        void loadMore();
        void loadBanner();
        void refresh();
    }
}
