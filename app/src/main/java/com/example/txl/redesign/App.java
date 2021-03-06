package com.example.txl.redesign;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.example.txl.gankio.cache.AppDataLoader;
import com.example.txl.gankio.change.mvp.data.User;
import com.example.txl.gankio.utils.ThemeUtils;
import com.example.txl.redesign.utils.imageutils.ImageLoader;
import com.example.txl.redesign.api.XmlyApi;
import com.example.txl.redesign.fragment.video.VideoIntentService;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public class App extends Application {
    private static User loginUser;

    private  static Context mContext;

    private static App _appInst;
    private static Handler _appMainHandler;
    private static ImageLoader mImageLoader;
    private static AppDataLoader mAppDataLoader;

    public App() {
        _appInst = this;
    }

    public static App getAppInstance() {
        return _appInst;
    }

    public static Context getAppContext(){
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //超越65535限制
        MultiDex.install(this);
        mContext = getApplicationContext();
        _appMainHandler = new Handler(this.getMainLooper());
        mImageLoader = ImageLoader.build( this );
        mAppDataLoader = AppDataLoader.build( this );
        XmlyApi.initXMFM(this);
        startVideoService();
        ThemeUtils.init();
    }

    private void startVideoService(){
        Intent intent = new Intent( this, VideoIntentService.class );
        startService( intent );
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        XmlyApi.destroyXmlyFm(this);
    }

    public static ImageLoader getImageLoader(){
        return mImageLoader;
    }

    public static AppDataLoader getAppDataLoader(){
        return mAppDataLoader;
    }

    public void postToMainLooper(Runnable runnable) {
        _appMainHandler.post(runnable);
    }

    public static void setLoginUser(User user){
        loginUser = user;
    }

    /**
     * 注销登录，销毁user对象
     * */
    public static void destoryLoginUser(){
        loginUser = null;
    }

    public static User getLoginUser(){
        return loginUser;
    }
}
