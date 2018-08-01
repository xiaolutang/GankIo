package com.example.txl.gankio.change.mvp.login;

import com.example.txl.gankio.change.mvp.data.User;
import com.example.txl.gankio.change.mvp.data.source.UserDataSource;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/1
 * description：
 */
public class LoginPresenter implements LoginContract.Presenter,UserDataSource.GetUserCallback{

    private final UserDataSource mUserRepository;

    private final LoginContract.View mLoginView;

    public LoginPresenter(UserDataSource userRepository, LoginContract.View loginView, boolean shouldLoadDataFromRemote) {
        mUserRepository = userRepository;
        mLoginView = loginView;
        mLoginView.setPresenter( this );
    }

    @Override
    public void registerUser(String userName, String password) {
        User user = new User( userName,password );
        mUserRepository.getUser( userName, new UserDataSource.GetUserCallback(){

            @Override
            public void onUserLoaded(User user) {
                mLoginView.showUserExit(user);
            }

            @Override
            public void onDataNotAvailable() {
                mUserRepository.saveUser( user );
                mLoginView.registerSuccess();
            }
        } );
    }

    @Override
    public void login(String userName, String password) {
        mUserRepository.getUser( userName,password,this );
    }

    @Override
    public void start() {

    }

    @Override
    public void onUserLoaded(User user) {
        mLoginView.loginSuccess( user );
    }

    @Override
    public void onDataNotAvailable() {
        mLoginView.loginFailed();
    }
}
