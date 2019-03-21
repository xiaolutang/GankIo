package com.example.txl.redesign.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;

/**
 * @author TXL
 * description :主要用来做导航的容器，放其他的fragment
 */
public class NavigationFragment extends BaseFragment {

    /**
     * 根布局
     * */
    private View rootView;
    /**
     * viewpager 指示器
     * */
    private MagicIndicator mCategoryMagicIndicator;
    /**
     * 指示器左边
     * */
    private LinearLayout linearIndicatorLeft;
    /**
     * 指示器右边
     * */
    private LinearLayout linearIndicatorRight;

    /**
     * 顶部导航指示器容器
     * */
    private LinearLayout indicatorContainer;

    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_navigation,null,false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected void initView(){
        viewPager= rootView.findViewById(R.id.vp_navigation);
        mCategoryMagicIndicator = rootView.findViewById(R.id.magic_indicator);
        linearIndicatorLeft = rootView.findViewById(R.id.linear_indicator_left);
        linearIndicatorRight = rootView.findViewById(R.id.linear_indicator_right);
        indicatorContainer = rootView.findViewById(R.id.linear_indicator_container);
    }

    @Override
    protected String getFragmentName() {
        return null;
    }
}
