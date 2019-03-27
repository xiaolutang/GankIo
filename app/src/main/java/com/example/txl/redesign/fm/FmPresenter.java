package com.example.txl.redesign.fm;

import android.support.annotation.Nullable;

import com.example.txl.redesign.api.XmlyApi;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/27
 * description：
 */
public class FmPresenter implements FmContract.Presenter {
    FmContract.View fmView;

    public FmPresenter(FmContract.View fmView) {
        this.fmView = fmView;
    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore() {

    }


    @Override
    public void start() {

    }

    @Override
    public void getAlbumList() {

    }

    @Override
    public void getFmCategory() {
        XmlyApi.getXmlyCategorys( null, new IDataCallBack<CategoryList>() {
            @Override
            public void onSuccess(@Nullable CategoryList categoryList) {
                fmView.onCategorySuccess( categoryList );
            }

            @Override
            public void onError(int i, String s) {
                fmView.onCategoryFailed();
            }
        } );
    }
}
