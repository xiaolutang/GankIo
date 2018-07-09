package com.example.txl.gankio.presenter;

import android.content.Context;
import android.util.Log;

import com.example.txl.gankio.App;
import com.example.txl.gankio.api.GankIoApi;
import com.example.txl.gankio.base.BasePresenter;
import com.example.txl.gankio.bean.CommonIdelReaderSubclassification;
import com.example.txl.gankio.bean.IdelInfo;
import com.example.txl.gankio.utils.StringUtils;
import com.example.txl.gankio.viewinterface.IGetIdelInfo;
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
 * date：2018/7/7
 * description：
 */
public class IdelInfoPersenter extends BasePresenter {

    public IdelInfoPersenter(Context mContext) {
        super( mContext );
    }

    /**
     * @param id 请求闲读数据对应数据源id
     * @param page 第几页数据
     * @param count 每页多少个
     * */
    public void getIdelReaderSubCategory(String id,int count, int page, IGetIdelInfo iGetIdelReadView, boolean refresh){
        String url = GankIoApi.URL_GET_IDEL_DATA +"id/"+id+"/count/"+count+"/page/"+page;
        Log.d(TAG, "getIdelReaderSubCategory url : "+url);
        OkHttpClient okHttpClient = GankIoApi.getClient();
        final Request request = new Request.Builder()
                .cacheControl( GankIoApi.getDefaultCacheControl() )
                .url( url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "getIdelReaderSubCategory onFailure: ");
                if(refresh){
                    iGetIdelReadView.updateIdelInfoFailed();
                }else {
                    iGetIdelReadView.onAddIdelInfoFailed();
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = StringUtils.replaceBlank( response.body().string());
                Log.d( TAG,"getIdelReaderSubCategory onResponse " +jsonString);
                Gson gson = new Gson();
                IdelInfo root = gson.fromJson( jsonString, IdelInfo.class);

                App.getAppInstance().postToMainLooper( new Runnable() {
                    @Override
                    public void run() {
                        if(root.isError() || root.getResults() == null){
                            Log.e( TAG, "getIdelReaderSubCategory onResponse "+root.isError()+" root.getCategories() "+ root.getResults());
                            if(refresh){
                                iGetIdelReadView.updateIdelInfoFailed();
                            }else {
                                iGetIdelReadView.onAddIdelInfoFailed();
                            }
                            return;
                        }
                        if(refresh){
                            iGetIdelReadView.updateIdelInfoSuccess(root.getResults());
                        }else {
                            iGetIdelReadView.onAddIdelInfoSuccess(root.getResults());
                        }
                    }
                } );

            }
        });
    }
}
