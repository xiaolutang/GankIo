package com.example.txl.gankio.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.txl.gankio.R;
import com.example.txl.gankio.bean.IdelInfo;
import com.example.txl.gankio.viewimpl.WebActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/7
 * description：
 */
public class IdelInfoAdapter extends RecyclerView.Adapter {

    String TAG = IdelInfoAdapter.class.getSimpleName();
    private Context mContext;

    List<IdelInfo.InfoContent> results = new ArrayList<>(  );

    IdelInfo.InfoContent contentFooter;

    public IdelInfoAdapter(Context context) {
        mContext = context;
        initFooterItem();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate( R.layout.idel_info_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        TextView tvTitle = viewHolder.mRelativeLayout.findViewById( R.id.tv_idel_info_title );
        tvTitle.setText( results.get( position ).getTitle() );
        viewHolder.mRelativeLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( mContext, WebActivity.class );
                intent.putExtra( "url",results.get( position ).getUrl() );
                intent.putExtra( "title",results.get( position ).getTitle() );
                mContext.startActivity( intent );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void AddHeaderItem(){

    }

    public void AddFooterItem(){

        results.add( contentFooter );
        notifyItemInserted( results.size()-1 );
    }


    private void initFooterItem(){
        IdelInfo info = new IdelInfo();
        contentFooter = info.new InfoContent();
        contentFooter.setTitle( "正在加载。。。。。" );
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout mRelativeLayout;
        public ViewHolder(View v) {
            super(v);
            mRelativeLayout = (RelativeLayout) v;
        }
    }

    public void updateData(List<IdelInfo.InfoContent> infoContents){
        results.clear();
        results.addAll( infoContents );
        Log.e( TAG,"updateData results "+results.size() );
        notifyDataSetChanged();
    }

    public void addData(List<IdelInfo.InfoContent> infoContents){
        results.remove( contentFooter );
        int start = results.size();
        notifyItemRemoved( start );
        results.addAll( infoContents );
        notifyItemRangeInserted(start, infoContents.size() );
    }
}
