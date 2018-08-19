package com.example.txl.gankio.change.mvp.data.source.local;

import android.support.annotation.NonNull;

import com.example.txl.gankio.change.mvp.data.User;
import com.example.txl.gankio.change.mvp.data.source.UserDataSource;
import com.example.txl.gankio.change.mvp.data.source.local.dao.UserDao;
import com.example.txl.gankio.utils.AppExecutors;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/31
 * description：
 */
public class UserLocalDataSource implements UserDataSource {

    private static volatile UserLocalDataSource INSTANCE;

    private UserDao mUserDao;

    private AppExecutors mAppExecutors;

    private UserLocalDataSource(@NonNull AppExecutors appExecutors,
                                @NonNull UserDao userDao){
        mAppExecutors = appExecutors;
        mUserDao = userDao;
    }

    public static UserLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                   @NonNull UserDao userDao) {
        if (INSTANCE == null) {
            synchronized (UserLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserLocalDataSource(appExecutors, userDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getUsers(@NonNull LoadUsersCallback callback) {

    }

    @Override
    public void getUser(@NonNull String userName, @NonNull GetUserCallback callback) {
        mAppExecutors.diskIO().execute( new Runnable() {
            @Override
            public void run() {
                User user = mUserDao.getUserByName( userName );
                mAppExecutors.mainThread().execute( new Runnable() {
                    @Override
                    public void run() {
                        if(user == null){
                            callback.onDataNotAvailable();
                            return;
                        }
                        callback.onUserLoaded( user );
                    }
                } );
            }
        } );
    }

    @Override
    public void getUser(@NonNull String userName, @NonNull String userPassword, @NonNull GetUserCallback callback) {
        mAppExecutors.diskIO().execute( new Runnable() {
            @Override
            public void run() {
                User user = mUserDao.LoginUserByName( userName,userPassword );
                mAppExecutors.mainThread().execute( new Runnable() {
                    @Override
                    public void run() {
                        if(user != null){
                            callback.onUserLoaded( user );
                        }else {
                            callback.onDataNotAvailable();
                        }
                    }
                } );
            }
        } );
    }

    @Override
    public void saveUser(@NonNull User user) {
        mAppExecutors.diskIO().execute( new Runnable() {
            @Override
            public void run() {
                mUserDao.insertUser( user );
            }
        } );
    }

    @Override
    public void refreshUsers() {

    }

    @Override
    public void deleteAllUsers() {

    }

    @Override
    public void deleteUser(@NonNull String userName) {

    }
}
