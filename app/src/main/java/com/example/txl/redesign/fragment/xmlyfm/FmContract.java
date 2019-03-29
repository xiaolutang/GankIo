package com.example.txl.redesign.fragment.xmlyfm;

import com.example.txl.redesign.IRefreshPresenter;
import com.example.txl.redesign.IRefreshView;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/27
 * description：
 */
public interface FmContract {
    interface View<D> extends IRefreshView<Presenter,D> {
        void onCategorySuccess(CategoryList categoryList);
        void onCategoryFailed();
        void onRefreshSuccess(D data);
        void onRefreshFailed();
        void onLoadMoreSuccess(D data,boolean hasMore);
        void onLoadMoreFailed();
    }

    interface Presenter extends IRefreshPresenter {
        void getFmCategory();
    }
}
