package com.example.txl.gankio.viewinterface;

import com.example.txl.gankio.bean.IdelInfo;

import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/7
 * description：
 */
public interface IGetIdelInfo {
    void onAddIdelInfoSuccess(List<IdelInfo.InfoContent> results);
    void onAddIdelInfoFailed();

    void updateIdelInfoSuccess(List<IdelInfo.InfoContent> results);
    void updateIdelInfoFailed();
}
