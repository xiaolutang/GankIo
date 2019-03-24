package com.example.txl.redesign.fragment;

import com.example.txl.redesign.IBasePresenter;
import com.example.txl.redesign.IBaseView;
import com.example.txl.redesign.model.NewsData;

import org.json.JSONObject;

import java.util.List;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/21
 * description：
 */
public class NewsContract {
    interface View extends IBaseView<Presenter> {
        void refreshFinish(List<NewsData> dataList, boolean hasMore);

        void refreshError();

        /**
         * @param jsonObject 接口返回数据
         * @param hasMore 是否还有更多
         * */
        void loadMoreFinish(JSONObject jsonObject,boolean hasMore);

        void loadMoreError();

        /**
         * 刷新二楼结束
         * */
        void secondFloorFinish(JSONObject jsonObject);
    }

    interface Presenter extends IBasePresenter {
        void refresh();

        void loadMore();

        /**
         * 刷新二楼
         * */
        void refreshSecondFloor(String categoryId);
    }
}
