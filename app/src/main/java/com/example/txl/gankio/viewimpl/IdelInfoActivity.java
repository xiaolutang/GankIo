package com.example.txl.gankio.viewimpl;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.txl.gankio.R;
import com.example.txl.gankio.adapter.IdelInfoAdapter;
import com.example.txl.gankio.base.BaseActivity;
import com.example.txl.gankio.bean.IdelInfo;
import com.example.txl.gankio.presenter.IdelInfoPersenter;
import com.example.txl.gankio.utils.ThemeUtils;
import com.example.txl.gankio.viewinterface.IGetIdelInfo;
import com.example.txl.gankio.widget.PullRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IdelInfoActivity extends BaseActivity implements IGetIdelInfo,SwipeRefreshLayout.OnRefreshListener{

    String id;
    String iconUrl;
    String title;

    int defaultCount = 20;
    int currentPage = 1;

    @BindView(R.id.pullrefrefh_recyclerview)
    PullRefreshRecyclerView recyclerView;

    @BindView( R.id.idel_activity_toolbar )
    Toolbar toolbar;

    IdelInfoPersenter idelInfoPersenter;
    IdelInfoAdapter idelInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_idel_info );
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu_search, menu);
        return super.onCreateOptionsMenu( menu );
    }

    protected void initView(){
        Intent intent = getIntent();
        if(intent == null){
            return;
        }
        id = intent.getStringExtra( "id" );
        iconUrl = intent.getStringExtra( "icon" );
        title = intent.getStringExtra( "title" );

        toolbar.setBackgroundColor( ThemeUtils.getToolBarColor());
        toolbar.setTitle( title );
        setSupportActionBar( toolbar );
        toolbar.setNavigationIcon( R.drawable.icons8_go_back_24);
        toolbar.setNavigationOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        } );

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL);
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.list_item_divider);
        decoration.setDrawable( drawable );
        recyclerView.addItemDecoration( new DividerItemDecoration(this,DividerItemDecoration.VERTICAL) );
        recyclerView.setLayoutManager(layoutManager);
        idelInfoAdapter = new IdelInfoAdapter(this);
        idelInfoPersenter = new IdelInfoPersenter( this );
        recyclerView.setAdapter( idelInfoAdapter );
        recyclerView.setOnPullRefreshListener( new PullRefreshRecyclerView.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e( TAG,"onRefresh" );
                currentPage = 1;
                idelInfoPersenter.getIdelReaderSubCategory( id,defaultCount, currentPage,IdelInfoActivity.this,true);
            }

            @Override
            public void loadMore() {
                Log.e( TAG,"onLoadMore" );
                idelInfoPersenter.getIdelReaderSubCategory( id,defaultCount,++currentPage,IdelInfoActivity.this,false );
            }
        } );
//        recyclerView.setOnRefreshListener( new PullRefreshRecyclerView.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//
//            @Override
//            public void onLoadMore() {
//               ;
//            }
//        } );
//        recyclerView.addOnScrollListener( new OnScrollListener(){
//            int lastVisibleItem ;
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
//                if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1 == idelInfoAdapter.getItemCount()){
//                    idelInfoAdapter.AddFooterItem();
//                    idelInfoPersenter.getIdelReaderSubCategory( id,defaultCount,++currentPage,IdelInfoActivity.this,false );
//                }
//
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                //最后一个可见的ITEM
//                lastVisibleItem=layoutManager.findLastVisibleItemPosition();
//            }
//        } );
    }

    protected void initData(){
        idelInfoPersenter.getIdelReaderSubCategory( id, defaultCount,currentPage,this,true);
    }

    @Override
    public void onAddIdelInfoSuccess(List<IdelInfo.InfoContent> results) {
        idelInfoAdapter.addData(results  );
        recyclerView.setLoadMoreFinish();
    }

    @Override
    public void onAddIdelInfoFailed() {

    }

    @Override
    public void updateIdelInfoSuccess(List<IdelInfo.InfoContent> results) {
        Log.e( TAG,"updateFuLiDataSuccess" );
        idelInfoAdapter.updateData(results );
        recyclerView.setRefreshFinish();
    }

    @Override
    public void updateIdelInfoFailed() {

    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        idelInfoPersenter.getIdelReaderSubCategory( id,defaultCount, currentPage,this,true);
    }
}
