package com.example.txl.redesign.splash;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.example.txl.gankio.widget.BannerAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import com.example.txl.redesign.main.NewStyleMainActivity;
import com.example.txl.redesign.utils.AppExecutors;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;


public class SplashActivity extends BaseActivity implements SplashContract.View{

    public static boolean canGotoMain = false;

    ViewPager viewPager;
    LinearLayout linearLayoutPoints;

    private List<ImageView> mList=new ArrayList<ImageView>();// 存放要显示在ViewPager对象中的所有Imageview对象
    private List<ImageView> mPointListView = new ArrayList<>(  );

    private int prevPosition = 0;

    FuLiPresenter fuLiPresenter;
    BannerAdapter<String> pagerAdapter;
    private SplashContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        if(canGotoMain){
            gotoMain();
        }
        presenter = new SplashPresenter(this);

        checkPermission();
        checkNetState();
        initView();
        checkUpdate();
        AppExecutors.getInstance().diskIO().execute( new Runnable() {
            @Override
            public void run() {
                try {
                    System.loadLibrary("xmopendatacrypto");
                } catch (Exception var1) {
                    Toast.makeText( SplashActivity.this,"xmopendatacrypto加载出错",Toast.LENGTH_SHORT ).show();
                    Log.d( TAG,"xmopendatacrypto加载出错" );
                }
            }
        } );
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
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
        viewPager = findViewById( R.id.splash_vp );
        linearLayoutPoints = findViewById( R.id.liner_points );
        fuLiPresenter = new FuLiPresenter( this );
        pagerAdapter = new BannerAdapter<>(this,viewPager);
        pagerAdapter.setViewLoaderInterface( new BannerAdapter.ViewLoaderInterface() {
            @Override
            public void displayView(Context context, Object path, View imageView) {
                Glide.with( App.getAppContext() ).load( path ).into( (ImageView) imageView );
            }

            @Override
            public View createView(Context context) {
                ImageView imageView = new ImageView( context );
                imageView.setScaleType( ImageView.ScaleType.CENTER_CROP );
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(  ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT );
                imageView.setLayoutParams( params );
                return imageView;
            }
        } );
        viewPager.setAdapter( pagerAdapter );
        viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position<mList.size()){
                    mPointListView.get( prevPosition ).setImageResource( R.drawable.point_white );
                    mPointListView.get( position ).setImageResource( R.drawable.point_gray );
                    prevPosition = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        } );
        viewPager.setOnTouchListener( new View.OnTouchListener() {
            float startX;
            float endX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX=event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX=event.getX();
                        WindowManager windowManager= (WindowManager) getApplicationContext().getSystemService( Context.WINDOW_SERVICE);
//获取屏幕的宽度
                        Point size = new Point();
                        windowManager.getDefaultDisplay().getSize(size);
                        int width=size.x;
//首先要确定的是，是否到了最后一页，然后判断是否向左滑动，并且滑动距离是否符合，我这里的判断距离是屏幕宽度的4分之一（这里可以适当控制）
                        if(prevPosition ==(mList.size()-1)&&startX-endX>=(width/4)){
                            Log.i(TAG,"进入了触摸");
                            canGotoMain = true;
                            gotoMain();
                            return true;
                        }
                }

            return false;
        }} );
    }

    @Override
    public void showDataError() {
        Toast.makeText( this,"加载数据出错！",Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void prepareDataFinish() {
        // FIXME: 2019/3/20 使用广告的模式进行修改
        Toast.makeText( this,"加载数据完成，可以滑动进入主页！",Toast.LENGTH_SHORT ).show();
        gotoMain();
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
            linearLayoutPoints.addView(pointView);
            mPointListView.add( pointView );
        }
        pagerAdapter.update( imageUrls );
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
