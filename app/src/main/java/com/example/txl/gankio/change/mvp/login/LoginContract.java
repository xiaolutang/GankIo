package com.example.txl.gankio.change.mvp.login;

import com.example.txl.gankio.change.mvp.BasePresenter;
import com.example.txl.gankio.change.mvp.BaseView;
import com.example.txl.gankio.change.mvp.data.User;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/1
 * description：
 */
public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void registerSuccess();
        void showUserExit(User user);
        void loginSuccess(User user);
        void loginFailed();
    }

    interface Presenter extends BasePresenter {

        void registerUser(String userName, String password);
        void login(String userName, String password);
    }
}
