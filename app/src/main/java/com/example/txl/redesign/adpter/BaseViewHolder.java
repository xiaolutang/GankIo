package com.example.txl.redesign.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/25
 * description：
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super( itemView );
    }

    protected abstract void onBindViewHolder(int position);
}
