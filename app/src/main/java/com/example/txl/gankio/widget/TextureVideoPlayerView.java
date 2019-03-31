package com.example.txl.gankio.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.txl.gankio.R;
import com.example.txl.redesign.player.SimpleAndroidPlayer;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/8
 * description：
 */
public class TextureVideoPlayerView extends FrameLayout implements View.OnClickListener{
    private static final String TAG = "TextureVideoPlayerView";

    // FIXME: 2018/8/11 播放器不应该放在这里
    private SimpleAndroidPlayer simpleAndroidPlayer;

    private ImageView backImage;
    private TextureView mTextureView;
    private ViewGroup mContainer;
    private ImageView togglePlayerImageView;
    private TextView descTextView;
    //进度条
    //播控按钮
    //desc描述

    public TextureVideoPlayerView(@NonNull Context context) {
        this( context ,null);
    }

    public TextureVideoPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this( context, attrs, 0);
    }

    public TextureVideoPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super( context, attrs, defStyleAttr );
        initView( context );
    }

    private void initView(Context context){
        backImage = new ImageView( context );
        backImage.setScaleType( ImageView.ScaleType.FIT_XY );
        this.addView( backImage, new LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        mTextureView = new TextureView( context );
        this.addView(mTextureView, new LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        descTextView = new TextView( context );
        descTextView.setTextColor( Color.WHITE );
        descTextView.setTextSize( 18 );
        descTextView .setTypeface(Typeface.defaultFromStyle( Typeface.BOLD));
        LayoutParams params = new FrameLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        params.gravity = Gravity.BOTTOM | Gravity.LEFT;
        params.bottomMargin = 45;
        params.leftMargin = 30;
        this.addView( descTextView, params);

        mContainer = new FrameLayout( context );
        mContainer.setBackgroundColor( Color.alpha( 0xffCC00FF ) );
        mContainer.setClickable( true );
        mContainer.setOnClickListener( this );
        this.addView( mContainer, new FrameLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        togglePlayerImageView = new ImageView( context );
        togglePlayerImageView.setImageResource( R.drawable.icons_stop_play_64 );
        togglePlayerImageView.setClickable( true );
        togglePlayerImageView.setOnClickListener( this );
        togglePlayerImageView.setVisibility( GONE );
        LayoutParams layoutParams = new LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        layoutParams.gravity = Gravity.CENTER;
        mContainer.addView( togglePlayerImageView, layoutParams );

    }

    public ImageView getBackImage(){
        return backImage;
    }

    public TextureView getTextureView(){
        return mTextureView;
    }


    public void setSurfaceTextureListener(TextureView.SurfaceTextureListener listener){
        if(mTextureView == null){
            return;
        }
        mTextureView.setSurfaceTextureListener( listener );
    }

    public void addPlayer(SimpleAndroidPlayer player){
        simpleAndroidPlayer = player;
    }

    @Override
    public void onClick(View v) {
        if(simpleAndroidPlayer == null){
            return;
        }
        if(v == togglePlayerImageView){

        }else if(v == mContainer){
            showMask();
            if(simpleAndroidPlayer.isPlaying()){
                simpleAndroidPlayer.pause();
                togglePlayerImageView.setImageResource( R.drawable.icon_start_play_64 );
            }else {
                simpleAndroidPlayer.play();
                togglePlayerImageView.setImageResource( R.drawable.icons_stop_play_64 );
                disMissMask();
            }

        }
    }

    /**
     * 遮罩层消失
     * */
    public void disMissMask(){
        togglePlayerImageView.setVisibility( GONE );
    }

    private void showMask(){
        togglePlayerImageView.setVisibility( VISIBLE );
    }

    public void setDescString(String desc){
        descTextView.setText( desc );
    }
}
