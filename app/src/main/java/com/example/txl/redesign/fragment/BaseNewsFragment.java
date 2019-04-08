package com.example.txl.redesign.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.txl.gankio.R;
import com.example.txl.gankio.viewimpl.WebActivity;
import com.example.txl.redesign.adpter.BaseAdapter;
import com.example.txl.redesign.adpter.BaseNewsAdapter;
import com.example.txl.redesign.data.model.NewsData;

import java.util.List;


/**
 * @author TXL
 * description :
 */
public class BaseNewsFragment extends BaseRefreshFragment<BaseNewsAdapter,NewsContract.Presenter> implements NewsContract.View{

    protected String categoryId;


    @Override
    public String getFragmentName() {
        return TAG;
    }


    @Override
    protected void initView(){
        categoryId = getFragmentArguments().getString("category_id");
        super.initView();
        adapter.setItemClickListener( new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, NewsData newsData) {
                Intent intent = new Intent( getContext(), WebActivity.class );
                intent.putExtra( "url",newsData.getUrl() );
                intent.putExtra( "title",newsData.getDesc() );
                getContext().startActivity( intent );
            }
        } );
    }

    @Override
    protected void onRefresh() {
        presenter.refresh();
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager( getContext(),LinearLayoutManager.VERTICAL ,false);
    }

    protected BaseNewsAdapter getAdapter() {
        return new BaseNewsAdapter( getContext() );
    }

    protected BaseNewsPresenter getPresenter(){
        return new BaseNewsPresenter(this,categoryId);
    }

    @Override
    protected void initData() {
        presenter.refresh();
    }

    @Override
    public void refreshFinish(List<NewsData> dataList, boolean hasMore) {
        adapter.setNewsData( dataList );
        smartRefreshLayout.finishRefresh();
        closeLoadingView();
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
        adapter.addNewsData( dataList );
    }

    @Override
    public void loadMoreError() {
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {

    }
}
