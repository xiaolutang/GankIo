package com.example.txl.gankio.viewinterface;

import com.example.txl.gankio.base.IBaseView;
import com.example.txl.gankio.bean.CommonIdelReaderSubclassification;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public interface IGetIdelReadView extends IBaseView {
    /**
     * 获取闲读资讯成功
     * */
    void onUpdateSuccess(CommonIdelReaderSubclassification subclassification);
    /**
     * 获取闲读资讯失败
     * */
    void onFailed();

    void loadDataSuccess(CommonIdelReaderSubclassification subclassification);
}
