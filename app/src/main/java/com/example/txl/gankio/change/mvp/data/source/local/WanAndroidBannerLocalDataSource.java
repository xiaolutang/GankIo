package com.example.txl.gankio.change.mvp.data.source.local;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.txl.gankio.change.mvp.data.WanAndroidBanner;
import com.example.txl.gankio.change.mvp.data.source.IWanAndroidBannerDataSource;
import com.example.txl.gankio.change.mvp.data.source.local.dao.WanAndroidBannerDao;
import com.example.txl.gankio.utils.AppExecutors;

import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/19
 * description：
 */
public class WanAndroidBannerLocalDataSource implements IWanAndroidBannerDataSource {
    private static final String TAG = WanAndroidBannerLocalDataSource.class.getSimpleName();


    private static volatile WanAndroidBannerLocalDataSource INSTANCE;

    private WanAndroidBannerDao mBannerDao;

    private AppExecutors mAppExecutors;

    public static WanAndroidBannerLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                              @NonNull WanAndroidBannerDao bannerDao) {
        if (INSTANCE == null) {
            synchronized (WanAndroidBannerLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WanAndroidBannerLocalDataSource(bannerDao, appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    private WanAndroidBannerLocalDataSource(WanAndroidBannerDao mBannerDao, AppExecutors mAppExecutors) {
        this.mBannerDao = mBannerDao;
        this.mAppExecutors = mAppExecutors;
    }

    @Override
    public void getBannerData(IBannerDataCallBack callBack) {
        mAppExecutors.diskIO().execute( new Runnable() {
            @Override
            public void run() {
                List<WanAndroidBanner.Data> dataList = mBannerDao.getDatas();
                if(callBack == null){
                    Log.w( "",TAG + "   getBannerData callBack is null" );
                    return;
                }
                mAppExecutors.mainThread().execute( new Runnable() {
                    @Override
                    public void run() {
                        if(null == dataList || dataList.size() == 0){
                            Log.w( TAG,TAG + "   getBannerData callBack dataList" +dataList);
                            callBack.onBannerDataLoadFailed();
                        }else {
                            WanAndroidBanner bannerData = new WanAndroidBanner();
                            bannerData.setData( dataList );
                            callBack.onBannerDataLoaded( bannerData );
                        }
                    }
                } );
            }
        } );
    }

    @Override
    public void deleteAllBannerData() {

    }
}
