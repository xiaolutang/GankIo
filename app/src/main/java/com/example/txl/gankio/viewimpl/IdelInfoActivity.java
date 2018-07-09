package com.example.txl.gankio.viewimpl;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;

import com.example.txl.gankio.R;
import com.example.txl.gankio.adapter.IdelInfoAdapter;
import com.example.txl.gankio.base.BaseActivity;
import com.example.txl.gankio.bean.IdelInfo;
import com.example.txl.gankio.presenter.IdelInfoPersenter;
import com.example.txl.gankio.viewinterface.IGetIdelInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IdelInfoActivity extends BaseActivity implements IGetIdelInfo,SwipeRefreshLayout.OnRefreshListener{

    String id;
    String iconUrl;
    String title;

    int defaultCount = 20;
    int currentPage = 1;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
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

    private void initView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL);
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.list_item_divider);
        decoration.setDrawable( drawable );
        recyclerview.addItemDecoration( new DividerItemDecoration(this,DividerItemDecoration.VERTICAL) );
        recyclerview.setLayoutManager(layoutManager);
        idelInfoAdapter = new IdelInfoAdapter();
        idelInfoPersenter = new IdelInfoPersenter( this );
        recyclerview.setAdapter(idelInfoAdapter  );
        swiperefreshlayout.setOnRefreshListener( this );
        recyclerview.addOnScrollListener( new OnScrollListener(){
            int lastVisibleItem ;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1 == idelInfoAdapter.getItemCount()){
                    idelInfoAdapter.AddFooterItem();
                    idelInfoPersenter.getIdelReaderSubCategory( id,defaultCount,++currentPage,IdelInfoActivity.this,false );
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //最后一个可见的ITEM
                lastVisibleItem=layoutManager.findLastVisibleItemPosition();
            }
        } );
    }

    private void initData(){
        Intent intent = getIntent();
        if(intent == null){
            return;
        }
        id = intent.getStringExtra( "id" );
        iconUrl = intent.getStringExtra( "icon" );
        title = intent.getStringExtra( "title" );
        idelInfoPersenter.getIdelReaderSubCategory( id, defaultCount,currentPage,this,true);
    }

    @Override
    public void onAddIdelInfoSuccess(List<IdelInfo.InfoContent> results) {
        idelInfoAdapter.addData(results  );
    }

    @Override
    public void onAddIdelInfoFailed() {

    }

    @Override
    public void updateIdelInfoSuccess(List<IdelInfo.InfoContent> results) {
        idelInfoAdapter.updateData(results );
        swiperefreshlayout.setRefreshing( false );//刷新完成
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
