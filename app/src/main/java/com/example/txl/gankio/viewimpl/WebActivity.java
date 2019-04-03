package com.example.txl.gankio.viewimpl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
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
    protected void setStatusBar() {
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags( WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.navigation_menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected void initView() {
        toolbar.setTitle( getIntent().getStringExtra( "title" ) );
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
        initWebView();
    }

    private void initWebView(){
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
        WebSettings ws = webView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 不缩放
        webView.setInitialScale(100);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    }

    protected void initData() {

        Log.e( TAG,  "url "+url );
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
