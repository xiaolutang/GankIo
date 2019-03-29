package com.example.txl.gankio.change.mvp.data.source;

import android.util.Log;

import com.example.txl.redesign.data.wanandroid.WanAndroidBanner;
import com.example.txl.gankio.change.mvp.data.source.local.WanAndroidBannerLocalDataSource;
import com.example.txl.gankio.change.mvp.data.source.remote.WanAndroidBannerRemoteDataSource;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/19
 * description：
 */
public class WanAndroidBannerRepository implements IWanAndroidBannerDataSource{
    private static final String TAG = WanAndroidBannerRepository.class.getSimpleName();

    private WanAndroidBanner bannerData;

    private WanAndroidBannerLocalDataSource localDataSource;

    private WanAndroidBannerRemoteDataSource remoteDataSource;

    private static WanAndroidBannerRepository INSTANCE;

    public static WanAndroidBannerRepository getInstance(WanAndroidBannerLocalDataSource localDataSource, WanAndroidBannerRemoteDataSource remoteDataSource){
        if(INSTANCE != null){
            return INSTANCE;
        }
        INSTANCE = new WanAndroidBannerRepository( localDataSource,remoteDataSource );
        return INSTANCE;
    }

    private WanAndroidBannerRepository(WanAndroidBannerLocalDataSource localDataSource, WanAndroidBannerRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void getBannerData(IBannerDataCallBack callBack) {
        if(bannerData!=null){
            callBack.onBannerDataLoaded( bannerData );
            return;
        }
        getBannerFromLocal(callBack);
    }

    private void getBannerFromLocal(IBannerDataCallBack callBack){
        Log.d( TAG,"getBannerFromLocal" );
        localDataSource.getBannerData( new IBannerDataCallBack() {
            @Override
            public void onBannerDataLoaded(WanAndroidBanner banner) {
                WanAndroidBannerRepository.this.bannerData = banner;

                callBack.onBannerDataLoaded( banner );
            }

            @Override
            public void onBannerDataLoadFailed() {
                getBannerFromRemote(callBack);
            }
        } );
    }

    private void getBannerFromRemote(IBannerDataCallBack callBack){
        Log.d( TAG,"getBannerFromRemote" );
        remoteDataSource.getBannerData( new IBannerDataCallBack() {
            @Override
            public void onBannerDataLoaded(WanAndroidBanner banner) {
                WanAndroidBannerRepository.this.bannerData = banner;
                callBack.onBannerDataLoaded( banner );
            }

            @Override
            public void onBannerDataLoadFailed() {
                callBack.onBannerDataLoadFailed();
            }
        } );
    }



    @Override
    public void deleteAllBannerData() {

    }
}
