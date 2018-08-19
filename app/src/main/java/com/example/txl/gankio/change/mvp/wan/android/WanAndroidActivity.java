package com.example.txl.gankio.change.mvp.wan.android;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseActivity;
import com.example.txl.gankio.change.mvp.data.source.RepositoryFactory;
import com.example.txl.gankio.change.mvp.login.LoginContract;
import com.example.txl.gankio.widget.PullRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WanAndroidActivity extends BaseActivity implements WanAndroidContract.View{

    @BindView( R.id.wan_android_activity_PullRefreshRecyclerView )
    PullRefreshRecyclerView pullRefreshRecyclerView;

    private WanAndroidAdapter androidAdapter;
    private WanAndroidPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_wan_android );
        ButterKnife.bind( this );
        initView();
        initData();
    }

    private void initView(){
        LinearLayoutManager manager = new LinearLayoutManager( this);
        manager.setOrientation(  LinearLayoutManager.VERTICAL );
        pullRefreshRecyclerView.setLayoutManager( manager );
        pullRefreshRecyclerView.addItemDecoration( new DividerItemDecoration( this, DividerItemDecoration.VERTICAL) );
        androidAdapter = new WanAndroidAdapter( this );
        pullRefreshRecyclerView.setAdapter( androidAdapter );
        presenter = new WanAndroidPresenter( this,RepositoryFactory.providerWanAndroidBannerRepository( this ) );
    }

    private void initData(){
        presenter.start();
    }

    @Override
    public void loadMoreFinish(List<IDataModel> iDataModels) {
        androidAdapter.loadMore( iDataModels );
        pullRefreshRecyclerView.setLoadMoreFinish();
    }

    @Override
    public void loadMoreFailed() {

    }

    @Override
    public void loadBannerFinish(List<IDataModel> iDataModels) {
        androidAdapter.refresh( iDataModels);
        pullRefreshRecyclerView.setRefreshFinish();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }
}
