package com.example.txl.redesign.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.redesign.adpter.BaseNewsAdapter;
import com.example.txl.redesign.data.model.NewsData;
import com.example.txl.redesign.widget.GankSmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.util.List;


/**
 * @author TXL
 * description :
 */
public class BaseNewsFragment extends BaseFragment implements NewsContract.View{

    protected GankSmartRefreshLayout smartRefreshLayout;
    protected RecyclerView recyclerView;
    protected NewsContract.Presenter presenter;
    /**
     * 二楼效果
     * */
    protected TwoLevelHeader twoLevelHeader;
    protected ImageView twoLevelContentImage;
    protected ImageView twoLevelImage;

    protected String categoryId;

    protected BaseNewsAdapter baseNewsAdapter;


    @Override
    protected String getFragmentName() {
        return TAG;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_base_news,container,false);
        return rootView;
    }

    @Override
    protected void initView(){
        smartRefreshLayout = rootView.findViewById( R.id.smart_refresh_layout );
        categoryId = getFragmentArguments().getString("category_id");
        presenter = getPresenter();
        presenter.start();
        recyclerView = rootView.findViewById( R.id.recycler_view );
        RecyclerView.LayoutManager linearLayoutManager = getLayoutManager();
        recyclerView.setLayoutManager( linearLayoutManager );
        baseNewsAdapter = getAdapter();
        recyclerView.setAdapter( baseNewsAdapter );
        smartRefreshLayout.setOnRefreshListener( new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.refresh();
            }
        } );
        smartRefreshLayout.setOnLoadMoreListener( new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadMore();
            }
        } );
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager( getContext(),LinearLayoutManager.VERTICAL ,false);
    }

    protected BaseNewsAdapter getAdapter() {
        return new BaseNewsAdapter( getContext() );
    }

    protected NewsContract.Presenter getPresenter(){
        return new BaseNewsPresenter(this,categoryId);
    }

    @Override
    protected void initData() {
        presenter.refresh();
    }

    @Override
    public void refreshFinish(List<NewsData> dataList, boolean hasMore) {
        baseNewsAdapter.setNewsData( dataList );
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void refreshError() {
        smartRefreshLayout.finishRefresh(false);
    }

    @Override
    public void loadMoreFinish(List<NewsData> dataList, boolean hasMore) {
        if(!hasMore){
            smartRefreshLayout.finishLoadMoreWithNoMoreData();
        }else {
            smartRefreshLayout.finishLoadMore();
        }
        baseNewsAdapter.addNewsData( dataList );
    }

    @Override
    public void loadMoreError() {
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    public void secondFloorFinish(JSONObject jsonObject) {

    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {

    }
}
