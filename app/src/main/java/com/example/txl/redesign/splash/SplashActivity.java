package com.example.txl.redesign.splash;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.txl.redesign.App;
import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseActivity;
import com.example.txl.gankio.bean.BeautyGirls;
import com.example.txl.gankio.presenter.FuLiPresenter;
import com.example.txl.gankio.utils.NetUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import com.example.txl.redesign.main.NewStyleMainActivity;
import com.example.txl.redesign.utils.AppExecutors;
import com.example.txl.redesign.utils.GlobalCacheUtils;
import com.example.txl.redesign.utils.PermissionUtilsKt;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.txl.baidumap.BaiduLocationUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class SplashActivity extends BaseActivity implements SplashContract.View{

    public static boolean canGotoMain = false;

    private List<ImageView> mList=new ArrayList<ImageView>();// 存放要显示在ViewPager对象中的所有Imageview对象
    private List<ImageView> mPointListView = new ArrayList<>(  );

    private int prevPosition = 0;

    FuLiPresenter fuLiPresenter;
    private SplashContract.Presenter presenter;

    private static void accept(Throwable accept) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        if(canGotoMain){
            gotoMain();
        }
        presenter = new SplashPresenter(this);
        initView();
        checkUpdate();
        checkPermission();
    }

    private void getLocation() {
        new BaiduLocationUtils().start(SplashActivity.this, new BaiduLocationUtils.BaiduLocationListener() {
            @Override
            public void getLocationSuccess(BaiduLocationUtils.BaiduLocationModel baiduLocationModel) {
                if(baiduLocationModel.status == BaiduLocationUtils.SUCCESS){
                    Log.d(TAG,"getLocationSuccess address "+baiduLocationModel.address);
                    GlobalCacheUtils.location = baiduLocationModel.getLocation();
                    GlobalCacheUtils.locationAddress = baiduLocationModel.address;
                    GlobalCacheUtils.longitude = baiduLocationModel.longitude;
                    GlobalCacheUtils.latitude = baiduLocationModel.latitude;
                }

            }

            @Override
            public void getLocationFailed() {
                Log.d(TAG,"getLocationFailed ");
            }
        });
    }

    private void gotoMain() {
        startActivity( NewStyleMainActivity.class );
        finish();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
        presenter.prepareSplashData();
        presenter.prepareMainData();
    }

    private void checkPermission(){
        final RxPermissions rxPermissions = new RxPermissions(this);
        String[] splashPermissions = PermissionUtilsKt.getPermissionUtils().getSplashPermission();
        rxPermissions
                .request(splashPermissions)
                .subscribe( new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            // All requested permissions are granted
                            //申请的权限全部允许
                            Toast.makeText(SplashActivity.this, "允许了权限!", Toast.LENGTH_SHORT).show();
                            AppExecutors.getInstance().diskIO().execute( () -> getLocation() );
                        } else {
                            // At least one permission is denied
                            Toast.makeText(SplashActivity.this, "未授权权限，部分功能不能使用", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e( TAG,"checkPermission onError" );
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                } );
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Permission is not granted
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
//            }
//            //申请权限
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
//        }
    }

    private void checkUpdate() {
        Intent intent = new Intent(  );
        intent.setPackage("com.txl.gankio");
        intent.setAction( "com.example.txl.gankio.change.mvp.checkupdate" );
        startService( intent );
    }

    private void checkNetState(){
        if(!NetUtils.isNetworkConnected( this )){
            AlertDialog dialog = null;
            AlertDialog.Builder builder = new AlertDialog.Builder( this, android.R.style.Theme_Material_Light_Dialog_Alert)
                    .setTitle( "当前网络异常！请检查网络哦！" )
                    .setPositiveButton( "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    } );
            dialog  = builder.create();
            dialog.show();
            return;
        }
        if(!NetUtils.isNetworkConnectedByWifi( this )){
            AlertDialog dialog = null;
            AlertDialog.Builder builder = new AlertDialog.Builder( this, android.R.style.Theme_Material_Light_Dialog_Alert)
                    .setTitle( "当前网络不是wifi请确认是否继续使用" )
                    .setPositiveButton( "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    } )
                    .setNegativeButton( "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    } );
            dialog  = builder.create();
            dialog.show();

        }
    }

    protected void initView() {

        fuLiPresenter = new FuLiPresenter( this );
    }

    @Override
    public void showDataError() {
        Toast.makeText( this,"加载数据出错！",Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void prepareDataFinish() {
        // FIXME: 2019/3/20 使用广告的模式进行修改
        Toast.makeText( this,"加载数据完成，可以滑动进入主页！",Toast.LENGTH_SHORT ).show();

    }

    @Override
    public void prepareSplashFinish(JSONObject jsonObject) {
        Gson gson = new Gson();
        BeautyGirls root = gson.fromJson( jsonObject.toString(), BeautyGirls.class);
        mList.clear();
        List<BeautyGirls.Girl> results = root.getResults();
        List<String> imageUrls = new ArrayList<>(  );
        for(BeautyGirls.Girl girl :results ){
            ImageView imageView = new ImageView( this );
            imageView.setLayoutParams( new RelativeLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT ) );
            Glide.with( App.getAppContext())
                    .load(girl.getUrl())
                    .into(imageView);
            imageUrls.add( girl.getUrl() );
            mList.add( imageView );
            ImageView pointView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(5, 0, 5, 0);
            pointView.setLayoutParams(params);
//            //如果当前是第一个 设置为选中状态
//
            if (results.get( 0 ) == girl) {
                pointView.setImageResource(R.drawable.point_gray);
            } else {
                pointView.setImageResource(R.drawable.point_white);
            }
            mPointListView.add( pointView );
        }
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
