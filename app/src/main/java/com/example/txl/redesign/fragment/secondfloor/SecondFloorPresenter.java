package com.example.txl.redesign.fragment.secondfloor;

import com.example.txl.redesign.fragment.BaseNewsPresenter;
import com.example.txl.redesign.fragment.NewsContract;
import com.example.txl.redesign.data.model.NewsData;
import com.example.txl.redesign.data.model.TodayResult;
import com.example.txl.redesign.utils.AppExecutors;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/25
 * description：
 */
public class SecondFloorPresenter extends BaseNewsPresenter {
    public SecondFloorPresenter(NewsContract.View view, String categoryId) {
        super( view, categoryId );
    }

    @Override
    public void refresh() {
        Observable<JSONObject> objectObservable = gankIoApi.getTodayGanHuo();
        objectObservable .subscribeOn( Schedulers.io())
                .observeOn( Schedulers.io())
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
                        List<NewsData> dataList  = new ArrayList<>(  );
                        Gson gson = new Gson();
                        if(categoryId.equals( "推荐" )){
                            TodayResult result = gson.fromJson( jsonObject.optString( "results" ), TodayResult.class );
                            if(result.getAndroid() != null && result.getAndroid().size() != 0){
                                dataList.addAll( result.getAndroid() );
                            }
                            if(result.getApp() != null && result.getApp().size() != 0){
                                dataList.addAll( result.getApp() );
                            }
                            if(result.getiOS() != null && result.getiOS().size() != 0){
                                dataList.addAll( result.getiOS() );
                            }
                            if(result.get休息视频() != null && result.get休息视频().size() != 0){
                                dataList.addAll( result.get休息视频() );
                            }
                            if(result.get前端() != null && result.get前端().size() != 0){
                                dataList.addAll( result.get前端() );
                            }
                            if(result.get拓展资源() != null && result.get拓展资源().size() != 0){
                                dataList.addAll( result.get拓展资源() );
                            }
                            if(result.get瞎推荐() != null && result.get瞎推荐().size() != 0){
                                dataList.addAll( result.get瞎推荐() );
                            }
                            if(result.get福利() != null && result.get福利().size() != 0){
                                dataList.addAll( result.get福利() );
                            }


                        }
                        Collections.shuffle(dataList);
                        AppExecutors.getInstance().mainThread().execute( new Runnable() {
                            @Override
                            public void run() {
                                view.refreshFinish(dataList,false);
                            }
                        } );


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
}
