package com.example.txl.gankio.change.mvp.data.source;

import android.support.annotation.NonNull;

import com.example.txl.gankio.change.mvp.data.User;

import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/31
 * description：
 */
public interface UserDataSource {
    interface LoadUsersCallback {

        void onUsersLoaded(List<User> users);

        void onDataNotAvailable();
    }

    interface GetUserCallback {

        void onUserLoaded(User user);

        void onDataNotAvailable();
    }

    void getUsers(@NonNull LoadUsersCallback callback);

    void getUser(@NonNull String userName, @NonNull GetUserCallback callback);

    void getUser(@NonNull String userName, @NonNull String userPassword, @NonNull GetUserCallback callback);

    void saveUser(@NonNull User user);

    void refreshUsers();

    void deleteAllUsers();

    void deleteUser(@NonNull String userName);
}
