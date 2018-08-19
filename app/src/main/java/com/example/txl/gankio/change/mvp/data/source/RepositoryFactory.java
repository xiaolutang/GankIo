package com.example.txl.gankio.change.mvp.data.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.txl.gankio.change.mvp.data.source.local.GankIoDatabase;
import com.example.txl.gankio.change.mvp.data.source.local.UserLocalDataSource;
import com.example.txl.gankio.change.mvp.data.source.local.WanAndroidBannerLocalDataSource;
import com.example.txl.gankio.change.mvp.data.source.remote.UserRemoteDataSource;
import com.example.txl.gankio.change.mvp.data.source.remote.WanAndroidBannerRemoteDataSource;
import com.example.txl.gankio.utils.AppExecutors;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/1
 * description：
 */
public class RepositoryFactory {
    private static GankIoDatabase gankIoDatabase;
    public static UserRepository provideUserRepository(@NonNull Context context) {
        if(null == context){
            throw new NullPointerException( "context is null" );
        }
        if(gankIoDatabase == null){
            gankIoDatabase = GankIoDatabase.getInstance(context);
        }
        return UserRepository.getInstance(new UserRemoteDataSource(),
                UserLocalDataSource.getInstance(new AppExecutors(),
                        gankIoDatabase.userDao()));
    }

    public static WanAndroidBannerRepository providerWanAndroidBannerRepository(Context context){
        if(gankIoDatabase == null){
            gankIoDatabase = GankIoDatabase.getInstance(context);
        }
        return WanAndroidBannerRepository.getInstance( WanAndroidBannerLocalDataSource.getInstance( new AppExecutors(),gankIoDatabase.bannerDao() ), WanAndroidBannerRemoteDataSource.getInstance());
    }
}
