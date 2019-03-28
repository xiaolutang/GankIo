package com.example.txl.redesign.fragment;

import com.example.txl.redesign.IBaseView;
import com.example.txl.redesign.IRefreshPresenter;
import com.example.txl.redesign.data.model.NewsData;


import java.util.List;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/21
 * description：
 */
public class NewsContract {
    public interface View extends IBaseView<Presenter> {
        void refreshFinish(List<NewsData> dataList, boolean hasMore);

        void refreshError();

        /**
         * @param dataList 接口返回数据
         * @param hasMore 是否还有更多
         * */
        void loadMoreFinish(List<NewsData> dataList,boolean hasMore);

        void loadMoreError();
    }

    public interface Presenter extends IRefreshPresenter {

    }
}
