package com.example.txl.gankio.change.mvp.wan.android;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.txl.gankio.R;
import com.example.txl.redesign.data.wanandroid.ArticleList;
import com.example.txl.redesign.data.wanandroid.WanAndroidArticle;
import com.example.txl.redesign.data.wanandroid.WanAndroidBanner;
import com.example.txl.gankio.viewimpl.WebActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;
import com.youth.banner.view.BannerViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/18
 * description：
 */
public class WanAndroidAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<IWanAndroidDataModel> result = new ArrayList(  );

    public WanAndroidAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from( mContext );
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case IWanAndroidDataModel.TYPE_BANNER:
                Banner banner = (Banner) mInflater.inflate( R.layout.wan_android_banner,parent,false );
                return new BannerViewHolder(banner);
            case IWanAndroidDataModel.TYPE_ARTICLE:
                ConstraintLayout constraintLayout = (ConstraintLayout) mInflater.inflate( R.layout.wan_android_item_article,parent,false );
                return new ArticleViewHolder( constraintLayout );

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).bindViewHolder(result.get( position ));
    }

    @Override
    public int getItemCount() {
        return result == null ? 0 : result.size();
    }

    @Override
    public int getItemViewType(int position) {
        return result.get( position ).getDataModelType();
    }

    public void loadMore(List<IWanAndroidDataModel> dataModels){
        result.addAll( dataModels );
        notifyDataSetChanged();
    }

    public void refresh(List<IWanAndroidDataModel> dataModels){
        result.clear();
        result.addAll( dataModels );
        notifyDataSetChanged();
    }

    private class BaseViewHolder extends RecyclerView.ViewHolder{

        public BaseViewHolder(View itemView) {
            super( itemView );
        }

        public void bindViewHolder(IWanAndroidDataModel model){
        }
    }

    private class BannerViewHolder extends BaseViewHolder{

        private Banner banner;
        List<String> bannerTitles;
        List<String> bannerImages;
        public BannerViewHolder(View itemView) {
            super( itemView );
            banner = (Banner) itemView;
        }

        @Override
        public void bindViewHolder(IWanAndroidDataModel model) {
            bannerTitles = new ArrayList<>(  );
            bannerImages = new ArrayList<>(  );
            WanAndroidBanner bannerData = (WanAndroidBanner) model;
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
                    Intent intent = new Intent( mContext, WebActivity.class );
                    intent.putExtra( "url",bannerData.getData().get( position ).getUrl() );
                    intent.putExtra( "title",bannerData.getData().get( position ).getTitle() );
                    mContext.startActivity( intent );
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

    private class ArticleViewHolder extends BaseViewHolder{
        ViewGroup rootView;
        TextView author, title, publishTime;
        CheckBox zanButton, collectButton;

        public ArticleViewHolder(View itemView) {
            super( itemView );
            rootView = (ViewGroup) itemView;
            author = itemView.findViewById( R.id.tv_article_author );
            title = itemView.findViewById( R.id.tv_article_title );
            publishTime = itemView.findViewById( R.id.tv_article_publish_time );
            zanButton = itemView.findViewById( R.id.rb_article_zan );
            collectButton = itemView.findViewById( R.id.rb_article_collect );
        }

        @Override
        public void bindViewHolder(IWanAndroidDataModel model) {
            WanAndroidArticle article = (WanAndroidArticle) model;
            author.setText("作者："+ article.getAuthor() );
            title.setText( article.getTitle() );
            publishTime.setText( article.getNiceDate() );
            zanButton.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int zan = article.getZan();
                    if(isChecked){
                        zan++;
                    }else {
                        zan--;
                    }
                    article.setZan( zan );
                    zanButton.setText( "  "+zan );
                }
            } );
            zanButton.setText( "  "+article.getZan() );
            collectButton.setChecked( article.isCollect() );
            collectButton.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    article.setCollect( isChecked );
                }
            } );
            rootView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( mContext, WebActivity.class );
                    intent.putExtra( "url",article.getLink() );
                    intent.putExtra( "title",article.getTitle() );
                    mContext.startActivity( intent );
                }
            } );
        }
    }
}
