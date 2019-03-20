package com.example.txl.gankio.change.mvp.data.source.remote;

import android.util.Log;

import com.example.txl.redesign.api.ApiFactory;
import com.example.txl.gankio.change.mvp.data.WanAndroidBanner;
import com.example.txl.gankio.change.mvp.data.source.IWanAndroidBannerDataSource;
import com.example.txl.gankio.utils.AppExecutors;
import com.example.txl.gankio.utils.StringUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/19
 * description：
 */
public class WanAndroidBannerRemoteDataSource implements IWanAndroidBannerDataSource {
    private static final String TAG = WanAndroidBannerRemoteDataSource.class.getSimpleName();

    private AppExecutors mAppExecutors;
    private static  WanAndroidBannerRemoteDataSource instance;
    public static WanAndroidBannerRemoteDataSource getInstance(){
        if(instance == null){
            instance = new WanAndroidBannerRemoteDataSource();
        }
        return instance;
    }

    private WanAndroidBannerRemoteDataSource() {
        this.mAppExecutors = new AppExecutors();
    }

    @Override
    public void getBannerData(IBannerDataCallBack callBack) {
        String url = ApiFactory.WAN_ANDROID_GET_BANNER;
        OkHttpClient client = ApiFactory.getClient();
        final Request request = new Request.Builder()
                .cacheControl( ApiFactory.getDefaultCacheControl() )
                .url( url)
                .get()
                .build();
        Call call = client.newCall( request );
        call.enqueue( new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d( TAG,"getBannerData onFailure" );
                callBack.onBannerDataLoadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d( TAG,"getBannerData onResponse" );
                String jsonString = StringUtils.replaceBlank( response.body().string());
                Gson gson = new Gson();
                WanAndroidBanner banner = gson.fromJson( jsonString, WanAndroidBanner.class);
                mAppExecutors.mainThread().execute( new Runnable() {
                    @Override
                    public void run() {
                        callBack.onBannerDataLoaded( banner );
                    }
                } );
            }
        } );
    }

    @Override
    public void deleteAllBannerData() {

    }
}
