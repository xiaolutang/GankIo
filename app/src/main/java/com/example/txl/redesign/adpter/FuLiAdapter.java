package com.example.txl.redesign.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.txl.gankio.R;
import com.example.txl.redesign.data.model.NewsData;
import com.example.txl.redesign.utils.GlideUtils;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/25
 * description：
 */
public class FuLiAdapter extends BaseNewsAdapter {
    public FuLiAdapter(Context context) {
        super( context );
    }

    @NonNull
    @Override
    public BaseNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FuliViewHolder(mInflater.inflate( R.layout.beauty_girl_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseNewsViewHolder holder, int position) {
        super.onBindViewHolder( holder, position );
    }

    class FuliViewHolder extends BaseNewsViewHolder{
        ImageView imageView;
        public FuliViewHolder(View itemView) {
            super( itemView );
        }

        @Override
        protected void initView(View itemView) {
            imageView = itemView.findViewById( R.id.image_item );
        }

        @Override
        public void onBindViewHolder(NewsData newsData) {

            GlideUtils.loadImage( context,newsData.getUrl(),imageView,true );
        }
    }
}
