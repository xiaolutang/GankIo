package com.example.txl.gankio.change.mvp.data.source.remote;

import android.support.annotation.NonNull;

import com.example.txl.gankio.change.mvp.data.User;
import com.example.txl.gankio.change.mvp.data.source.UserDataSource;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/31
 * description：
 */
public class UserRemoteDataSource implements UserDataSource {
    @Override
    public void getUsers(@NonNull LoadUsersCallback callback) {

    }

    @Override
    public void getUser(@NonNull String userName, @NonNull GetUserCallback callback) {

    }

    @Override
    public void getUser(@NonNull String userName, @NonNull String userPassword, @NonNull GetUserCallback callback) {

    }

    @Override
    public void saveUser(@NonNull User user) {

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
