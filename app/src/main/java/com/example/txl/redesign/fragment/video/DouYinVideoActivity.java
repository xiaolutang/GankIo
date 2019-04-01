package com.example.txl.redesign.fragment.video;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseActivity;

public class DouYinVideoActivity extends BaseActivity{

    FrameLayout frameLayout;
    VideoFragment videoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dou_yin_video );
        initView();
    }

    private void initView(){
        frameLayout = findViewById( R.id.frame_layout );
        videoFragment = new VideoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager. beginTransaction();
        transaction.replace(R.id.frame_layout, videoFragment);
        transaction.commit();
    }
}
