package com.example.txl.gankio.change.mvp.wan.android;

import android.util.Log;

import redesign.api.ApiFactory;
import com.example.txl.gankio.change.mvp.data.ArticleList;
import com.example.txl.gankio.change.mvp.data.WanAndroidBanner;
import com.example.txl.gankio.change.mvp.data.source.IWanAndroidBannerDataSource;
import com.example.txl.gankio.change.mvp.data.source.WanAndroidBannerRepository;
import com.example.txl.gankio.utils.AppExecutors;
import com.example.txl.gankio.utils.StringUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/18
 * description：
 */
public class WanAndroidPresenter implements WanAndroidContract.Presenter {
    private final static String TAG = WanAndroidPresenter.class.getSimpleName();
    private int currentPageIndex = 0;

    private WanAndroidContract.View mView;
    private final WanAndroidBannerRepository repository;
    private AppExecutors mAppExecutors;

    public WanAndroidPresenter(WanAndroidContract.View mView, WanAndroidBannerRepository repository) {
        this.mView = mView;
        this.repository = repository;
        mAppExecutors = new AppExecutors();
    }

    @Override
    public void loadMore() {
        currentPageIndex++;
        String url = ApiFactory.WAN_ANDROID_GET_ARTICEL_LIST+"/"+currentPageIndex+"/json";
        OkHttpClient okHttpClient = ApiFactory.getClient();
        final Request request = new Request.Builder()
                .cacheControl( ApiFactory.getDefaultCacheControl() )
                .url( url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue( new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = StringUtils.replaceBlank( response.body().string());
                Log.d( TAG,"getFuLiData onResponse " +url);
                Gson gson = new Gson();
                ArticleList root = gson.fromJson( jsonString, ArticleList.class);
                mAppExecutors.mainThread().execute( new Runnable() {
                    @Override
                    public void run() {
                        List<IDataModel> list = new ArrayList<>(  );
                        list.addAll( root.getData().getDatas() );
                        mView.loadMoreFinish( list );
                    }
                } );
            }
        } );
    }

    @Override
    public void loadBanner() {
        repository.getBannerData( new IWanAndroidBannerDataSource.IBannerDataCallBack() {
            @Override
            public void onBannerDataLoaded(WanAndroidBanner bannerData) {
                List<IDataModel> list = new ArrayList<>(  );
                list.add( bannerData );
                mView.loadBannerFinish(list);
            }

            @Override
            public void onBannerDataLoadFailed() {
            }
        } );
    }

    @Override
    public void refresh() {
        //重新获取首页内容
        currentPageIndex = 0;
        String url = ApiFactory.WAN_ANDROID_GET_ARTICEL_LIST+"/"+currentPageIndex+"/json";
        OkHttpClient okHttpClient = ApiFactory.getClient();
        final Request request = new Request.Builder()
                .cacheControl( ApiFactory.getDefaultCacheControl() )
                .url( url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue( new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = StringUtils.replaceBlank( response.body().string());
                Log.d( TAG,"getFuLiData onResponse " +url);
                Gson gson = new Gson();
                ArticleList root = gson.fromJson( jsonString, ArticleList.class);
                mAppExecutors.mainThread().execute( new Runnable() {
                    @Override
                    public void run() {
                        List<IDataModel> list = new ArrayList<>(  );
                        list.addAll( root.getData().getDatas() );
                        mView.loadMoreFinish( list );
                    }
                } );
            }
        } );
    }

    @Override
    public void start() {
        loadBanner();
        refresh();
    }


}
