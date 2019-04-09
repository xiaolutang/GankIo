package com.example.txl.redesign.fragment.secondfloor;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.txl.gankio.R;
import com.example.txl.redesign.data.XmlyFmData;
import com.example.txl.redesign.data.model.NewsData;
import com.example.txl.redesign.fragment.xmlyfm.XmlyFmViewHolder;
import com.example.txl.redesign.utils.GlideUtils;

import java.util.List;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/4/8
 * description：
 */
public class SecondFloorViewHolder extends XmlyFmViewHolder {
    protected TextView tvTitle;
    protected TextView tvAuthor;
    protected TextView tvNewsType;
    protected CheckBox checkBoxLike;
    protected CheckBox checkBoxCollect;
    protected TextView tvPublishTime;
    protected ImageView imageTitle;
    protected RecyclerView recyclerViewImages;

    public SecondFloorViewHolder(View itemView) {
        super( itemView );
        initView( itemView );
    }

    protected void initView(View itemView) {
        tvTitle = itemView.findViewById( R.id.tv_article_title );
        tvAuthor = itemView.findViewById( R.id.tv_article_author );
        checkBoxLike = itemView.findViewById( R.id.rb_article_zan );
        checkBoxCollect = itemView.findViewById( R.id.rb_article_collect );
        tvPublishTime = itemView.findViewById( R.id.tv_article_publish_time );
        imageTitle = itemView.findViewById( R.id.image_title );
        tvNewsType = itemView.findViewById( R.id.tv_news_type );
        recyclerViewImages = itemView.findViewById( R.id.recycler_view_images );
    }

    @Override
    public void onBindViewHolder(int position, XmlyFmData xmlyFmData){
        NewsData newsData = xmlyFmData.getNewsData();
        tvTitle.setText( newsData.getDesc() );
        tvAuthor.setText( "作者："+newsData.getWho() );
        checkBoxLike.setChecked( false );
        checkBoxCollect.setChecked( false );
        tvPublishTime.setText( "创建于："+newsData.getPublishedAt() );
        tvNewsType.setText("类型："+ newsData.getType() );
        recyclerViewImages.setVisibility( View.GONE );
        if(newsData.getImages() == null || newsData.getImages().size() == 0){
            imageTitle.setVisibility( View.GONE );
        }else if(newsData.getImages().size() %3 != 0){
            GlideUtils.loadImage(imageTitle.getContext(),newsData.getImages().get( 0 ),imageTitle,null);
            imageTitle.setVisibility( View.VISIBLE );
        }else {
            imageTitle.setVisibility( View.GONE );
            recyclerViewImages.setVisibility( View.VISIBLE );
            int spanCount = 3;

            GridLayoutManager glm = new GridLayoutManager( imageTitle.getContext(),spanCount );
            glm.setOrientation( GridLayoutManager.VERTICAL );
            recyclerViewImages.setLayoutManager( glm );
            recyclerViewImages.setAdapter( new GridImageAdapter( newsData.getImages() ) );
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvTitle.getLayoutParams();
            params.addRule( RelativeLayout.BELOW,R.id.recycler_view_images );
            tvTitle.setLayoutParams( params );
        }
    }

    private class GridImageAdapter extends RecyclerView.Adapter<ImageViewHolder>{

        private List<String> images;

        public GridImageAdapter(List<String> images) {
            this.images = images;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView( parent.getContext() );
            imageView.setScaleType( ImageView.ScaleType.CENTER_CROP );
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, (int) parent.getResources().getDimension( R.dimen.dp_90 ) );
            params.bottomMargin = (int) parent.getContext().getResources().getDimension( R.dimen.dp_6 );
            params.topMargin = (int) parent.getContext().getResources().getDimension( R.dimen.dp_6 );
            params.leftMargin = (int) parent.getContext().getResources().getDimension( R.dimen.dp_6 );
            params.rightMargin = (int) parent.getContext().getResources().getDimension( R.dimen.dp_6 );
            imageView.setLayoutParams( params );
            return new ImageViewHolder( imageView );
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            GlideUtils.loadImage( itemView.getContext(),images.get( position ), holder.imageView,null );
        }

        @Override
        public int getItemCount() {
            if(images == null){
                return 0;
            }
            return Math.min( 3,images.size() );
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super( itemView );
            imageView = (ImageView) itemView;
        }

    }
}
