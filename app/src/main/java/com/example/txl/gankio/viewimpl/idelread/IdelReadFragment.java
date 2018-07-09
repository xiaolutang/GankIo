package com.example.txl.gankio.viewimpl.idelread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.txl.gankio.R;
import com.example.txl.gankio.adapter.CommonSubIdelReaderAdapter;
import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.gankio.bean.IdelReaderCategoryRoot;
import com.example.txl.gankio.presenter.IdelReaderPresenter;
import com.example.txl.gankio.viewinterface.IGetIdelReadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public class IdelReadFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.navigation_top_tabLayout)
    TabLayout tableLayout;
    @BindView(R.id.navigation_bottom_viewPager)
    ViewPager viewPager;

    MyViewPagerAdapter myViewPagerAdapter;
    CommonSubIdelFragment currentFragment;
    final static List<CommonSubIdelFragment> subFragmengs = new ArrayList<>(  );
    IdelReaderPresenter idelReaderPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.tablayout_and_viewpager, container, false);
        ButterKnife.bind( this,view );
        initView();
        initData();
        return view;
    }

    private void initView(){
        myViewPagerAdapter = new MyViewPagerAdapter( getFragmentManager() );
        viewPager.setAdapter(myViewPagerAdapter) ;
        tableLayout.setupWithViewPager(viewPager,true);

    }

    private void initData(){
        idelReaderPresenter = new IdelReaderPresenter( getContext() );
//        idelReaderPresenter.getIdelReaderSubCategory();
    }

    @Override
    protected String getFragmentName() {
        return "闲读";
    }

    @Override
    public boolean hasMultiFragment() {
        return false;
    }

    @Override
    public void onRefresh() {

    }

    public void updateData(List<IdelReaderCategoryRoot.IdelReaderCategory> readerCategoryList){
        subFragmengs.clear();
        for (IdelReaderCategoryRoot.IdelReaderCategory listItem: readerCategoryList){
            switch (listItem.getName()){
                default:
                    CommonSubIdelFragment subIdelFragment = CommonSubIdelFragment.newInsatance(listItem);
                    subIdelFragment.setFragmentname( listItem.getName() );
                    CommonSubIdelReaderAdapter adapter = new CommonSubIdelReaderAdapter( listItem.getName(),getContext() );
                    subIdelFragment.setRecyclerViewAdapter( adapter );
                    subFragmengs.add(subIdelFragment );
                    break;
            }
        }
        if(myViewPagerAdapter != null){
            myViewPagerAdapter.notifyDataSetChanged();
        }
    }

    final class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return subFragmengs.get( position );
        }


        @Override
        public int getCount() {
            return subFragmengs.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return subFragmengs.get( position ).getFragmentName();
        }
    }
}
