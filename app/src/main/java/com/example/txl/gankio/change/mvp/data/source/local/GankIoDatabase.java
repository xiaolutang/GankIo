package com.example.txl.gankio.change.mvp.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.txl.gankio.change.mvp.data.User;
import com.example.txl.redesign.data.wanandroid.WanAndroidBanner;
import com.example.txl.gankio.change.mvp.data.source.local.dao.UserDao;
import com.example.txl.gankio.change.mvp.data.source.local.dao.WanAndroidBannerDao;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/31
 * description：
 */
@Database( entities = {User.class, WanAndroidBanner.Data.class}, version = 1,exportSchema = false)
public abstract class GankIoDatabase extends RoomDatabase {
    private static GankIoDatabase INSTANCE;

    public abstract UserDao userDao();

    public abstract WanAndroidBannerDao bannerDao();

    private static final Object sLock = new Object();

    public static GankIoDatabase getInstance(Context context){
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        GankIoDatabase.class, "GankIo.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}
