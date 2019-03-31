package com.example.txl.redesign.api;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.txl.redesign.fragment.xmlyfm.album.TrackListActivity;
import com.example.txl.redesign.player.xmlyfmvideo.MyXmlyPlayerReceiver;
import com.example.txl.redesign.utils.VersionUtils;
import com.ximalaya.ting.android.opensdk.constants.ConstantsOpenSdk;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.appnotification.NotificationColorUtils;
import com.ximalaya.ting.android.opensdk.player.appnotification.XmNotificationCreater;
import com.ximalaya.ting.android.opensdk.util.BaseUtil;

import java.util.Map;

/**
 * @author TXL
 * description :
 */
public class XmlyApi {
    private static final int XMLY_ID = 12569;
    private static final String TAG = "XmlyApi";
    public static void initXMFM(Context context){
        CommonRequest mXimalaya = CommonRequest.getInstanse();
        //考虑搞一个自己的小服务器来存放
        mXimalaya.setAppkey("cac099ed9bf7d296136255aaa3861fa0");
        mXimalaya.setPackid(context.getPackageName());
        mXimalaya.init(context ,"956c9515d1384e084bcc71cc11c5541e");
        ConstantsOpenSdk.isDebug = true;//开启debug模式
        //设置使用https
        CommonRequest.getInstanse().setUseHttps(true);
//        设置不使用https
//        CommonRequest.getInstanse().mNoSupportHttps.add("http://www.baidu.com/request");
        //初始化播放器

        if(VersionUtils.isAndroidOOrHigherN()){
            NotificationColorUtils.isTargerSDKVersion24More = true;
        }
        if(BaseUtil.getCurProcessName(context).contains(":player")) {

            XmNotificationCreater instanse = XmNotificationCreater.getInstanse(context);
            instanse.setNextPendingIntent((PendingIntent)null);
            instanse.setPrePendingIntent((PendingIntent)null);
            instanse.setStartOrPausePendingIntent((PendingIntent)null);

            String actionName = context.getPackageName()+"Action_Close";
            Intent intent = new Intent(actionName);
            intent.setClass(context, MyXmlyPlayerReceiver.class);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 0);
            instanse.setClosePendingIntent(broadcast);
        }
        XmPlayerManager mPlayerManager = XmPlayerManager.getInstance( context );
        Notification mNotification = XmNotificationCreater.getInstanse(context).initNotification(context.getApplicationContext(), TrackListActivity.class);
        mPlayerManager.init((int) System.currentTimeMillis(), mNotification);
    }

    public static void destroyXmlyFm(Context context){
        XmPlayerManager.release();
    }

    /**
     *获取喜马拉雅内容分类
     * */
    public static void getXmlyCategorys(Map<String, String> specificParams, IDataCallBack<CategoryList> callback){
        CommonRequest.getCategories(specificParams, callback);
    }

    /**
     *根据分类和标签获取某个分类某个标签下的专辑列表（最火/最新/最多播放）
     * */
    public static void getAlbumList(Map<String, String> specificParams, IDataCallBack<AlbumList> callback){
        CommonRequest.getAlbumList(specificParams, callback);
    }

    /**
     * 专辑浏览，根据专辑ID获取专辑下的声音列表
     * */
    public static void getTracks(Map<String, String> specificParams, IDataCallBack<TrackList> callback){
        CommonRequest.getTracks(specificParams, callback);
    }
}
