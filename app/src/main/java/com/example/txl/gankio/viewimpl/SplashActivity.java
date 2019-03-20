package com.example.txl.gankio.viewimpl;


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
import android.support.v4.view.PagerAdapter;
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
import com.example.txl.gankio.App;
import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseActivity;
import com.example.txl.gankio.bean.BeautyGirls;
import com.example.txl.gankio.bean.IdelReaderCategoryRoot;
import com.example.txl.gankio.change.mvp.data.WanAndroidBanner;
import com.example.txl.gankio.change.mvp.data.source.IWanAndroidBannerDataSource;
import com.example.txl.gankio.change.mvp.data.source.RepositoryFactory;
import com.example.txl.gankio.presenter.MainPresenter;
import com.example.txl.gankio.presenter.FuLiPresenter;
import com.example.txl.gankio.utils.NetUtils;
import com.example.txl.gankio.viewinterface.IGetFuLiData;
import com.example.txl.gankio.viewinterface.IGetMainDataView;
import com.example.txl.gankio.widget.BannerAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import redesign.api.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity implements IGetFuLiData{

    public static boolean canGotoMain = false;
    public static IdelReaderCategoryRoot root;

    @BindView( R.id.splash_vp )
    ViewPager viewPager;
    @BindView( R.id.liner_points )
    LinearLayout linearLayoutPoints;

    private List<ImageView> mList=new ArrayList<ImageView>();// 存放要显示在ViewPager对象中的所有Imageview对象
    private List<ImageView> mPointListView = new ArrayList<>(  );

    private int prevPosition = 0;

    FuLiPresenter fuLiPresenter;
    BannerAdapter<String> pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        ButterKnife.bind( this );
        if(canGotoMain){
            startActivity(MainActivity.class);
            finish();
        }
        ApiRetrofit.initGankAPi();
        ApiRetrofit.GankIoApi gankIoApi = ApiRetrofit.getGankIoApi();
        gankIoApi.getXianDuCategory().enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                Log.d(TAG,"onResponse sssssss"+response.body().toString());
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.d(TAG,"onFailure sssssss"+t.toString());
            }
        });
        Observer<JSONObject> observer = new Observer<JSONObject>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe : " + d);
            }

            @Override
            public void onNext(JSONObject s) {
                Log.d(TAG, Thread.currentThread().getName() +  "  Item: onNext " + s);
            }
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Error!");
            }

            @Override
            public void onComplete() {

            }
        };
        gankIoApi.getTodayGanHuo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
//        checkPermission();
//        checkNetState();
//        initView();
//        initData();
//        checkUpdate();
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
        intent.setPackage("com.example.txl.gankio");
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
        pagerAdapter = new BannerAdapter<>(this,viewPager);
        pagerAdapter.setViewLoaderInterface( new BannerAdapter.ViewLoaderInterface() {
            @Override
            public void displayView(Context context, Object path, View imageView) {
                Glide.with( App.getAppContext() ).load( path ).into( (ImageView) imageView );
            }

            @Override
            public View createView(Context context) {
                return new ImageView( context );
            }
        } );
        viewPager.setAdapter( pagerAdapter );
        viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPointListView.get( prevPosition ).setImageResource( R.drawable.point_white );
                mPointListView.get( position ).setImageResource( R.drawable.point_gray );
                prevPosition = position;
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
                            startActivity(MainActivity.class);
                            finish();
                            return true;
                        }
                }

            return false;
        }} );
    }

    protected void initData() {
        fuLiPresenter.getFuLiData( 5,1,this,true );
        new MainPresenter(this).prepareMainData( new IGetMainDataView(){
            @Override
            public void getIdelReaderSuccess(IdelReaderCategoryRoot root) {
                SplashActivity.root = root;
            }

            @Override
            public void getIdelReaderFailed(Object o) {

            }

            @Override
            public void getVideoSuccess(Object o) {

            }

            @Override
            public void getVideoFailed(Object o) {

            }

            @Override
            public void getAllDataSuccess(Object o) {

            }

            @Override
            public void getAllDataFailed(Object o) {

            }
        } );
        RepositoryFactory.providerWanAndroidBannerRepository(this).getBannerData( new IWanAndroidBannerDataSource.IBannerDataCallBack() {
            @Override
            public void onBannerDataLoaded(WanAndroidBanner bannerData) {
                for (WanAndroidBanner.Data data: bannerData.getData()){
                    Log.d( TAG,"onBannerDataLoaded url:"+data);
                }
            }

            @Override
            public void onBannerDataLoadFailed() {

            }
        } );
    }

    @Override
    public void onAddFuLiDataSuccess(List<BeautyGirls.Girl> results) {

    }

    @Override
    public void onAddFuLiDataFailed() {

    }

    @Override
    public void updateFuLiDataSuccess(List<BeautyGirls.Girl> results) {
        mList.clear();
        List<String> imageUrls = new ArrayList<>(  );
        for(BeautyGirls.Girl girl : results){
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
//        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateFuLiDataFailed() {

    }

    public class MyViewPagerAdapter extends PagerAdapter {

//当要显示的图片进行缓存时，会调用这个方法进行显示图片的初始化
//我们将要显示的ImageView加入到ViewGroup中
        public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
            container.addView(mList.get(position));
            return mList.get(position);
        }
        @Override
//PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            container.removeView(mList.get(position));
        }
        //获取要滑动的控件的数量，
        public int getCount() {
            // TODO Auto-generated method stub
            return mList.size();
        }

        //来判断显示的是否是同一张照片，这个我们将两个图片对比 再返回
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0==arg1;
        }
    }
}
