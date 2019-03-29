package com.example.txl.redesign.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.txl.redesign.data.model.NewsData;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/27
 * description：
 */
public abstract class BaseAdapter<D,T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    protected Context context;
    protected List<D> listData;
    protected LayoutInflater mInflater;
    protected OnItemClickListener itemClickListener;

    public BaseAdapter(Context context) {
        this(context,null);
    }

    public BaseAdapter(Context context, List<D> listData) {
        this.context = context;
        this.listData = listData;
        mInflater = LayoutInflater.from( context );
    }


    @Override
    public int getItemCount() {
        if(listData == null){
            return 0;
        }
        return listData.size();
    }

    public List<D> getNewsData() {
        return listData;
    }

    public void setNewsData(List<D> newsData) {
        this.listData = newsData;
        notifyDataSetChanged();
    }

    public void addNewsData(List<D> newsData){
        if(this.listData == null){
            this.listData = newsData;
            notifyDataSetChanged();
            return;
        }
        if(newsData == null){
            return;
        }
        this.listData.addAll( newsData );
        notifyDataSetChanged();
    }

    public void addNewsData(int position,D data){
        if(this.listData == null){
            this.listData = new ArrayList<>();
            listData.add(data);
            notifyDataSetChanged();
            return;
        }
        listData.add(position,data);
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position, NewsData newsData);
    }
}
