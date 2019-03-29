package com.example.txl.redesign.fragment.wanandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.txl.gankio.viewimpl.WebActivity;
import com.example.txl.redesign.data.XmlyFmData;
import com.example.txl.redesign.data.wanandroid.WanAndroidBanner;
import com.example.txl.redesign.fragment.xmlyfm.XmlyFmViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;
import com.youth.banner.view.BannerViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TXL
 * description :
 */
public class WanAndroidBannerViewHolder extends XmlyFmViewHolder {
    private Banner banner;
    List<String> bannerTitles;
    List<String> bannerImages;
    public WanAndroidBannerViewHolder(View itemView) {
        super(itemView);
        banner = (Banner) itemView;
    }

    @Override
    public void onBindViewHolder(int position, XmlyFmData xmlyFmData) {
        bannerTitles = new ArrayList<>(  );
        bannerImages = new ArrayList<>(  );
        WanAndroidBanner bannerData = xmlyFmData.getWanAndroidBanner();
        for (WanAndroidBanner.Data data : bannerData.getData()){
            bannerTitles.add( data.getTitle() );
            bannerImages.add( data.getImagePath() );
        }
        banner.setBannerStyle( BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE );
        banner.setImageLoader( new ImageLoaderInterface() {
            @Override
            public void displayImage(Context context, Object path, View imageView) {
                Glide.with( context ).load( path ).into((ImageView) ((CardView) imageView).getChildAt(0));
            }

            @Override
            public View createImageView(Context context) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                CardView cardView = new CardView(context);
                cardView.addView(imageView);
                cardView.setRadius(16);//设置图片圆角的半径大小
//                    cardView.setCardCo();

                cardView.setCardElevation(8);//设置阴影部分大小

//                    cardView.setContentPadding(5,5,5,5);//设置图片距离阴影大
                return cardView;
            }
        } );
        banner.setImages(bannerImages);
        //设置banner动画效果
//            banner.setBannerAnimation( Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(bannerTitles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener( new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent( banner.getContext(), WebActivity.class );
                intent.putExtra( "url",bannerData.getData().get( position ).getUrl() );
                intent.putExtra( "title",bannerData.getData().get( position ).getTitle() );
                banner.getContext().startActivity( intent );
            }
        } );
        try {
            long currentTime = System.nanoTime();
            Field mViewPagerField = Banner.class.getDeclaredField("viewPager");
            mViewPagerField.setAccessible(true);
            BannerViewPager viewPager = (BannerViewPager) mViewPagerField.get(banner);
            Field mLayoutParamsField = View.class.getDeclaredField("mLayoutParams");
            mLayoutParamsField.setAccessible(true);
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mLayoutParamsField.get(viewPager);
            layoutParams.rightMargin = 60;
            layoutParams.leftMargin = 60;
            viewPager.setLayoutParams(layoutParams);
            viewPager.setPageMargin(30);
            Log.d("WanAndroidAdapter"," 反射耗时ms： "+(System.nanoTime()-currentTime));

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
