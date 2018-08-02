package com.example.txl.gankio.presenter;

import android.content.Context;
import android.util.Log;

import com.example.txl.gankio.App;
import com.example.txl.gankio.api.GankIoApi;
import com.example.txl.gankio.base.BasePresenter;

import com.example.txl.gankio.bean.IdelReaderCategoryRoot;
import com.example.txl.gankio.utils.StringUtils;
import com.example.txl.gankio.viewinterface.IGetMainDataView;
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
 * date：2018/7/6
 * description：
 */
public class MainPresenter extends BasePresenter {
    IGetMainDataView dataView;

    public MainPresenter(Context mContext) {
        super( mContext );
    }

    public void prepareMainData(IGetMainDataView dataView){
        this.dataView = dataView;
        getIdelReaderCategory();
    }

    public void getIdelReaderCategory(){
        OkHttpClient okHttpClient = GankIoApi.getClient();
        final Request request = new Request.Builder()
                .cacheControl( GankIoApi.getDefaultCacheControl() )
                .url( GankIoApi.URL_GET_IDEL_CATEGORY )
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "searchIdelReaderByCategory onFailure: ");
                dataView.getIdelReaderFailed( null );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = StringUtils.replaceBlank( response.body().string());
                Log.d( TAG,"getIdelReaderCategory onResponse " +jsonString);
                Gson gson = new Gson();
                IdelReaderCategoryRoot root = gson.fromJson( jsonString, IdelReaderCategoryRoot.class);
                if(root.isError() || root.getCategories() == null){
                    Log.e( TAG, "getIdelReaderCategory onResponse "+root.isError()+" root.getCategories() "+ root.getCategories());
                    dataView.getIdelReaderFailed( null );
                    return;
                }
                App.getAppInstance().postToMainLooper( new Runnable() {
                    @Override
                    public void run() {
                        dataView.getIdelReaderSuccess( root );
                    }
                } );

            }
        });
        okHttpClient.newCall(request).enqueue( new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e( TAG," 333333 getIdelReaderCategory onResponse 1=====" +response);
                Log.e( TAG," 333333 getIdelReaderCategory onResponse 2=====" +response.cacheResponse());
                Log.e( TAG," 333333getIdelReaderCategory onResponse 3=====" +response.networkResponse());
            }
        } );
    }
}
