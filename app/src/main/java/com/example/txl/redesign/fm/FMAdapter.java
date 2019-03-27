package com.example.txl.redesign.fm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.example.txl.gankio.R;
import com.example.txl.redesign.adpter.BaseAdapter;
import com.ximalaya.ting.android.opensdk.model.category.Category;

import java.util.List;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/27
 * description：
 */
public class FMAdapter<T> extends BaseAdapter<T, FmViewHolder> {

    public FMAdapter(Context context) {
        super( context );
    }

    public FMAdapter(Context context, List listData) {
        super( context, listData );
    }


    @NonNull
    @Override
    public FmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FmViewHolder( mInflater.inflate( R.layout.item_fm_category,parent,false ) );
    }

    @Override
    public void onBindViewHolder(@NonNull FmViewHolder holder, int position) {
        holder.onBindViewHolder( (Category) listData.get( position ) );
    }


}
