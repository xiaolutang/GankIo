package com.example.txl.redesign.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import org.json.JSONObject;


/**
 * @author TXL
 * description :
 */
public class BaseNewsFragment extends BaseFragment implements NewsContract.View{

    protected SmartRefreshLayout smartRefreshLayout;
    protected RecyclerView recyclerView;
    protected NewsContract.Presenter presenter;
    /**
     * 二楼效果
     * */
    protected TwoLevelHeader twoLevelHeader;
    protected ImageView twoLevelContentImage;
    protected ImageView twoLevelImage;

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

    @Override
    protected void initView(){
        smartRefreshLayout = rootView.findViewById( R.id.smart_refresh_layout );
        String categoryId = getFragmentArguments().getString("category_id");
        //二楼效果
        if("推荐".equals(categoryId)){
            twoLevelHeader = new TwoLevelHeader(getContext());
            ClassicsHeader classicsHeader = new ClassicsHeader(getContext());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            classicsHeader.setLayoutParams(params);
            twoLevelContentImage = new ImageView(getContext());
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            twoLevelContentImage.setLayoutParams(params);
            twoLevelContentImage.setImageResource(R.drawable.image_secondfloor_content);
            twoLevelImage = new ImageView(getContext());
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            twoLevelImage.setLayoutParams(params);
            twoLevelImage.setImageResource(R.drawable.image_secondfloor);
            twoLevelHeader.addView(twoLevelImage);
            twoLevelHeader.addView(twoLevelContentImage);
            twoLevelHeader.setRefreshHeader(classicsHeader);
            smartRefreshLayout.setRefreshHeader(twoLevelHeader);
        }
        smartRefreshLayout.setOnMultiPurposeListener(new SecondFloorMultiPurposeListener());
//        recyclerView = rootView.findViewById( R.id.recycler_view );
    }

    @Override
    protected void initData() {

    }

    @Override
    public void refreshFinish(JSONObject jsonObject, boolean hasMore) {
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

    /**
     *
     * */
    class SecondFloorMultiPurposeListener extends SimpleMultiPurposeListener{
        @Override
        public void onRefresh(RefreshLayout refreshLayout) {
            super.onRefresh(refreshLayout);
            presenter.refresh();
        }

        @Override
        public void onLoadMore(RefreshLayout refreshLayout) {
            super.onLoadMore(refreshLayout);
            presenter.loadMore();
        }

        @Override
        public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
            twoLevelContentImage.setAlpha(1 - Math.min(percent, 1));
            twoLevelImage.setTranslationY(Math.min(offset - twoLevelImage.getHeight() + twoLevelImage.getHeight(), smartRefreshLayout.getLayout().getHeight() - twoLevelImage.getHeight()));
        }
    }
}
