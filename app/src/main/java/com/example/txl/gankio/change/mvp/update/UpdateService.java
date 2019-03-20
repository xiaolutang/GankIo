package com.example.txl.gankio.change.mvp.update;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.txl.gankio.R;
import com.example.txl.redesign.api.ApiFactory;
import com.example.txl.gankio.utils.DownUtils;
import com.example.txl.gankio.utils.StringUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/25
 * description：
 */
public class UpdateService extends IntentService {
    private static final String TAG = "UpdateService";

    private static final int MSG_UPDATE_PROGRESS = 0;

    private Handler mHandler;

    private UpdateInfo info;
    private NotificationCompat.Builder mBuilder;
    private DownUtils utils;
    private NotificationManager notificationManager;

    public UpdateService() {
        this("UpdateService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UpdateService(String name) {
        super( name );
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d( TAG,"onBind" );
        return null;
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d( TAG,"onStartCommand" );
        mHandler = new Handler(  ){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case MSG_UPDATE_PROGRESS:
                        double progress = utils.getCompleteRate() * 100;
                        mBuilder.setProgress( 100, (int) progress, false);
                        mBuilder.setDefaults(Notification.DEFAULT_LIGHTS);
                        notificationManager.notify(10, mBuilder.build());
                        if(progress < 100){
                            mHandler.sendEmptyMessageDelayed( MSG_UPDATE_PROGRESS,1000 );
                        }else {
                            //文件下载完成，进行安装
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setDataAndType(Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/updateApkFile/"),"application/vnd.android.package-archive");
                            startActivity(intent);
                            notificationManager.cancel( 10 );
                        }
                        return;
                }
                super.handleMessage( msg );
            }
        };
        return super.onStartCommand( intent, flags, startId );
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d( TAG,"onHandleIntent" );
        boolean needUpdate = checkUpdate();
        Log.d( TAG,"onHandleIntent  needUpdate:"+needUpdate );
        if(needUpdate){
            updateInNotification();
            downLoadApk();
        }
    }

    private void updateInNotification() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        /**
         *  实例化通知栏构造器
         */

        mBuilder = new NotificationCompat.Builder(this);

        /**
         *  设置Builder
         */
        //设置标题
        mBuilder.setContentTitle("版本跟新")
                //设置内容
                .setContentText("更新版本为: "+info.getServerVersion())
                //设置大图标
                .setLargeIcon( BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher_round)
                //设置通知时间
                .setWhen(System.currentTimeMillis())
                //首次进入时显示效果
                .setTicker("App升级")
                //设置通知方式，声音，震动，呼吸灯等效果，这里通知方式为声音
                .setDefaults( Notification.DEFAULT_SOUND)
                .setProgress( 100,0,false )
                .setOngoing( true );
        //发送通知请求
        notificationManager.notify(10, mBuilder.build());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d( TAG,"onCreate" );
    }

    private boolean checkUpdate(){
        int packageCode = packageCode( this );
        String packageName = packageName( this );
        OkHttpClient client = ApiFactory.getClient();
        Request request = new Request.Builder()
                .url(ApiFactory.GANK_IO_CHECK_UPDATE)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            Log.d( TAG,"result:"+result );
            Gson gson = new Gson();
            info = gson.fromJson( result,UpdateInfo.class );
            if(StringUtils.praseToFloat(info.getServerVersion()) > StringUtils.praseToFloat(packageName)){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void downLoadApk(){
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/updateApkFile/";
        Log.d( TAG,"filePath:"+filePath );
        //调用浏览器下载
//        Intent intent = new Intent();
//        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//        intent.setAction("android.intent.action.VIEW");
//        Uri content_url = Uri.parse(ApiFactory.GANK_IO_UPDATE_APK);
//        intent.setData(content_url);
//        startActivity(intent);

        try {
            utils = new DownUtils( info.getUpdateurl(),filePath,5 );
            utils.download();
            mHandler.sendEmptyMessage( MSG_UPDATE_PROGRESS );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    private String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d( TAG,"onLowMemory" );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mHandler != null){
            mHandler.removeCallbacks( null );
        }
    }
}
