package com.example.txl.gankio.viewimpl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

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
    @BindView( R.id.web_activity_progressBar )
    ProgressBar progressBar;

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
                if(webView.canGoBack()){
                    webView.goBack();
                    return;
                }
                finish();
            }
        } );
        webView.setWebViewClient( new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                toolbar.setTitle(request.getUrl().getPath() );
                return super.shouldOverrideUrlLoading( view, request );
            }
        } );
        webView.setWebChromeClient( new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }
            }
        } );
    }

    @Override
    protected void initData() {
        super.initData();

        webView.loadUrl( url );
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(webView.canGoBack() && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            webView.goBack();
            return true;
        }
        return super.dispatchKeyEvent( event );
    }
}
