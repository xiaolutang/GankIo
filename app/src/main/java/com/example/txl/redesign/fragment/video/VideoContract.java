package com.example.txl.redesign.fragment.video;

import com.example.txl.redesign.IRefreshPresenter;
import com.example.txl.redesign.IRefreshView;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/7
 * description：
 */
public class VideoContract {
    interface View<D> extends IRefreshView<Presenter,D> {

    }

    interface Presenter extends IRefreshPresenter {
        void destroy();
    }
}
