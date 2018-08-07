package com.example.txl.gankio.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.txl.gankio.App;
import com.example.txl.gankio.R;
import com.example.txl.gankio.change.mvp.data.VideoBean;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/8
 * description：
 */
public class VideoAdapter extends RecyclerView.Adapter {
    String TAG = FuLiAdapter.class.getSimpleName();

    List<VideoBean.VideoInfo> results = new ArrayList<>(  );

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate( R.layout.alldata_fragment_item, parent, false);
        return new ViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder linearHolder = (ViewHolder) holder;

        JZVideoPlayerStandard player = linearHolder.mLinearLayout.findViewById( R.id.player_list_video );
//        if (player != null) {
//            player.release();
//        }
//        http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super
//        http://play.g3proxy.lecloud.com/vod/v2/MjUxLzE2LzgvbGV0di11dHMvMTQvdmVyXzAwXzIyLTExMDc2NDEzODctYXZjLTE5OTgxOS1hYWMtNDgwMDAtNTI2MTEwLTE3MDg3NjEzLWY1OGY2YzM1NjkwZTA2ZGFmYjg2MTVlYzc5MjEyZjU4LTE0OTg1NTc2ODY4MjMubXA0?b=259&mmsid=65565355&tm=1499247143&key=f0eadb4f30c404d49ff8ebad673d3742&platid=3&splatid=345&playid=0&tss=no&vtype=21&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super
        player.setUp(results.get( position ).getContent().getPlayAddr(),
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                results.get( position ).getDesc());
        Glide.with( App.getAppContext()).load(results.get( position ).getContent().getCover()).into(player.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void updateData(List<VideoBean.VideoInfo> infoContents){
        results.clear();
        results.addAll( infoContents );
        Log.e( TAG,"updateData results "+results.size() );
        notifyDataSetChanged();
    }

    public void addData(List<VideoBean.VideoInfo> infoContents){
        int start = results.size();
        results.addAll( infoContents );
        notifyItemRangeInserted(start, infoContents.size() );
        Log.e( TAG,"addData results "+results.size() );
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout mLinearLayout;
        public ViewHolder(View v) {
            super(v);
            mLinearLayout = (LinearLayout) v;
        }
    }
}
