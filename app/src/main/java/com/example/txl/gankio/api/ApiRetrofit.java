package com.example.txl.gankio.api;

import com.example.txl.gankio.App;
import com.example.txl.gankio.utils.NetUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public class ApiRetrofit {

    public GankIoApi gankIoApiService;

    public static final String gankIoBaseUrl = "http://gank.io/api/";

    public GankIoApi getGankIoApiService(){
        return gankIoApiService;
    }

    ApiRetrofit() {
        //cache url
        File httpCacheDirectory = new File( App.getAppContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache)
                .build();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(gankIoBaseUrl)
//                .client(client)
//                .addConverterFactory( GsonConverterFactory.create())
//                .addCallAdapterFactory( RxJavaCallAdapterFactory.create())
//                .build();
//
//
//        gankIoApiService = retrofit.create(GankIoApi.class);

    }

    Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {

        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
        cacheBuilder.maxStale(365, TimeUnit.DAYS);
        CacheControl cacheControl = cacheBuilder.build();

        Request request = chain.request();
        if (!NetUtils.checkNetWorkIsAvailable(App.getAppContext())) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();

        }
        Response originalResponse = chain.proceed(request);
        if (NetUtils.checkNetWorkIsAvailable(App.getAppContext())) {
            int maxAge = 0; // read from cache
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };
}
