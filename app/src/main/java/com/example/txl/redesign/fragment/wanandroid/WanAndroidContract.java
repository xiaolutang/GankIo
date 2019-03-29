package com.example.txl.redesign.fragment.wanandroid;

import com.example.txl.redesign.IRefreshPresenter;
import com.example.txl.redesign.IRefreshView;
import com.example.txl.redesign.data.XmlyFmData;

/**
 * @author TXL
 * description :
 */
public interface WanAndroidContract {
    interface View<D> extends IRefreshView<Presenter,D> {
        void onBannerSuccess(XmlyFmData data);
        void onBannerFailed();
    }

    interface Presenter extends IRefreshPresenter {

    }
}
