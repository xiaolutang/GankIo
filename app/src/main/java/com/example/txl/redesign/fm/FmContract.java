package com.example.txl.redesign.fm;

import com.example.txl.redesign.IBaseView;
import com.example.txl.redesign.IRefreshPresenter;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/27
 * description：
 */
public interface FmContract {
    interface View extends IBaseView<Presenter> {
        void onCategorySuccess(CategoryList categoryList);
        void onCategoryFailed();
    }

    interface Presenter extends IRefreshPresenter {
        void getAlbumList();
        void getFmCategory();
    }
}
