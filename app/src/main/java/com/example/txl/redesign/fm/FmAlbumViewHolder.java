package com.example.txl.redesign.fm;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.txl.gankio.R;
import com.example.txl.redesign.utils.GlideUtils;
import com.example.txl.redesign.utils.NewsItemClickHandle;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/28
 * description：
 */
public class FmAlbumViewHolder extends XmlyFmViewHolder {
    ImageView imageViewIcon;
    /**
     * 作者
     * */
    TextView tvAuthor;
    /**
     * 描述
     * */
    TextView tvDesc;
    /**
     * 阅读数
     * */
    TextView tvReads;
    public FmAlbumViewHolder(View itemView) {
        super( itemView );
        imageViewIcon = itemView.findViewById( R.id.image_fm_album );
        tvAuthor = itemView.findViewById( R.id.tv_fm_album_author );
        tvDesc = itemView.findViewById( R.id.tv_album_item_des );
        tvReads = itemView.findViewById( R.id.tv_fm_album_reads );
    }

    @Override
    public void onBindViewHolder(int position, XmlyFmData data) {
        itemView.setOnClickListener( v -> NewsItemClickHandle.fmItemClick(itemView.getContext(),data) );
        GlideUtils.loadImage( itemView.getContext(),data.getAlbum().getCoverUrlMiddle(),imageViewIcon,true );
        tvAuthor.setText( data.getAlbum().getAnnouncer().getNickname() );
        tvDesc.setText( data.getAlbum().getAlbumIntro() );
        tvReads.setText( data.getAlbum().getPlayCount()+"" );
    }
}
