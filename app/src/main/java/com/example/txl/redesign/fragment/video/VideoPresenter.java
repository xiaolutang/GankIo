package com.example.txl.redesign.fragment.video;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.txl.redesign.App;
import com.example.txl.redesign.api.ApiFactory;
import com.example.txl.gankio.cache.AppDataLoader;
import com.example.txl.redesign.data.VideoBean;
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
    int defaultPageCount = 10;

    /**
     * 当前页数.
     * */
    int currentPageIndex = 1;

    VideoContract.View<List<VideoBean.VideoInfo>> videoView;
    Context context;
    VideoIntentService.VideoConsumer videoConsumer;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            videoConsumer = (VideoIntentService.VideoConsumer) service;
            refresh();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            videoConsumer = null;
        }
    };


    public VideoPresenter(Context context,VideoContract.View<List<VideoBean.VideoInfo>> videoView) {
        this.context = context;
        this.videoView = videoView;
        videoView.setPresenter( this );
        Intent intent = new Intent( context,VideoIntentService.class );
        context.bindService( intent, serviceConnection,Context.BIND_AUTO_CREATE);
    }

    public void getVideoData(boolean refresh){
        String url = ApiFactory.URL_GET_VIDEO_DATA +""+defaultPageCount+"/"+currentPageIndex;
        Log.d(TAG, "getFuLiData url : "+url);
        OkHttpClient okHttpClient = ApiFactory.getClient();
        final Request request = new Request.Builder()
                .cacheControl( ApiFactory.getDefaultCacheControl() )
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
                            videoView.onRefreshSuccess(root.getResults());
                        }else {
                            videoView.onRefreshFailed();
                        }
                    }
                } );

            }
        });
        preLoad();
    }

    @Override
    public void start() {
    }

    @Override
    public void refresh() {
        currentPageIndex = 1;
        if(videoConsumer == null){
            return;
        }
        videoConsumer.getVideoList( currentPageIndex, new VideoIntentService.IVideoListCallback() {
            @Override
            public void onSuccess(List<VideoBean.VideoInfo> videoInfoList) {
                videoView.onRefreshSuccess( videoInfoList );
            }

            @Override
            public void onError() {
                videoView.onRefreshFailed();
            }
        } );
    }

    @Override
    public void loadMore() {
        if(videoConsumer == null){
            return;
        }
        currentPageIndex ++;
        videoConsumer.getVideoList( currentPageIndex, new VideoIntentService.IVideoListCallback() {
            @Override
            public void onSuccess(List<VideoBean.VideoInfo> videoInfoList) {
                videoView.onLoadMoreSuccess( videoInfoList,true );
            }

            @Override
            public void onError() {
                videoView.onLoadMoreFailed();
            }
        } );
    }


    private void preLoad(){
        String url = ApiFactory.URL_GET_VIDEO_DATA +""+defaultPageCount+"/"+(currentPageIndex+1);
        Log.d(TAG, "preLoad url : "+url);
        OkHttpClient okHttpClient = ApiFactory.getClient();
        final Request request = new Request.Builder()
                .cacheControl( ApiFactory.getDefaultCacheControl() )
                .url( url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "last preLoad onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = StringUtils.replaceBlank( response.body().string());
                Log.d( TAG,"last preLoad onResponse " +jsonString);
                Gson gson = new Gson();
                VideoBean root = gson.fromJson( jsonString, VideoBean.class);
                List<VideoBean.VideoInfo> list = root.getResults();
                Iterator iterator = list.iterator();
                for (;iterator.hasNext();){
                    VideoBean.VideoInfo videoInfo = (VideoBean.VideoInfo) iterator.next();
                    App.getAppDataLoader().loadVideoInfoContent( videoInfo.getUrl(), new AppDataLoader.VideoInfoCallback() {
                        @Override
                        public void loadVideoInfoReady(VideoBean.VideoInfo.VideoContent content) {

                        }
                    } );
                }
            }
        });
        if(currentPageIndex == 1){
            return;
        }
        url = ApiFactory.URL_GET_VIDEO_DATA +""+defaultPageCount+"/"+(currentPageIndex-1);
        Log.d(TAG, "preLoad url : "+url);
        final Request preRequest = new Request.Builder()
                .cacheControl( ApiFactory.getDefaultCacheControl() )
                .url( url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call preCall = okHttpClient.newCall(preRequest);
        preCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "preLoad onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = StringUtils.replaceBlank( response.body().string());
                Log.d( TAG,"preLoad onResponse " +jsonString);
                Gson gson = new Gson();
                VideoBean root = gson.fromJson( jsonString, VideoBean.class);
                List<VideoBean.VideoInfo> list = root.getResults();
                Iterator iterator = list.iterator();
                for (;iterator.hasNext();){
                    VideoBean.VideoInfo videoInfo = (VideoBean.VideoInfo) iterator.next();
                    App.getAppDataLoader().loadVideoInfoContent( videoInfo.getUrl(), new AppDataLoader.VideoInfoCallback() {
                        @Override
                        public void loadVideoInfoReady(VideoBean.VideoInfo.VideoContent content) {

                        }
                    } );
                }
            }
        });
    }

    @Override
    public void destroy() {
        context.unbindService( serviceConnection );
    }
}
