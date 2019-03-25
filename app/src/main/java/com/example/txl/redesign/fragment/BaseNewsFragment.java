package com.example.txl.redesign.fragment;

import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.redesign.dapter.BaseNewsAdapter;
import com.example.txl.redesign.model.NewsData;
import com.example.txl.redesign.widget.GankSmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

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

    private boolean isSecondFloor = false;
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
        presenter = new BaseNewsPresenter(this,categoryId);
        presenter.start();
        recyclerView = rootView.findViewById( R.id.recycler_view );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getContext(),LinearLayoutManager.VERTICAL ,false);
        recyclerView.setLayoutManager( linearLayoutManager );
        baseNewsAdapter = new BaseNewsAdapter( getContext() );
        recyclerView.setAdapter( baseNewsAdapter );
    }

    @Override
    protected void initData() {
        presenter.refresh();
        presenter.refreshSecondFloor( categoryId );
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
    public void loadMoreFinish(JSONObject jsonObject, boolean hasMore) {
        if(!hasMore){
            smartRefreshLayout.finishLoadMoreWithNoMoreData();
        }else {
            smartRefreshLayout.finishLoadMore();
        }
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
