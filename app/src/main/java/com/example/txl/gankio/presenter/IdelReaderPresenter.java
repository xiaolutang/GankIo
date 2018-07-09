package com.example.txl.gankio.presenter;

import android.content.Context;
import android.util.Log;

import com.example.txl.gankio.App;
import com.example.txl.gankio.api.GankIoApi;
import com.example.txl.gankio.base.BasePresenter;
import com.example.txl.gankio.bean.CommonIdelReaderSubclassification;
import com.example.txl.gankio.utils.StringUtils;
import com.example.txl.gankio.viewinterface.IGetIdelReadView;
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
public class IdelReaderPresenter extends BasePresenter {

    public IdelReaderPresenter(Context mContext) {
        super( mContext );
    }

    public void searchIreaderByCategory(IGetIdelReadView view, String category, boolean isLoadeMore){
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    public void getIdelReaderSubCategory(String type,IGetIdelReadView iGetIdelReadView){
        String url = GankIoApi.URL_GET_IDEL_SUB_CATEGORY +type;
        Log.d(TAG, "getIdelReaderSubCategory url : "+url);
        OkHttpClient okHttpClient = GankIoApi.getClient();
        final Request request = new Request.Builder()
                .cacheControl( GankIoApi.getDefaultCacheControl() )
                .url( url )
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "getIdelReaderSubCategory onFailure: ");
                iGetIdelReadView.onFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = StringUtils.replaceBlank( response.body().string());
                Log.d( TAG,"getIdelReaderSubCategory onResponse " +jsonString);
                Gson gson = new Gson();
                CommonIdelReaderSubclassification root = gson.fromJson( jsonString, CommonIdelReaderSubclassification.class);
                if(root.isError() || root.getResults() == null){
                    Log.e( TAG, "getIdelReaderSubCategory onResponse "+root.isError()+" root.getCategories() "+ root.getResults());
                    iGetIdelReadView.onFailed();
                    return;
                }
                App.getAppInstance().postToMainLooper( new Runnable() {
                    @Override
                    public void run() {
                        iGetIdelReadView.onUpdateSuccess( root );
                    }
                } );

            }
        });
    }
}
