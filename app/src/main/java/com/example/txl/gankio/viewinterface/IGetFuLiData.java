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
    void onAddFuLiDataSuccess(List<BeautyGirls.Girl> results);
    void onAddFuLiDataFailed();

    void updateFuLiDataSuccess(List<BeautyGirls.Girl> results);
    void updateFuLiDataFailed();
}
