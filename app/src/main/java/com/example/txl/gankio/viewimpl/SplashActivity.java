package com.example.txl.gankio.viewimpl;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.example.txl.gankio.App;
import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseActivity;
import com.example.txl.gankio.bean.BeautyGirls;
import com.example.txl.gankio.bean.IdelReaderCategoryRoot;
import com.example.txl.gankio.presenter.MainPresenter;
import com.example.txl.gankio.presenter.FuLiPresenter;
import com.example.txl.gankio.utils.DownUtils;
import com.example.txl.gankio.viewinterface.IGetFuLiData;
import com.example.txl.gankio.viewinterface.IGetMainDataView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements IGetFuLiData{

    public static boolean canGotoMain = true;
    public static IdelReaderCategoryRoot root;

    @BindView( R.id.splash_vp )
    ViewPager viewPager;
    @BindView( R.id.liner_points )
    LinearLayout linearLayoutPoints;

    private List<ImageView> mList=new ArrayList<ImageView>();// 存放要显示在ViewPager对象中的所有Imageview对象
    private List<ImageView> mPointListView = new ArrayList<>(  );

    private int prevPosition = 0;

    FuLiPresenter fuLiPresenter;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        ButterKnife.bind( this );
        if(canGotoMain){
            startActivity(MainActivity.class);
            finish();
        }
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        fuLiPresenter = new FuLiPresenter( this );
        pagerAdapter = new MyViewPagerAdapter();
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

    @Override
    protected void initData() {
        super.initData();
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
        String basePath = getFilesDir().getPath().toString();
        for(BeautyGirls.Girl girl : results){
            DownUtils downUtils = new DownUtils( girl.getUrl(),basePath+ girl.get_id()+".jpg",2 );
            Log.e( TAG,"url === "+girl.getUrl()+"   basePath = "+basePath  );
            new Thread( new Runnable() {
                @Override
                public void run() {
                    try {
                        downUtils.download();
                    } catch (Exception e) {
                        Log.e( TAG,"downUtils " +e);
                        e.printStackTrace();
                    }
                }
            } ).start();
            ImageView imageView = new ImageView( this );
            imageView.setLayoutParams( new RelativeLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT ) );
            Glide.with( App.getAppContext())
                    .load(girl.getUrl())
                    .into(imageView);
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
        pagerAdapter.notifyDataSetChanged();
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
