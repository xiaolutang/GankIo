package com.example.txl.redesign.fragment.xmlyfm.album;

import com.example.txl.redesign.IRefreshPresenter;
import com.example.txl.redesign.IRefreshView;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/31
 * description：
 */
public interface TrackListContract {
    interface View<D> extends IRefreshView<Presenter,D>{

    }

    interface Presenter extends IRefreshPresenter{

    }
}
