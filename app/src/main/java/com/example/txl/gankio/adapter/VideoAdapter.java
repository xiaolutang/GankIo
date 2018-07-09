package com.example.txl.gankio.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.txl.gankio.App;
import com.example.txl.gankio.R;
import com.example.txl.gankio.bean.BeautyGirls;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/8
 * description：实际放置的是图片
 */
public class VideoAdapter extends RecyclerView.Adapter {

    String TAG = VideoAdapter.class.getSimpleName();

    List<BeautyGirls.Girl> results = new ArrayList<>(  );

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate( R.layout.beauty_girl_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ImageView imageView = viewHolder.cardView.findViewById( R.id.image_item );
        Glide.with( App.getAppContext())
                .load(results.get( position ).getUrl())
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void updateData(List<BeautyGirls.Girl> infoContents){
        results.clear();
        results.addAll( infoContents );
        Log.e( TAG,"updateData results "+results.size() );
        notifyDataSetChanged();
    }

    public void addData(List<BeautyGirls.Girl> infoContents){
        int start = results.size();
        results.addAll( infoContents );
        notifyItemRangeInserted(start, infoContents.size() );
        Log.e( TAG,"addData results "+results.size() );
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v;
        }
    }

}
