package com.example.txl.redesign.fragment;

import com.example.txl.redesign.IBasePresenter;
import com.example.txl.redesign.IBaseView;

import org.json.JSONObject;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/21
 * description：
 */
public class NewsContract {
    interface View extends IBaseView<Presenter> {
        void refreshFinish(JSONObject jsonObject,boolean hasMore);

        void refreshError();

        /**
         * @param jsonObject 接口返回数据
         * @param hasMore 是否还有更多
         * */
        void loadMoreFinish(JSONObject jsonObject,boolean hasMore);

        void loadMoreError();
    }

    interface Presenter extends IBasePresenter {
        void refresh();

        void loadMore();
    }
}
