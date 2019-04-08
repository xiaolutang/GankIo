package com.example.txl.gankio.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.txl.gankio.R;
import com.example.txl.gankio.bean.BeautyGirls;
import com.example.txl.redesign.utils.imageutils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static com.example.txl.redesign.App.*;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/8
 * description：实际放置的是图片
 */
public class FuLiAdapter extends RecyclerView.Adapter {

    String TAG = FuLiAdapter.class.getSimpleName();

    List<BeautyGirls.Girl> results = new ArrayList<>(  );
    SparseArray<Integer> heightArray;

    ImageLoader loader = getImageLoader();

    RecyclerView recyclerView;
    Context context;
    RecyclerView.LayoutManager layoutManager;
    int itemWidth = 0;
    boolean mIsRecyclerViewIdle = true;

    public FuLiAdapter(RecyclerView recyclerView, Context context) {
        this.recyclerView = recyclerView;
        this.context = context;
        recyclerView.addOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                FuLiAdapter.this.onScrollStateChanged( recyclerView, newState);
                super.onScrollStateChanged( recyclerView, newState );
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled( recyclerView, dx, dy );
            }
        } );
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate( R.layout.beauty_girl_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        if(mIsRecyclerViewIdle){
            final ImageView imageView = viewHolder.cardView.findViewById( R.id.image_item );
            if(heightArray == null){
                heightArray = new SparseArray<>(  );
            }
            if(heightArray.get( position ) == null){
                loader.decodeBitmapSize( results.get( position ).getUrl(), new ImageLoader.SourceReady() {
                    @Override
                    public void bitmapSourceReady(int bitmapWidth, int bitmapHeight) {
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                        int realHeight = viewHolder.cardView.getWidth()*bitmapHeight/bitmapWidth;
                        layoutParams.height = realHeight;
                        imageView.setLayoutParams(layoutParams);
                        heightArray.put( position,realHeight );
                        RequestOptions options = new RequestOptions();
                        Glide.with( context ).setDefaultRequestOptions( options.placeholder( R.drawable.image_loading_50 ) ).load(results.get( position ).getUrl()  ).into( imageView );
                        TextView textView = viewHolder.cardView.findViewById( R.id.name_item );
                        String createAt = results.get( position ).getCreatedAt();
                        createAt = createAt.split( "T" )[0];
                        textView.setText( createAt );
                    }
                } );
            }else {
                int height = heightArray.get(position);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams.height = height;
                imageView.setLayoutParams(layoutParams);
                RequestOptions options = new RequestOptions();
                Glide.with( context ).setDefaultRequestOptions( options.placeholder( R.drawable.image_loading_50 ) ).load(results.get( position ).getUrl()  ).into( imageView );
                TextView textView = viewHolder.cardView.findViewById( R.id.name_item );
                String createAt = results.get( position ).getCreatedAt();
                createAt = createAt.split( "T" )[0];
                textView.setText( createAt );
            }
        }
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

    public void onScrollStateChanged(RecyclerView view, int scrollState){
        if(scrollState == RecyclerView.SCROLL_STATE_IDLE){
            mIsRecyclerViewIdle = true;
            notifyDataSetChanged();
        }else {
            mIsRecyclerViewIdle = false;
        }
    }

    public int calculateItemwidth(){
        if(layoutManager != null && itemWidth != 0){
            return itemWidth;
        }
        DisplayMetrics dm = getAppContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof StaggeredGridLayoutManager){
            int spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            itemWidth = (int) (1.0* screenWidth/spanCount);
        }else if(layoutManager instanceof GridLayoutManager){
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            itemWidth = (int) (1.0* screenWidth/spanCount);
        }
        return itemWidth;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v;
        }
    }

}
