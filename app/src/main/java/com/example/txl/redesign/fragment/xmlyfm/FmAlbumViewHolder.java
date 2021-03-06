package com.example.txl.redesign.fragment.xmlyfm;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.txl.gankio.R;
import com.example.txl.redesign.data.XmlyFmData;
import com.example.txl.redesign.fragment.xmlyfm.XmlyFmViewHolder;
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

    private XmlyFmData xmlyFmData;

    public FmAlbumViewHolder(View itemView) {
        super( itemView );
        imageViewIcon = itemView.findViewById( R.id.image_fm_album );
        tvAuthor = itemView.findViewById( R.id.tv_fm_album_author );
        tvDesc = itemView.findViewById( R.id.tv_album_item_des );
        tvReads = itemView.findViewById( R.id.tv_fm_album_reads );
        itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsItemClickHandle.fmItemClick( itemView.getContext(),xmlyFmData );
            }
        } );
    }

    @Override
    public void onBindViewHolder(int position, XmlyFmData data) {
        xmlyFmData= data;
        itemView.setOnClickListener( v -> NewsItemClickHandle.fmItemClick(itemView.getContext(),data) );
        GlideUtils.loadImage( itemView.getContext(),data.getAlbum().getCoverUrlMiddle(),imageViewIcon,true );
        tvAuthor.setText( data.getAlbum().getAnnouncer().getNickname() );
        tvDesc.setText( data.getAlbum().getAlbumIntro() );
        tvReads.setText( data.getAlbum().getPlayCount()+"" );
    }
}
