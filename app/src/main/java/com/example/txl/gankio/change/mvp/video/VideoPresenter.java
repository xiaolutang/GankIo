package com.example.txl.gankio.change.mvp.video;

import android.util.Log;

import com.example.txl.gankio.App;
import com.example.txl.gankio.api.GankIoApi;
import com.example.txl.gankio.change.mvp.data.VideoBean;
import com.example.txl.gankio.utils.StringUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/7
 * description：
 */
public class VideoPresenter implements VideoContract.Presenter{
    private static final String TAG = "VideoPresenter";
    /**
     * 默认每页的请求的数据
     * */
    int defaultPageCount = 5;

    /**
     * 当前页数
     * */
    int currentPageIndex = 1;

    VideoContract.View videoView;

    public VideoPresenter(VideoContract.View videoView) {
        this.videoView = videoView;
        videoView.setPresenter( this );
    }

    public void getVideoData(boolean refresh){
        String url = GankIoApi.URL_GET_VIDEO_DATA +""+defaultPageCount+"/"+currentPageIndex;
        Log.d(TAG, "getFuLiData url : "+url);
        OkHttpClient okHttpClient = GankIoApi.getClient();
        final Request request = new Request.Builder()
                .cacheControl( GankIoApi.getDefaultCacheControl() )
                .url( url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "getFuLiData onFailure: ");
//                if(refresh){
//                    iGetFuLiData.updateFuLiDataFailed();
//                }else {
//                    iGetFuLiData.onAddFuLiDataFailed();
//                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = StringUtils.replaceBlank( response.body().string());
                Log.d( TAG,"getFuLiData onResponse " +jsonString);
                Gson gson = new Gson();
                VideoBean root = gson.fromJson( jsonString, VideoBean.class);
                List<VideoBean.VideoInfo> list = root.getResults();
                Iterator iterator = list.iterator();
                for (;iterator.hasNext();){
                    VideoBean.VideoInfo videoInfo = (VideoBean.VideoInfo) iterator.next();
                    VideoBean.VideoInfo.VideoContent content = App.getAppDataLoader().loadVideoInfoContent( videoInfo.getUrl());
                    if(content == null){
                        Log.w( TAG,"content is null" );
                        iterator.remove();
                    }else {
                        videoInfo.setContent( content );
                    }

                }
                App.getAppInstance().postToMainLooper( new Runnable() {
                    @Override
                    public void run() {
                        if(root.isError() || root.getResults() == null){
                            Log.e( TAG, "getFuLiData onResponse "+root.isError()+" root.getCategories() "+ root.getResults());
//                            if(refresh){
//                                iGetFuLiData.updateFuLiDataFailed();
//                            }else {
//                                iGetFuLiData.onAddFuLiDataFailed();
//                            }
                            return;
                        }
                        if(refresh){
                            videoView.refreshFinish(root.getResults());
                        }else {
                            videoView.loadMoreFinish(root.getResults());
                        }
                    }
                } );

            }
        });
    }

    @Override
    public void start() {
        currentPageIndex = 1;
        getVideoData( true );
    }

    @Override
    public void refresh() {
        currentPageIndex = 1;
        getVideoData( true );
    }

    @Override
    public void loadMore() {
        currentPageIndex ++;
        getVideoData( false );
    }
}
