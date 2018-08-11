package com.example.txl.gankio.adapter;

import android.graphics.SurfaceTexture;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.txl.gankio.App;
import com.example.txl.gankio.R;
import com.example.txl.gankio.change.mvp.data.VideoBean;
import com.example.txl.gankio.player.SimpleAndroidPlayer;
import com.example.txl.gankio.player.SimpleAndroidPlayerManager;
import com.example.txl.gankio.widget.TextureVideoPlayerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/8
 * description：
 */
public class VideoAdapter extends RecyclerView.Adapter {
    private static final String TAG = VideoAdapter.class.getSimpleName();

    List<VideoBean.VideoInfo> results = new ArrayList<>(  );

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextureVideoPlayerView textureVideoPlayerView = (TextureVideoPlayerView) LayoutInflater.from(parent.getContext())
                .inflate( R.layout.video_item_textrueplayerview, parent, false);
        return new ViewHolder(textureVideoPlayerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d( TAG,"VideoActivity  "+"onBindViewHolder" );
        ViewHolder viewHolder = (ViewHolder) holder;
        ImageView backImage = viewHolder.textureVideoPlayerView.getBackImage();
        App.getImageLoader().bindBitmap( results.get( position ).getContent().getCover(),backImage,0,0 );
        viewHolder.initPlayer(results.get( position ).getContent().getPlayAddr());
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
        SimpleAndroidPlayer simpleAndroidPlayer;
        private TextureVideoPlayerView textureVideoPlayerView;
        private ViewHolder(View v) {
            super(v);
            textureVideoPlayerView = (TextureVideoPlayerView) v;
        }

        void initPlayer(String playUrl){
            textureVideoPlayerView.setSurfaceTextureListener( new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                    Log.d( TAG,"PageScrollerRecyclerView VideoActivity  onSurfaceTextureAvailable");
                    simpleAndroidPlayer = SimpleAndroidPlayerManager.newInstance();
                    textureVideoPlayerView.addPlayer( simpleAndroidPlayer );
                    simpleAndroidPlayer.init( textureVideoPlayerView.getContext(),null );
                    simpleAndroidPlayer.setMediaPlaerSurface( new Surface(  textureVideoPlayerView.getTextureView().getSurfaceTexture() ));
                    simpleAndroidPlayer.open( playUrl );
                    simpleAndroidPlayer.play();
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                    Log.d( TAG,"PageScrollerRecyclerView VideoActivity  onSurfaceTextureSizeChanged");
                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    Log.d( TAG,"PageScrollerRecyclerView VideoActivity  onSurfaceTextureDestroyed");
                    if(simpleAndroidPlayer != null && simpleAndroidPlayer.isPlaying()){
                        simpleAndroidPlayer.stop();
                        simpleAndroidPlayer.destroy();
                    }
                    return false;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                    Log.d( TAG,"PageScrollerRecyclerView VideoActivity  onSurfaceTextureUpdated");
                }
            } );
        }
    }
}
