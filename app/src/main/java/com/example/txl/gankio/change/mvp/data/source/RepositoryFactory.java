package com.example.txl.gankio.change.mvp.data.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.txl.gankio.change.mvp.data.source.local.GankIoDatabase;
import com.example.txl.gankio.change.mvp.data.source.local.UserLocalDataSource;
import com.example.txl.gankio.change.mvp.data.source.remote.UserRemoteDataSource;
import com.example.txl.gankio.utils.AppExecutors;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/1
 * description：
 */
public class RepositoryFactory {
    public static UserRepository provideUserRepository(@NonNull Context context) {
        if(null == context){
            throw new NullPointerException( "context is null" );
        }
        GankIoDatabase database = GankIoDatabase.getInstance(context);
        return UserRepository.getInstance(new UserRemoteDataSource(),
                UserLocalDataSource.getInstance(new AppExecutors(),
                        database.userDao()));
    }
}
