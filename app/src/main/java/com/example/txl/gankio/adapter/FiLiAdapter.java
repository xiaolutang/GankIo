package com.example.txl.gankio.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
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
public class FiLiAdapter extends RecyclerView.Adapter {

    String TAG = FiLiAdapter.class.getSimpleName();

    List<BeautyGirls.Girl> results = new ArrayList<>(  );
    SparseArray<Integer> heightArray;

    public FiLiAdapter() {
    }

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
        if(heightArray == null){
            heightArray = new SparseArray<>(  );
        }
        if (heightArray.get(position) == null){
            Glide.with(App.getAppContext())
                    .asBitmap()
                    .load(results.get( position ).getUrl())
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            int height = bitmap.getHeight(); //获取bitmap信息，可赋值给外部变量操作，也可在此时行操作。
                            int width = bitmap.getWidth();
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                            int realHeight = viewHolder.cardView.getWidth()*height/width;
                            layoutParams.height = realHeight;
                            imageView.setLayoutParams(layoutParams);
                            heightArray.put( position,realHeight );
                        }

                    });
        }else {
            int height = heightArray.get(position);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.height = height;
            imageView.setLayoutParams(layoutParams);
        }

        Glide.with(App.getAppContext())
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
