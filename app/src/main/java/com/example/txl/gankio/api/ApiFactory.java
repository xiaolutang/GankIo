package com.example.txl.gankio.api;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public class ApiFactory {

    protected static final Object monitor = new Object();
    static GankIoApi gankIoApi = null;


    //return Singleton
    public static GankIoApi getGankIoApiSingleton() {
        synchronized (monitor) {
            if (gankIoApi == null) {
                gankIoApi = new ApiRetrofit().getGankIoApiService();
            }
            return gankIoApi;
        }
    }
}
