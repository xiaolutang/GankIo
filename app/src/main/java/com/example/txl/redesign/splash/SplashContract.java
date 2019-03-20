package com.example.txl.redesign.splash;

import com.example.txl.redesign.IBasePresenter;
import com.example.txl.redesign.IBaseView;

import org.json.JSONObject;

/**
 * @author TXL
 * description :
 */
public interface SplashContract {
    interface View extends IBaseView<Presenter> {
        /**
         * 获取相关数据出错
         * */
        void showDataError();

        void prepareDataFinish();

        void prepareSplashFinish(JSONObject jsonObject);
    }

    interface Presenter extends IBasePresenter {
        /**
         * 准备首页数据
         * */
        void prepareMainData();

        void prepareSplashData();
    }
}
