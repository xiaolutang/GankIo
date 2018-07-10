package com.example.txl.gankio.viewimpl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;

import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity {

    String url;

    @BindView( R.id.web_view )
    WebView webView;
    @BindView( R.id.web_activity_toolbar )
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_web );
        ButterKnife.bind( this );
        Intent intent = getIntent();
        url = intent.getStringExtra( "url");
        initView();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void initView() {
        super.initView();
        toolbar.setTitle( url );
        toolbar.setNavigationIcon( R.drawable.icons8_go_back_24 );
        setSupportActionBar( toolbar );
        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
    }

    @Override
    protected void initData() {
        super.initData();

        webView.loadUrl( url );
    }
}
