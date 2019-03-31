package com.example.txl.redesign.utils;

import android.content.Context;
import android.content.Intent;

import com.example.txl.redesign.data.XmlyFmData;
import com.example.txl.redesign.fragment.xmlyfm.album.TrackListActivity;


/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/28
 * description：专门用于处理元素的点击跳转
 */
public class NewsItemClickHandle {

    public static void fmItemClick(Context context, XmlyFmData xmlyFmData, Object... objects){
        switch (xmlyFmData.getType()){
            case XmlyFmData.XMLY_TYPE_CATEGORY_LIST:
            case XmlyFmData.XMLY_TYPE_CATEGORY_ITEM:
                break;
            case XmlyFmData.XMLY_TYPE_ALBUN_ITEM:
                Intent intent = new Intent( context, TrackListActivity.class );
                intent.putExtra( "album",xmlyFmData.getAlbum() );
                context.startActivity( intent );
                break;
            case XmlyFmData.WAN_ANDROID_TYPE_BANNER:
            case XmlyFmData.WAN_ANDROID_TYPE_ARTICLE:
        }
    }
}
