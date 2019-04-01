package com.example.txl.gankio.adapter;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.txl.gankio.R;
import com.example.txl.redesign.adpter.BaseAdapter;
import com.example.txl.redesign.adpter.BaseViewHolder;
import com.example.txl.redesign.data.VideoBean;
import com.example.txl.redesign.player.SimpleAndroidPlayer;
import com.example.txl.redesign.player.SimpleAndroidPlayerManager;
import com.example.txl.gankio.widget.TextureVideoPlayerView;
import com.example.txl.redesign.utils.GlideUtils;



/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/8
 * description：
 */
public class VideoAdapter extends BaseAdapter<VideoBean.VideoInfo, VideoAdapter.ViewHolder> {
    private static final String TAG = VideoAdapter.class.getSimpleName();
    public VideoAdapter(Context context) {
        super( context );
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextureVideoPlayerView textureVideoPlayerView = (TextureVideoPlayerView) LayoutInflater.from(parent.getContext())
                .inflate( R.layout.video_item_textrueplayerview, parent, false);
        return new ViewHolder(textureVideoPlayerView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d( TAG,"VideoActivity  "+"onBindViewHolder" );
        holder.onBindViewHolder( position,getNewsData().get( position ) );
    }



    public static class ViewHolder extends BaseViewHolder<VideoBean.VideoInfo> {
        SimpleAndroidPlayer simpleAndroidPlayer;
        private TextureVideoPlayerView textureVideoPlayerView;
        private ViewHolder(View v) {
            super(v);
            textureVideoPlayerView = (TextureVideoPlayerView) v;
        }

        @Override
        public void onBindViewHolder(int position, VideoBean.VideoInfo data) {
            ImageView backImage = textureVideoPlayerView.getBackImage();
            textureVideoPlayerView.setDescString(data.getDesc());
            new GlideUtils.GlideUtilsBuilder()
                    .useDefaultPlaceholder( true )
                    .setImageView( backImage )
                    .setContext( itemView.getContext() )
                    .setUrl( data.getContent().getCover() )
                    .load();
            initPlayer(data.getContent().getPlayAddr());
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
                    textureVideoPlayerView.disMissMask();
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

                }
            } );
        }
    }
}
