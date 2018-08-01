package com.example.txl.gankio.change.mvp.data.source;

import android.support.annotation.NonNull;

import com.example.txl.gankio.change.mvp.data.User;
import com.example.txl.gankio.utils.ObjectUtils;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/31
 * description：
 */
public class UserRepository implements UserDataSource {

    private static UserRepository INSTANCE = null;

    private final UserDataSource mUserRemoteDataSource;

    private final UserDataSource mUserLocalDataSource;

    public static UserRepository getInstance(UserDataSource userRemoteDataSource,UserDataSource userLocalDataSource) {
        if(INSTANCE == null){
            INSTANCE = new UserRepository(userRemoteDataSource,userLocalDataSource);
        }
        return INSTANCE;
    }

    public UserRepository(UserDataSource mUserRemoteDataSource, UserDataSource mUserLocalDataSource) {
        this.mUserRemoteDataSource = mUserRemoteDataSource;
        this.mUserLocalDataSource = mUserLocalDataSource;
    }

    @Override
    public void getUsers(@NonNull LoadUsersCallback callback) {
        ObjectUtils.checkNotNull(callback);
    }

    @Override
    public void getUser(@NonNull String userName, @NonNull GetUserCallback callback) {
        mUserLocalDataSource.getUser( userName,callback );
    }

    @Override
    public void getUser(@NonNull String userName, @NonNull String userPassword, @NonNull GetUserCallback callback) {
        mUserLocalDataSource.getUser( userName,userPassword,callback );
    }

    @Override
    public void saveUser(@NonNull User user) {
        ObjectUtils.checkNotNull(user);
        mUserRemoteDataSource.saveUser( user );
        mUserLocalDataSource.saveUser( user );
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
