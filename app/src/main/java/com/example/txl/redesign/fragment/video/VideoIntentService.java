package com.example.txl.redesign.fragment.video;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AndroidRuntimeException;
import android.util.Log;

import com.example.txl.gankio.utils.StringUtils;
import com.example.txl.redesign.App;
import com.example.txl.redesign.api.ApiFactory;
import com.example.txl.redesign.data.VideoBean;
import com.example.txl.redesign.utils.AppExecutors;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *使用生产者消费者模型获取解析抖音api
 */
public class VideoIntentService extends IntentService {
    private String TAG = getClass().getSimpleName();
    private int defaultPageCount = 20;
    private int currentPageIndex = 1;
    private Map<Integer, List<VideoBean.VideoInfo>> map = new ConcurrentHashMap<>();
    private final VideoProducer videoProducer = new VideoProducer();
    private final VideoConsumer videoConsumer = new VideoConsumer();

    /**
     * 缓存几页数据
     * */
    private int cacheSize = 3;

    public VideoIntentService() {
        super( "VideoIntentService" );
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if(!videoProducer.init){
            videoProducer.init = true;
            AppExecutors.getInstance().networkIO().execute( videoProducer  );
            //这样处理可以么》？
            while (true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d( TAG,"onDestroy" );
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return videoConsumer;
    }

    private void getVideo() {
        String url = ApiFactory.URL_GET_VIDEO_DATA +""+defaultPageCount+"/"+currentPageIndex;
        Log.d(TAG, "getFuLiData url : "+url);
        OkHttpClient okHttpClient = ApiFactory.getClient();
        final Request request = new Request.Builder()
                .cacheControl( ApiFactory.getDefaultCacheControl() )
                .url( url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            String jsonString = StringUtils.replaceBlank( response.body().string());
            Log.d( TAG,"getFuLiData onResponse " +jsonString);
            Gson gson = new Gson();
            VideoBean root = gson.fromJson( jsonString, VideoBean.class);
            List<VideoBean.VideoInfo> list = root.getResults();
            List<VideoBean.VideoInfo> resultList = new ArrayList<>(  );
            Iterator iterator = list.iterator();
            for (;iterator.hasNext();){
                VideoBean.VideoInfo videoInfo = (VideoBean.VideoInfo) iterator.next();
                VideoBean.VideoInfo.VideoContent content = App.getAppDataLoader().loadVideoInfoContent( videoInfo.getUrl());
                if(content == null){
                    Log.w( TAG,"content is null" );
                }else {
                    videoInfo.setContent( content );
                    resultList.add( videoInfo );
                }
                iterator.next();
            }
            Log.d( TAG,"创建currentPageIndex  "+currentPageIndex );
            map.put( currentPageIndex, resultList);
            currentPageIndex++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class VideoProducer  implements Runnable{
        boolean init = false;
        @Override
        public void run() {
           try {
               while (!Thread.interrupted()){
                    synchronized (this){
                        while (map.size() >= cacheSize){
                            wait();
                        }
                    }
                    synchronized (videoConsumer){
                        getVideo();
                        videoConsumer.notifyAll();
                    }
               }
           }catch (Exception e){
               Log.d( TAG,"VideoProducer interrupted" );
           }
        }
    }

    public class VideoConsumer extends Binder {
        private VideoConsumer(){}


        public List<VideoBean.VideoInfo> getVideoList(int pageIndex){
            Log.d( TAG,"getVideoList  " +pageIndex);
            if (Looper.getMainLooper() == Looper.myLooper()){
                throw  new CalledFromWrongThreadException( "can,t call this method in main thread" );
            }
            List<VideoBean.VideoInfo> videoInfoList = null;
            try {
                synchronized (this){
                    while (!map.containsKey( pageIndex )){
                        synchronized (videoProducer){
                            if(pageIndex == 1){
                                map.clear();
                                currentPageIndex = 1;
                                videoProducer.notifyAll();
                            }
                        }
                        wait();
                    }
                }
                synchronized (videoProducer){
                    videoInfoList = map.remove( pageIndex );
                    Log.d( TAG,"消费  "+pageIndex );
                    videoProducer.notifyAll();
                }
            }catch (Exception e){
                Log.d( TAG,"VideoConsumer interrupted" );
            }
            return videoInfoList;
        }

        public void getVideoList(int pageIndex,IVideoListCallback callback){
            AppExecutors.getInstance().networkIO().execute( new Runnable() {
                @Override
                public void run() {
                    List<VideoBean.VideoInfo> videoInfoList = getVideoList(pageIndex);
                    if(callback != null){
                        AppExecutors.getInstance().mainThread().execute( new Runnable() {
                            @Override
                            public void run() {
                                if(videoInfoList == null){
                                    callback.onError();
                                }else {
                                    callback.onSuccess( videoInfoList );
                                }
                            }
                        } );
                    }
                }
            } );
        }
    }

    public interface IVideoListCallback{
        void onSuccess(List<VideoBean.VideoInfo> videoInfoList);
        void onError();
    }

    public static final class CalledFromWrongThreadException extends AndroidRuntimeException {
        public CalledFromWrongThreadException(String msg) {
            super(msg);
        }
    }
}
