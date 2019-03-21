package com.example.txl.redesign.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONObject;


/**
 * @author TXL
 * description :
 */
public class BaseNewsFragment extends BaseFragment implements NewsContract.View{

    protected SmartRefreshLayout smartRefreshLayout;
    protected RecyclerView recyclerView;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        initView();
    }

    protected void initView(){
        smartRefreshLayout = rootView.findViewById( R.id.smart_refresh_layout );
        recyclerView = rootView.findViewById( R.id.recycler_view );
    }

    @Override
    public void refreshFinish(JSONObject jsonObject, boolean hasMore) {

    }

    @Override
    public void refreshError() {

    }

    @Override
    public void loadMoreFinish(JSONObject jsonObject, boolean hasMore) {

    }

    @Override
    public void loadMoreError() {

    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {

    }
}
