package com.example.txl.redesign.fm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.txl.gankio.R;
import com.example.txl.redesign.adpter.BaseAdapter;
import com.example.txl.redesign.adpter.BaseViewHolder;
import com.example.txl.redesign.utils.GlideUtils;
import com.ximalaya.ting.android.opensdk.model.category.Category;

import java.util.List;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/27
 * description：
 */
public class FmCategoryViewHolder extends XmlyFmViewHolder {
    RecyclerView recyclerView;
    ItemAdapter adapter;

    public FmCategoryViewHolder(View itemView) {
        super( itemView );
        recyclerView = itemView.findViewById(R.id.recycler_item_fm_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL,false));
    }

    @Override
    public void onBindViewHolder(int position, XmlyFmData data) {
        adapter = new ItemAdapter(itemView.getContext(),data.getCategoryList().getCategories());
        recyclerView.setAdapter(adapter);
    }

    class ItemAdapter extends BaseAdapter<Category, FmCategoryItemViewHolder>{

        public ItemAdapter(Context context) {
            super(context);
        }

        public ItemAdapter(Context context, List<Category> listData) {
            super(context, listData);
        }

        @NonNull
        @Override
        public FmCategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FmCategoryItemViewHolder(mInflater.inflate(R.layout.item_fm_category,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull FmCategoryItemViewHolder holder, int position) {
            holder.onBindViewHolder(position,getNewsData().get(position));
        }
    }

    public class FmCategoryItemViewHolder extends BaseViewHolder<Category>{
        ImageView imageIcon;
        TextView tvName;
        public FmCategoryItemViewHolder(View itemView) {
            super(itemView);
            imageIcon = itemView.findViewById( R.id.image_fm_category_icon );
            tvName = itemView.findViewById( R.id.tv_fm_category_name );
        }

        @Override
        public void onBindViewHolder(int position, Category data) {
            GlideUtils.loadImage( imageIcon.getContext(),data.getCoverUrlMiddle(),imageIcon, true);
            tvName.setText( data.getCategoryName() );
        }
    }
}
