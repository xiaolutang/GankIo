package com.example.txl.gankio.presenter;

import android.content.Context;
import android.util.Log;

import com.example.txl.redesign.App;
import com.example.txl.redesign.api.ApiFactory;
import com.example.txl.gankio.base.BasePresenter;
import com.example.txl.gankio.bean.BeautyGirls;
import com.example.txl.gankio.utils.StringUtils;
import com.example.txl.gankio.viewinterface.IGetFuLiData;
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
 * date：2018/7/8
 * description：
 */
public class FuLiPresenter extends BasePresenter {

    public FuLiPresenter(Context mContext) {
        super( mContext );
    }

    public void getFuLiData(int count, int page, IGetFuLiData iGetFuLiData, boolean refresh){
        String url = ApiFactory.URL_GET_FULI_DATA +""+count+"/"+page;
        Log.d(TAG, "getFuLiData url : "+url);
        OkHttpClient okHttpClient = ApiFactory.getClient();
        final Request request = new Request.Builder()
                .cacheControl( ApiFactory.getDefaultCacheControl() )
                .url( url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "getFuLiData onFailure: ");
                if(refresh){
                    iGetFuLiData.updateFuLiDataFailed();
                }else {
                    iGetFuLiData.onAddFuLiDataFailed();
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = StringUtils.replaceBlank( response.body().string());
                Log.d( TAG,"getFuLiData onResponse " +jsonString);
                Gson gson = new Gson();
                BeautyGirls root = gson.fromJson( jsonString, BeautyGirls.class);

                App.getAppInstance().postToMainLooper( new Runnable() {
                    @Override
                    public void run() {
                        if(root.isError() || root.getResults() == null){
                            Log.e( TAG, "getFuLiData onResponse "+root.isError()+" root.getCategories() "+ root.getResults());
                            if(refresh){
                                iGetFuLiData.updateFuLiDataFailed();
                            }else {
                                iGetFuLiData.onAddFuLiDataFailed();
                            }
                            return;
                        }
                        if(refresh){
                            iGetFuLiData.updateFuLiDataSuccess(root.getResults());
                        }else {
                            iGetFuLiData.onAddFuLiDataSuccess(root.getResults());
                        }
                    }
                } );

            }
        });
    }
}
