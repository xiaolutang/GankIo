package com.example.txl.gankio.viewinterface;

import com.example.txl.gankio.bean.BeautyGirls;
import com.example.txl.gankio.bean.IdelInfo;

import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/8
 * description：
 */
public interface IGetFuLiData {
    void onAddIdelInfoSuccess(List<BeautyGirls.Girl> results);
    void onAddIdelInfoFailed();

    void updateIdelInfoSuccess(List<BeautyGirls.Girl> results);
    void updateIdelInfoFailed();
}
