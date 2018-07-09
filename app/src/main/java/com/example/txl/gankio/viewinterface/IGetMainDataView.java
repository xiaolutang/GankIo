package com.example.txl.gankio.viewinterface;

import com.example.txl.gankio.bean.IdelReaderCategoryRoot;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public interface IGetMainDataView {
    void getIdelReaderSuccess(IdelReaderCategoryRoot root);
    void getIdelReaderFailed(Object o);

    void getVideoSuccess(Object o);
    void getVideoFailed(Object o);

    void getAllDataSuccess(Object o);
    void getAllDataFailed(Object o);
}
