package com.example.txl.redesign.fragment.xmlyfm.album;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseActivity;
import com.example.txl.redesign.utils.GlideUtils;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

public class TrackListActivity extends BaseActivity implements TrackListContract.View<TrackList>, IXmPlayerStatusListener {

    TrackListContract.Presenter presenter;
    XmPlayerManager xmPlayerManager;
    private Album album;
    private TrackList mListTracks;
    private Track track;

    View rootView;
    TextView tvMusicName;
    TextView tvAuthorName;
    TextView tvPlayModel;
    ImageView imagePreMusic;
    ImageView imageNextMusic;
    ImageView imageToggleMusic;
    ImageView imageMusicAuthorIcon;
    ImageView imageShowProgram;
    SeekBar seekBar;

    Animation animation ;


    public TrackListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_track_list );
        initView();
        initData();
    }

    private void initData() {
        try {
            album = getIntent().getParcelableExtra( "album" );
            if(album == null){
                throw new RuntimeException( "initData album is null please check" );
            }
            Log.d( TAG,"initData  "+album );
            presenter = new TrackListPresenter( this,album );
        }catch (Exception e){
            e.printStackTrace();
        }
        presenter.refresh();
    }

    private void initView() {
        rootView = findViewById( R.id.tract_list_root );
        xmPlayerManager = XmPlayerManager.getInstance( this );
        xmPlayerManager.addPlayerStatusListener( this );
        tvMusicName = findViewById( R.id.tv_music_name );
        tvAuthorName = findViewById( R.id.tv_author_name );
        tvPlayModel = findViewById( R.id.tv_play_model );
        imageShowProgram = findViewById( R.id.image_show_program );
        imageShowProgram.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgram();
            }
        } );
        imageMusicAuthorIcon = findViewById( R.id.image_music_author );
        imagePreMusic = findViewById( R.id.image_pre_music );
        imagePreMusic.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(0);
                xmPlayerManager.playPre();
            }
        } );
        imageNextMusic = findViewById( R.id.image_next_music );
        imageNextMusic.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(0);
                xmPlayerManager.playNext();
            }
        } );
        imageToggleMusic = findViewById( R.id.image_toggle_music );
        imageToggleMusic.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(xmPlayerManager.isPlaying()){
                    xmPlayerManager.pause();
                }else {
                    xmPlayerManager.play();
                }
            }
        } );
        seekBar = findViewById(R.id.music_seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG,"onProgressChanged  progress :"+progress+"  fromUser: "+fromUser);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG,"onStartTrackingTouch  ");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG,"onStopTrackingTouch  ");
                if(track != null && track.getDuration() != 0){
                    long pos = seekBar.getProgress();
                    xmPlayerManager.seekTo( (int) pos );
                }
            }
        });
    }

    private void showProgram() {

    }

    /**
     * 可以抽取为播放器ui的通用方法
     * */
    private void togglePlayerUi(boolean isPlay){
        if(isPlay){
            imageToggleMusic.setImageResource( R.drawable.image_pause );
        }else {
            imageToggleMusic.setImageResource( R.drawable.image_play );
        }
    }

    @Override
    public void onRefreshSuccess(TrackList data) {
        Log.d( TAG,"onRefreshSuccess " +data.getTracks().get( 0 ));
        track = data.getTracks().get( 0 );
        resetUi(track);
        if(mListTracks == null){
            mListTracks = data;
            xmPlayerManager.playList(data,0);
        }else {
            mListTracks.updateCommonParams( data );
        }
    }

    private void resetUi(Track track){
        new GlideUtils.GlideUtilsBuilder()
                .setContext( this )
                .setImageView( imageMusicAuthorIcon )
                .setPlaceholder( ContextCompat.getDrawable( this,R.drawable.easy_player_icon ) )
                .setUrl( track.getCoverUrlLarge() )
                .isCircle( true )
                .load();
        tvMusicName.setText(track.getTrackTitle());
        tvAuthorName.setText(album.getAnnouncer().getNickname());
        new GlideUtils.GlideUtilsBuilder()
                .setContext( this )
                .setUrl( track.getCoverUrlMiddle() )
                .setBlur( true )
                .setRequestListener( new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.d( TAG,"onLoadFailed" );
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        rootView.setBackground( resource );
                        Log.d( TAG,"onResourceReady" );
                        return true;
                    }
                } )
                .loadAsDrawable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(animation != null){
            animation.cancel();
        }
    }

    @Override
    public void onRefreshFailed() {

    }

    @Override
    public void onLoadMoreSuccess(TrackList data, boolean hasMore) {
        mListTracks.updateCommonTrackList( data );
    }

    @Override
    public void onLoadMoreFailed() {

    }

    @Override
    public void setPresenter(TrackListContract.Presenter presenter) {

    }

    @Override
    public void onPlayStart() {
        Log.d( TAG,"onPlayStart" );
        togglePlayerUi(true);
        if(animation != null){
            animation.reset();
        }else {
            animation = AnimationUtils.loadAnimation( this,R.anim.rotate_animation );
        }
        imageMusicAuthorIcon.startAnimation(animation );
    }

    @Override
    public void onPlayPause() {
        Log.d( TAG,"onPlayPause" );
        togglePlayerUi(false);
    }

    @Override
    public void onPlayStop() {
        Log.d( TAG,"onPlayStop" );
        togglePlayerUi(false);
    }

    @Override
    public void onSoundPlayComplete() {
        Log.d( TAG,"onSoundPlayComplete" );
    }

    @Override
    public void onSoundPrepared() {
        Log.d( TAG,"onSoundPrepared" );
    }

    /**
     * 切歌
     * */
    @Override
    public void onSoundSwitch(PlayableModel playableModel, PlayableModel playableModel1) {
        Log.d( TAG,"onSoundSwitch" );
        for (Track trackItem: mListTracks.getTracks()){
            if(playableModel1.equals( trackItem )){
                track = trackItem;
                resetUi( track );
                return;
            }
        }
    }

    @Override
    public void onBufferingStart() {
        Log.d( TAG,"onBufferingStart" );
    }

    @Override
    public void onBufferingStop() {
        Log.d( TAG,"onBufferingStop" );
    }

    @Override
    public void onBufferProgress(int i) {
        Log.d( TAG,"onBufferProgress" );
    }

    @Override
    public void onPlayProgress(int currPos, int duration) {
        Log.d( TAG,"onPlayProgress" );
        if(duration != 0){
            seekBar.setMax( (duration));
            seekBar.setProgress(currPos);
        }
    }

    @Override
    public boolean onError(XmPlayerException e) {
        Log.d( TAG,"onError" );
        e.printStackTrace();
        return false;
    }
}
