package com.example.txl.gankio.change.mvp.wan.android;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.txl.gankio.R;
import com.example.txl.gankio.change.mvp.data.ArticleList;
import com.example.txl.gankio.change.mvp.data.WanAndroidBanner;
import com.example.txl.gankio.viewimpl.WebActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;

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
    private List<IDataModel> result = new ArrayList(  );

    public WanAndroidAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from( mContext );
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case IDataModel.TYPE_BANNER:
                Banner banner = (Banner) mInflater.inflate( R.layout.wan_android_banner,parent,false );
                return new BannerViewHolder(banner);
            case IDataModel.TYPE_ARTICLE:
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

    public void loadMore(List<IDataModel> dataModels){
        result.addAll( dataModels );
        notifyDataSetChanged();
    }

    public void refresh(List<IDataModel> dataModels){
        result.clear();
        result.addAll( dataModels );
        notifyDataSetChanged();
    }

    private class BaseViewHolder extends RecyclerView.ViewHolder{

        public BaseViewHolder(View itemView) {
            super( itemView );
        }

        public void bindViewHolder(IDataModel model){
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
        public void bindViewHolder(IDataModel model) {
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
                    Glide.with( context ).load( path ).into( (ImageView) imageView );
                }

                @Override
                public View createImageView(Context context) {
                    return null;
                }
            } );
            banner.setImages(bannerImages);
            //设置banner动画效果
            banner.setBannerAnimation( Transformer.DepthPage);
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
        }
    }

    private class ArticleViewHolder extends BaseViewHolder{
        TextView author, title, publishTime;

        public ArticleViewHolder(View itemView) {
            super( itemView );
            author = itemView.findViewById( R.id.tv_article_author );
            title = itemView.findViewById( R.id.tv_article_title );
            publishTime = itemView.findViewById( R.id.tv_article_publish_time );
        }

        @Override
        public void bindViewHolder(IDataModel model) {
            ArticleList.Data.Article article = (ArticleList.Data.Article) model;
            author.setText( article.getAuthor() );
            title.setText( article.getTitle() );
            publishTime.setText( article.getNiceDate() );
        }
    }
}
