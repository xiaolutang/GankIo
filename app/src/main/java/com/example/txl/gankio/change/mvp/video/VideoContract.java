package com.example.txl.gankio.change.mvp.video;

import com.example.txl.gankio.change.mvp.BasePresenter;
import com.example.txl.gankio.change.mvp.BaseView;
import com.example.txl.gankio.change.mvp.data.VideoBean;

import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/7
 * description：
 */
public class VideoContract {
    interface View extends BaseView<Presenter> {
        void refreshFinish(List<VideoBean.VideoInfo> videoInfoList);
        void loadMoreFinish(List<VideoBean.VideoInfo> videoInfoList);
    }

    interface Presenter extends BasePresenter {
        void refresh();
        void loadMore();
    }
}
