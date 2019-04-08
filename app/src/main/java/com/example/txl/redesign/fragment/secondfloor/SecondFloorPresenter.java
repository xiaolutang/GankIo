package com.example.txl.redesign.fragment.secondfloor;

import com.example.txl.redesign.api.ApiRetrofit;
import com.example.txl.redesign.data.XmlyFmData;
import com.example.txl.redesign.data.model.BaseNewsResult;
import com.example.txl.redesign.fragment.BaseNewsPresenter;
import com.example.txl.redesign.fragment.NewsContract;
import com.example.txl.redesign.data.model.NewsData;
import com.example.txl.redesign.data.model.TodayResult;
import com.example.txl.redesign.utils.AppExecutors;
import com.example.txl.redesign.utils.RxJavaUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/25
 * description：
 */
public class SecondFloorPresenter implements SecondFloorContract.Presenter {
    /**
     * 当前页数
     * */
    protected int pageIndex = 1;
    /**
     * 每页请求个数
     * */
    protected int count = 20;

    protected SecondFloorContract.View view;
    protected String categoryId ;

    protected ApiRetrofit.GankIoApi gankIoApi;

    public SecondFloorPresenter(SecondFloorContract.View view, String categoryId) {
        this.view = view;
        this.categoryId = categoryId;
        view.setPresenter( this );
    }

    @Override
    public void refresh() {
        Observable<JSONObject> objectObservable = gankIoApi.getTodayGanHuo();
        objectObservable
                .subscribeOn( Schedulers.io())
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
                                List<XmlyFmData> xmlyFmDataList = new ArrayList<>(  );
                                for (NewsData newsData:result.get福利()){
                                    XmlyFmData xmlyFmData = new XmlyFmData( XmlyFmData.GANK_IO_TYPE_ARTICLE );
                                    xmlyFmData.setNewsData( newsData );
                                    xmlyFmDataList.add( xmlyFmData );
                                }
                                AppExecutors.getInstance().mainThread().execute( new Runnable() {
                                    @Override
                                    public void run() {
                                        view.onFuliDataCallback(xmlyFmDataList);
                                    }
                                } );

                            }


                        }
                        Collections.shuffle(dataList);
                        List<XmlyFmData> xmlyFmDataList = new ArrayList<>(  );
                        for (NewsData newsData:dataList){
                            XmlyFmData xmlyFmData = new XmlyFmData( XmlyFmData.GANK_IO_TYPE_ARTICLE );
                            xmlyFmData.setNewsData( newsData );
                            xmlyFmDataList.add( xmlyFmData );
                        }
                        AppExecutors.getInstance().mainThread().execute( new Runnable() {
                            @Override
                            public void run() {
                                view.refreshFinish(xmlyFmDataList,false);
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

    @Override
    public void loadMore() {
        pageIndex++;
        Observable<JSONObject> observable = gankIoApi.getFuLi(categoryId,count,pageIndex);
        observable .compose( RxJavaUtils.gsonTransform( BaseNewsResult.class))
                .subscribeOn( Schedulers.io())
                .observeOn( AndroidSchedulers.mainThread())
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
                        List<XmlyFmData> xmlyFmDataList = new ArrayList<>(  );
                        for (NewsData newsData:baseNewsResult.getResults()){
                            XmlyFmData xmlyFmData = new XmlyFmData( XmlyFmData.GANK_IO_TYPE_ARTICLE );
                            xmlyFmData.setNewsData( newsData );
                            xmlyFmDataList.add( xmlyFmData );
                        }
                        view.loadMoreFinish(xmlyFmDataList,true);
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
    public void start() {
        gankIoApi = ApiRetrofit.getGankIoApi();
    }
}
