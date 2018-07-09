package com.example.txl.gankio;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.txl.gankio.utils.ThemeUtils;
import java.util.logging.LogRecord;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public class App extends Application {
    private  static Context mContext;

    private static App _appInst;
    private static Handler _appMainHandler;

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
        mContext = getApplicationContext();
        _appMainHandler = new Handler(this.getMainLooper());
        ThemeUtils.init();
    }

    public void postToMainLooper(Runnable runnable) {
        _appMainHandler.post(runnable);
    }

    public void postDelayToMainLooper(Runnable runnable, long ms) {
        _appMainHandler.postDelayed(runnable, ms);
    }

    public void removeMainLooperCallBack(Runnable runnable) {
        _appMainHandler.removeCallbacks(runnable);
    }


}
