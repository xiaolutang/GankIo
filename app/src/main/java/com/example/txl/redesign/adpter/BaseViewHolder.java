package com.example.txl.redesign.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/25
 * description：
 */
public abstract class BaseViewHolder<D> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super( itemView );
    }

    /**
     * @param position 位置
     * @param data 对应的数据
     * */
    public abstract void onBindViewHolder(int position,D data);
}
