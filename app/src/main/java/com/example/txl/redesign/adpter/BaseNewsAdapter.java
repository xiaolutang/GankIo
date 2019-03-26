package com.example.txl.redesign.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
public class BaseNewsAdapter extends RecyclerView.Adapter<BaseNewsViewHolder>{

    protected Context context;
    List<NewsData> newsData;
    protected LayoutInflater mInflater;
    private OnItemClickListener itemClickListener;

    public BaseNewsAdapter(Context context) {
        this(context,null);
    }

    public BaseNewsAdapter(Context context, List<NewsData> newsData) {
        mInflater= LayoutInflater.from( context );
        this.context = context;
        this.newsData = newsData;
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
                    itemClickListener.onItemClick( v,currentPosition,newsData.get( currentPosition ) );
                }
            }
        } );
        holder.onBindViewHolder(newsData.get( position ));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType( position );
    }

    @Override
    public int getItemCount() {
        if(newsData == null){
            return 0;
        }
        return newsData.size();
    }

    public List<NewsData> getNewsData() {
        return newsData;
    }

    public void setNewsData(List<NewsData> newsData) {
        this.newsData = newsData;
        notifyDataSetChanged();
    }

    public void addNewsData(List<NewsData> newsData){
        if(this.newsData == null){
            this.newsData = newsData;
            notifyDataSetChanged();
            return;
        }
        if(newsData == null){
            return;
        }
        this.newsData.addAll( newsData );
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position,NewsData newsData);
    }
}
