package com.example.txl.gankio.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.txl.gankio.App;
import com.example.txl.gankio.R;
import com.example.txl.gankio.bean.CommonIdelReaderSubclassification;
import com.example.txl.gankio.viewimpl.IdelInfoActivity;


import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/7
 * description：
 */
public class CommonSubIdelReaderAdapter extends RecyclerView.Adapter {


    String type;
    Context mContext;

    List<CommonIdelReaderSubclassification.Content> contents = new ArrayList<>(  );

    public CommonSubIdelReaderAdapter(String type, Context mContext) {
        this.type = type;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            default:
                RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                        .inflate( R.layout.idel_reader_content_item, parent, false);
                return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType( position );
        switch (viewType){
            default:
                CommonIdelReaderSubclassification.Content content = contents.get( position );
                ViewHolder myViewHolder = (ViewHolder) holder;
                ImageView sourceImage = myViewHolder.mRelativeLayout.findViewById( R.id.iv_content_source );
                Glide.with( App.getAppContext())
                        .load(content.getIcon())
                        .into(sourceImage);
                TextView titleTv = myViewHolder.mRelativeLayout.findViewById( R.id.tv_content_title );
                titleTv.setText( content.getTitle() );
                TextView timeTv = myViewHolder.mRelativeLayout.findViewById( R.id.tv_content_time );
                timeTv.setText( content.getCreated_at() );
                myViewHolder.mRelativeLayout.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent( mContext, IdelInfoActivity.class);
                        intent.putExtra( "icon" ,content.getIcon());
                        intent.putExtra( "title" ,content.getTitle());
                        intent.putExtra( "id" ,content.getId());
                        mContext.startActivity( intent );
                    }
                } );

        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType( position );
    }

    public void updateContent(List<CommonIdelReaderSubclassification.Content> contents){
        this.contents.clear();
        this.contents.addAll( contents );
    }

    public void addContent(List<CommonIdelReaderSubclassification.Content> contents){
        this.contents.addAll( contents );
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout mRelativeLayout;
        public ViewHolder(View v) {
            super(v);
            mRelativeLayout = (RelativeLayout) v;
        }
    }

}
