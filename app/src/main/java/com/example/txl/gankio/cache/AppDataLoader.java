package com.example.txl.gankio.cache;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;


import com.example.txl.gankio.change.mvp.data.VideoBean;
import com.google.gson.Gson;
import com.jakewharton.disklrucache.DiskLruCache;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/14
 * description：本地缓存网络数据
 */
public class AppDataLoader {
    private static final String TAG = "AppDataLoader";

    public static final int MESSAGE_POST_RESULT = 1;
    public static final int MESSAGE_POST_IMAGE_SIZE = 2;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int CORE_POOL_SIZE= CPU_COUNT +1;

    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2+1;

    private static final long KEEP_ALIVE = 10L;

    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;//文件缓存大小为50M

    private static final int IO_BUFFER_SIZE = 8 * 1024;

    private static final int DISK_CACHE_INDEX = 0;

    private boolean mIsDiskLruCacheCreate = false;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread( r, "ImageLoader#"+mCount.getAndIncrement() );
        }
    };

    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor( CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(  ), sThreadFactory );

    private Handler mMainHandler = new Handler( Looper.getMainLooper() ){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_POST_RESULT:

                    return;
                case MESSAGE_POST_IMAGE_SIZE:
                    return;
            }

            super.handleMessage( msg );
        }
    };

    private Context mContext;
    private LruCache<String, VideoBean.VideoInfo.VideoContent> mMemoryCache;
    private DiskLruCache mDiskLruCache;

    private AppDataLoader(Context context){
        mContext = context;
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        int cacheSize = maxMemory/8;
        mMemoryCache = new LruCache<String, VideoBean.VideoInfo.VideoContent>( cacheSize );
        File diskCacheDir = getDiskCacheDir(mContext, "VideoBean.VideoInfo");
        if(!diskCacheDir.exists()){
            diskCacheDir.mkdirs();
        }
        long m = getUsableSpace(diskCacheDir);
        if(m > DISK_CACHE_SIZE){
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir,1,1,DISK_CACHE_SIZE);
                mIsDiskLruCacheCreate = true;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private String hashKeyFromUrl(String url){
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance( "MD5" );
            mDigest.update( url.getBytes() );
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey = String.valueOf( url.hashCode() );
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder(  );
        for (int i=0; i<bytes.length; i++){
            String hex = Integer.toHexString( 0xFF & bytes[i] );
            if(hex.length() == 1){
                sb.append( "0" );
            }
            sb.append( hex );
        }
        return sb.toString();
    }

    public File getDiskCacheDir(Context context, String uniqueName){
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED );
        String cachePath;
        if(externalStorageAvailable){
            cachePath = context.getExternalCacheDir().getPath();
        }else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File( cachePath+File.separator+uniqueName );
    }

    private long getUsableSpace(File path){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return path.getUsableSpace();
        }
        final StatFs statFs = new StatFs( path.getPath() );
        return statFs.getBlockSizeLong() + statFs.getAvailableBlocksLong();
    }

    public static AppDataLoader build(Context context){
        return new AppDataLoader( context );
    }

    private void addVideoInfoToMemoryCache(String key, VideoBean.VideoInfo.VideoContent value){
        if(getVideoInfoFromMemCache(key) == null){
            mMemoryCache.put( key,value );
        }
    }

    private VideoBean.VideoInfo.VideoContent getVideoInfoFromMemCache(String key){
        return mMemoryCache.get( key );
    }


    public void loadVideoInfoContent(String uri, VideoInfoCallback callback){
        VideoBean.VideoInfo.VideoContent videoInfo = getVideoInfoFromMemCache( uri );
        if(videoInfo != null){
            Log.d( TAG,"loadBitmapFromMemCache,uri:"+uri );
            callback.loadVideoInfoReady( videoInfo );
            return;
        }
        Runnable loadVideoInfoTask = new Runnable() {
            @Override
            public void run() {
                try {
                    VideoBean.VideoInfo.VideoContent videoInfo = loadVideoInfoContentFromDiskCache(uri);
                    if(videoInfo != null){
                        Log.d( TAG,"loadVideoInfoContentFromDiskCache,uri:"+uri );
                        callback.loadVideoInfoReady( videoInfo );
                        return;
                    }
                    videoInfo = loadVideoInfoContentFromHttp(uri);
                    callback.loadVideoInfoReady( videoInfo );
                    Log.d( TAG,"loadVideoInfoContentFromHttp,uri:"+uri );
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        THREAD_POOL_EXECUTOR.execute( loadVideoInfoTask );
    }

    public VideoBean.VideoInfo.VideoContent loadVideoInfoContent(String uri){
        VideoBean.VideoInfo.VideoContent content = getVideoInfoFromMemCache( uri );
        if(content != null){
            Log.d( TAG,"loadBitmapFromMemCache,uri:"+uri );
            return content;
        }
        try {
            content = loadVideoInfoContentFromDiskCache(uri);
            if(content != null){
                Log.d( TAG,"loadVideoInfoContentFromDiskCache,uri:"+uri );
                return content;
            }
            content = loadVideoInfoContentFromHttp(uri);
            Log.d( TAG,"loadVideoInfoContentFromHttp,uri:"+uri );
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private VideoBean.VideoInfo.VideoContent loadVideoInfoContentFromDiskCache(String url) throws IOException, ClassNotFoundException {
        if(Looper.myLooper() == Looper.getMainLooper()){
            Log.w(TAG,"load VideoInfo from UI Thread, it's not recommended!");
        }
        if(mDiskLruCache == null){
            return null;
        }
        VideoBean.VideoInfo.VideoContent videoInfo = null;
        String key = hashKeyFromUrl( url );
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if(snapshot != null){
            InputStream inputStream =  snapshot.getInputStream( DISK_CACHE_INDEX );
            ObjectInputStream objectInputStream = new ObjectInputStream( inputStream );
            videoInfo = (VideoBean.VideoInfo.VideoContent) objectInputStream.readObject();
            if(videoInfo != null){
                addVideoInfoToMemoryCache( key,videoInfo );
            }
        }
        return videoInfo;
    }

    private VideoBean.VideoInfo.VideoContent loadVideoInfoContentFromHttp(String url) throws IOException{
        if(Looper.myLooper() == Looper.getMainLooper()){
            throw new RuntimeException( "can not visit network from UI Thread" );
        }
        if(mDiskLruCache == null){
            return null;
        }
        String key = hashKeyFromUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if(editor != null){
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if(downloadUrlToStream(url,outputStream)){
                editor.commit();
            }else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        Log.d( TAG,"loadVideoInfoContentFromHttp DiskLruCache loadVideoInfoContentFromHttp,uri:"+url);
        try {
            loadVideoInfoContentFromDiskCache(url);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadVideoInfoContentFromMemCache( url );
    }

    private VideoBean.VideoInfo.VideoContent loadVideoInfoContentFromMemCache(String url){
        final String key = hashKeyFromUrl(url);
        return getVideoInfoFromMemCache( key );
    }

    private boolean downloadUrlToStream(String url,OutputStream outputStream){
        Log.d(TAG, "downloadUrlToStream url : "+url);
        Connection connection = Jsoup.connect(url );
        Document document = null;
        try {
            document = connection.get();
            Elements content = document.select("div.main-content-block");
            Elements script = content.select( "script" );
            Gson gson = new Gson();
            String videoContent = "{"+script.last().toString().split( "\\{" )[2].split( "\\}" )[0]+"}";
            VideoBean.VideoInfo.VideoContent content2 = gson.fromJson( videoContent, VideoBean.VideoInfo.VideoContent.class);
            ObjectOutputStream outputStream1 = new ObjectOutputStream(outputStream);
            outputStream1.writeObject(content2);//将对象存到diskLruCache
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public interface VideoInfoCallback {
        /**
         * 加载完成
         * */
        void loadVideoInfoReady(VideoBean.VideoInfo.VideoContent content);
    }
}
