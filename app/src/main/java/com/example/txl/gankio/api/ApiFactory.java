package com.example.txl.gankio.api;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public class ApiFactory {

    public static final String URL_GET_IDEL_CATEGORY = "http://gank.io/api/xiandu/categories";
    public static final String URL_GET_IDEL_SUB_CATEGORY = "http://gank.io/api/xiandu/category/";
    public static final String URL_GET_IDEL_DATA = "http://gank.io/api/xiandu/data/";
    /**获取福利*/
    public static final String URL_GET_FULI_DATA = "http://gank.io/api/data/福利/";

    /**获取休息视频*/
    public static final String URL_GET_VIDEO_DATA = "http://gank.io/api/data/休息视频/";


    /**
     * 以下是 wanAndroid 的api接口
     * */

    /**获取首页轮播*/
    public static final String WAN_ANDROID_GET_BANNER = "http://www.wanandroid.com/banner/json";

    /**获取首页文章列表*/
    public static final String WAN_ANDROID_GET_ARTICEL_LIST = "http://www.wanandroid.com/article/list";

    /**自己服务器API接口*/
    public static final String GANK_IO_UPDATE_APK = "http://192.168.31.92/web/update/aweme_aweGW_v2.3.0_1aac92c.apk";

    public static final String GANK_IO_CHECK_UPDATE = "http://192.168.31.92/web/checkUpdate";

    private static final OkHttpClient client;

    private static final long cacheSize = 1024 * 1024 * 20;// 缓存文件最大限制大小20M
    private static String cacheDirectory = Environment.getExternalStorageDirectory() + "/okttpcaches"; // 设置缓存文件路径
    private static Cache cache = new Cache(new File(cacheDirectory), cacheSize);  //
    private static CacheControl cacheControl;

    static {
        //如果无法生存缓存文件目录，检测权限使用已经加上，检测手机是否把文件读写权限禁止了
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout( 8, TimeUnit.SECONDS ); // 设置连接超时时间
        builder.writeTimeout( 8, TimeUnit.SECONDS );// 设置写入超时时间
        builder.readTimeout( 8, TimeUnit.SECONDS );// 设置读取数据超时时间
        builder.retryOnConnectionFailure( true );// 设置进行连接失败重试
        builder.cache( cache );// 设置缓存
        client = builder.build();
//        maxAge:没有超出maxAge,不管怎么样都是返回缓存数据，超过了maxAge,发起新的请求获取数据更新，请求失败返回缓存数据。
//        maxStale:没有超过maxStale，不管怎么样都返回缓存数据，超过了maxStale,发起请求获取更新数据，请求失败返回失败
        cacheControl = new CacheControl.Builder().maxAge( 1 ,TimeUnit.SECONDS).maxStale(1, TimeUnit.DAYS)
                .build();
    }

    public static OkHttpClient getClient(){
        Log.e( "OkHttpClient","cacheDirectory = "+cacheDirectory );
        return client;
    }

    public static CacheControl getDefaultCacheControl(){
        return cacheControl;
    }
}
