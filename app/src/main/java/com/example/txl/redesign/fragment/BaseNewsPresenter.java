package com.example.txl.redesign.fragment;

import com.example.txl.redesign.api.ApiRetrofit;
import com.example.txl.redesign.data.model.BaseNewsResult;
import com.example.txl.redesign.utils.RxJavaUtils;

import org.json.JSONObject;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/21
 * description：
 */
public class BaseNewsPresenter implements NewsContract.Presenter {
    /**
     * 当前页数
     * */
    protected int pageIndex = 1;
    /**
     * 每页请求个数
     * */
    protected int count = 20;

    protected NewsContract.View view;
    protected String categoryId ;

    protected ApiRetrofit.GankIoApi gankIoApi;

    public BaseNewsPresenter(NewsContract.View view, String categoryId) {
        this.view = view;
        this.categoryId = categoryId;
        view.setPresenter( this );
    }

    @Override
    public void refresh() {
        pageIndex = 1;
        Observable<JSONObject> observable  = gankIoApi.getFuLi(categoryId,count,pageIndex);
        observable .compose(RxJavaUtils.gsonTransform(BaseNewsResult.class))
                .subscribeOn( Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new Observer<BaseNewsResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseNewsResult baseNewsResult) {
                        if(baseNewsResult.isError()){
                            view.refreshError();
                            return;
                        }

                        view.refreshFinish(baseNewsResult.getResults(),true);

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.refreshError();
                    }

                    @Override
                    public void onComplete() {

                    }
                } );
    }

    @Override
    public void loadMore() {
        pageIndex++;
        Observable<JSONObject> observable = gankIoApi.getFuLi(categoryId,count,pageIndex);
        observable .compose(RxJavaUtils.gsonTransform(BaseNewsResult.class))
                .subscribeOn( Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new Observer<BaseNewsResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseNewsResult baseNewsResult) {
                        if(baseNewsResult.isError()){
                            view.loadMoreError();
                            return;
                        }
                        view.loadMoreFinish(baseNewsResult.getResults(),true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.loadMoreError();
                    }

                    @Override
                    public void onComplete() {

                    }
                } );
    }

    @Override
    public void refreshSecondFloor(String categoryId) {

    }

    @Override
    public void start() {
        gankIoApi = ApiRetrofit.getGankIoApi();
    }
}
