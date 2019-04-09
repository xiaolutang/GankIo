package com.example.txl.redesign.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.example.txl.gankio.R;
import com.example.txl.redesign.data.XmlyFmData;
import com.example.txl.redesign.data.model.NewsData;
import com.example.txl.redesign.fragment.secondfloor.SecondFloorViewHolder;
import com.example.txl.redesign.fragment.secondfloor.WeatherViewHolder;
import com.example.txl.redesign.fragment.xmlyfm.XmlyFmViewHolder;

import java.util.List;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/4/8
 * description：
 */
public class SecondFloorAdapter extends BaseAdapter<XmlyFmData, XmlyFmViewHolder> {
    public SecondFloorAdapter(Context context) {
        super( context );
    }

    public SecondFloorAdapter(Context context, List<XmlyFmData> newsData) {
        super( context, newsData );
    }

    @NonNull
    @Override
    public XmlyFmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == XmlyFmData.GANK_IO_TYPE_ARTICLE){
            View itemView =  mInflater.inflate( R.layout.gank_io_base_news,parent,false );
            return new SecondFloorViewHolder( itemView );
        }else if(viewType == XmlyFmData.TYPE_WEATHER){
            View itemView =  mInflater.inflate( R.layout.item_weather,parent,false );
            return new WeatherViewHolder( itemView );
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull XmlyFmViewHolder holder, int position) {
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null && getItemViewType( position ) == XmlyFmData.GANK_IO_TYPE_ARTICLE){
                    itemClickListener.onItemClick( v,position,getNewsData().get( position ).getNewsData() );
                }
            }
        } );
        holder.onBindViewHolder( position,getNewsData().get( position ) );
    }

    @Override
    public int getItemViewType(int position) {
        return getNewsData().get( position ).getType();
    }
}
