package com.example.txl.redesign.fm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.example.txl.gankio.R;
import com.example.txl.redesign.adpter.BaseAdapter;
import com.example.txl.redesign.adpter.BaseViewHolder;

import java.util.List;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/27
 * description：
 */
public class FMAdapter extends BaseAdapter<XmlyFmData, BaseViewHolder> {

    public FMAdapter(Context context) {
        super( context );
    }

    public FMAdapter(Context context, List listData) {
        super( context, listData );
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(XmlyFmData.TYPE_CATEGORY_LIST == viewType){
            return new FmCategoryViewHolder( mInflater.inflate( R.layout.item_fm_categories,parent,false ) );
        }
       return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position,getNewsData().get(position) );
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
