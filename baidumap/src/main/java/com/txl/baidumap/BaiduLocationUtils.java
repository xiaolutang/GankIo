package com.txl.baidumap;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.lang.reflect.Field;


public class BaiduLocationUtils {
    public static final int SUCCESS = 1;
    public static final int FAILED = 0;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    Handler handler = new MyHandler(Looper.getMainLooper());
    BaiduLocationListener listener = null;
    static final long Interval=1000*60;
    static long LatestInvokeTime= System.currentTimeMillis();
    boolean flag = false;

    public static boolean overMaxDelay()
    {
        return System.currentTimeMillis()-LatestInvokeTime>Interval;
    }
    public void start(Context context, BaiduLocationListener ls) {
        LatestInvokeTime= System.currentTimeMillis();
        listener = ls;
        if (myListener == null)
            myListener = new MyLocationListener();
        if (mLocationClient == null)
            mLocationClient = new LocationClient(context.getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        int timeOut = 3500;
        option.setTimeOut(timeOut);
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        handler.sendEmptyMessageDelayed(0, timeOut);
    }

    class MyHandler extends Handler {
        MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            removeMessages(0);
            if(msg.what==1)
            {
                if (listener != null && !flag)
                {
                    BaiduLocationModel baiduLocationModel= (BaiduLocationModel) msg.obj;
                    Log.w("LBS","定位成功："+ baiduLocationModel.address.address);
                    listener.getLocationSuccess(baiduLocationModel);
                    flag = true;
                }
            }
            else
            {
                if (listener != null && !flag)
                {
                    Log.w("LBS","定位失败");
                    listener.getLocationFailed();
                    flag = true;
                }
            }

        }
    }

    public void stop() {
        if (mLocationClient != null)
            mLocationClient.stop();
        handler.removeMessages(0);
    }

    public void dispose() {
        stop();
        if (mLocationClient != null && myListener != null) {
            mLocationClient.unRegisterLocationListener(myListener);
        }
        listener = null;
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //只有这两种方式才能取到地址 离线定位只能取到经纬度
            handler.removeMessages(0);
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation)// GPS和网络定位结果
            {
                if (location.hasAddr() && !flag) {
                    final BaiduLocationModel baiduLocationModel = new BaiduLocationModel();
                    baiduLocationModel.location = location;
                    baiduLocationModel.status = SUCCESS;
                    try {
                        Field mAddr = location.getClass().getDeclaredField("mAddr");
                        mAddr.setAccessible(true);
                        baiduLocationModel.address = (Address) mAddr.get(location);
                        baiduLocationModel.longitude = location.getLongitude()+"";
                        baiduLocationModel.latitude = location.getLatitude()+"";
                        if (listener != null)
                        {
                            Message msg=new Message();
                            msg.what=1;
                            msg.obj=baiduLocationModel;
                            handler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(0);
                    }
                }
            }
            else
            {
                handler.sendEmptyMessage(0);
            }
        }
    }

    public interface BaiduLocationListener {
        void getLocationSuccess(BaiduLocationModel baiduLocationModel);

        void getLocationFailed();
    }


    public static class BaiduLocationModel {
        BDLocation location;
        public int status = FAILED;

        public Address address;
        public String longitude;//经度
        public String latitude;//纬度

        public BDLocation getLocation(){
            return location;
        }
    }
}
