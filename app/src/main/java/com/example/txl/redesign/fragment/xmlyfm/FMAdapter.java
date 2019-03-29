package com.example.txl.redesign.fragment.xmlyfm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.example.txl.gankio.R;
import com.example.txl.redesign.adpter.BaseAdapter;
import com.example.txl.redesign.adpter.BaseViewHolder;
import com.example.txl.redesign.data.XmlyFmData;
import com.example.txl.redesign.fm.FmAlbumViewHolder;

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
        switch (viewType){
            case XmlyFmData.XMLY_TYPE_CATEGORY_LIST:
                return new FmCategoryViewHolder( mInflater.inflate( R.layout.item_fm_categories,parent,false ) );
            case XmlyFmData.XMLY_TYPE_ALBUN_ITEM:
                return new FmAlbumViewHolder( mInflater.inflate( R.layout.item_fm_album,parent,false ) );
        }
       return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position,getNewsData().get(position) );
    }


    @Override
    public int getItemViewType(int position) {
        return listData.get( position ).getType();
    }
}
