package com.example.txl.gankio.viewimpl;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity {

    @BindView( R.id.web_view )
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_web );
        ButterKnife.bind( this );
        initData();
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        webView.loadUrl( intent.getStringExtra( "url" ) );
    }
}
