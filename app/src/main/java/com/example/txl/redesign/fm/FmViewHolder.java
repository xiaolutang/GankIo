package com.example.txl.redesign.fm;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.txl.gankio.R;
import com.example.txl.redesign.utils.GlideUtils;
import com.ximalaya.ting.android.opensdk.model.category.Category;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/27
 * description：
 */
public class FmViewHolder extends RecyclerView.ViewHolder {
    ImageView imageIcon;
    TextView tvName;
    public FmViewHolder(View itemView) {
        super( itemView );
        imageIcon = itemView.findViewById( R.id.image_fm_category_icon );
        tvName = itemView.findViewById( R.id.tv_fm_category_name );
    }

    public void onBindViewHolder(Category category){
        GlideUtils.loadImage( imageIcon.getContext(),category.getCoverUrlMiddle(),imageIcon, true);
        tvName.setText( category.getCategoryName() );
    }
}
