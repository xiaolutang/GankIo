package com.example.txl.redesign.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.txl.gankio.R;
import com.example.txl.redesign.data.model.NewsData;

import java.util.List;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/24
 * description：
 */
public class BaseNewsAdapter extends BaseAdapter<NewsData,BaseNewsViewHolder>{

    public BaseNewsAdapter(Context context) {
        this(context,null);
    }

    public BaseNewsAdapter(Context context, List<NewsData> newsData) {
        super(context,newsData);
    }

    @NonNull
    @Override
    public BaseNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d( "onCreateViewHolder  ",viewType +"");
        View itemView =  mInflater.inflate( R.layout.gank_io_base_news,parent,false );
        return new BaseNewsViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder(@NonNull BaseNewsViewHolder holder, int position) {
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            final int currentPosition = position;
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onItemClick( v,currentPosition,listData.get( currentPosition ) );
                }
            }
        } );
        holder.onBindViewHolder(listData.get( position ));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType( position );
    }
}
