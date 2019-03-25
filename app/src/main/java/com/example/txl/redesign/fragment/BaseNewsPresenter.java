package com.example.txl.redesign.fragment;

import com.example.txl.redesign.api.ApiRetrofit;
import com.example.txl.redesign.data.model.BaseNewsResult;
import com.example.txl.redesign.data.model.NewsData;
import com.example.txl.redesign.data.model.TodayResult;
import com.example.txl.redesign.utils.AppExecutors;
import com.example.txl.redesign.utils.GlobalCacheUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    protected String  requestKey;

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
        observable .subscribeOn( Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe( new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        if(jsonObject.optBoolean( "error" )){
                            view.refreshError();
                            return;
                        }

                        Gson gson = new Gson();
                        BaseNewsResult baseNewsResult = gson.fromJson( jsonObject.toString(), BaseNewsResult.class );
                        AppExecutors.getInstance().mainThread().execute( new Runnable() {
                            @Override
                            public void run() {
                                view.refreshFinish(baseNewsResult.getResults(),true);
                            }
                        } );

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
    public void loadMore() {
        pageIndex++;
        Observable<JSONObject> observable = gankIoApi.getFuLi(categoryId,count,pageIndex);
        observable .subscribeOn( Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        if(jsonObject.optBoolean( "error" )){
                            view.loadMoreError();
                            return;
                        }

                        Gson gson = new Gson();
                        BaseNewsResult baseNewsResult = gson.fromJson( jsonObject.toString(), BaseNewsResult.class );
                        AppExecutors.getInstance().mainThread().execute( new Runnable() {
                            @Override
                            public void run() {
                                view.loadMoreFinish(baseNewsResult.getResults(),true);
                            }
                        } );

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
