package com.example.txl.redesign.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.redesign.adpter.CategoryFragmentAdapter;
import com.example.txl.redesign.data.MainNavigation;
import com.example.txl.redesign.data.Navigation;
import com.example.txl.redesign.fragment.fuli.FuliFragment;
import com.example.txl.redesign.fragment.secondfloor.SecondFloorNewsFragment;
import com.example.txl.redesign.fragment.video.DouYinVideoActivity;
import com.example.txl.redesign.utils.GlideUtils;
import com.example.txl.redesign.utils.GlobalCacheUtils;
import com.example.txl.redesign.utils.TextSelectUtils;
import com.example.txl.redesign.widget.GankViewPager;
import com.example.txl.redesign.widget.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;


import java.util.ArrayList;
import java.util.List;

/**
 * @author TXL
 * description :主要用来做导航的容器，放其他的fragment
 */
public class NavigationFragment extends BaseFragment {

    /**
     * viewpager 指示器
     * */
    private MagicIndicator mCategoryMagicIndicator;

    private CommonNavigatorAdapter indicatorNavigatorAdapter;

    /**
     * 指示器title,先装String后面在考虑对应的数据处理问题。因为现在还不清楚数据的样式
     * */
    private List<String> indicatorTitleList = new ArrayList<>();
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

    /**
     * 二楼内容
     * */
    private RelativeLayout secondFloorContainer;
    private ImageView imageTopNavigationBg;

    private GankViewPager viewPager;
    private String category;
    private CategoryFragmentAdapter categoryFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_navigation,container,false);
        return rootView;
    }

    private int selectPosition = 0;

    protected void initView(){
        viewPager= rootView.findViewById(R.id.vp_navigation);
        viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int currentAlpha = categoryFragmentAdapter.getItem( selectPosition ).getNavigationBgAlpha();
                int targetPosition;
                int targetAlpha;
                if (selectPosition == position) {//右滑与当前position相等
                    targetPosition = position + 1;
                    if (targetPosition >= categoryFragmentAdapter.getCount()
                            || currentAlpha == categoryFragmentAdapter.getItem( targetPosition ).getNavigationBgAlpha()) {
                        return;
                    }
                    targetAlpha = categoryFragmentAdapter.getItem( targetPosition ).getNavigationBgAlpha();//目标透明度
                    if(targetAlpha > currentAlpha){//透明度增加
                        targetAlpha = (int) (currentAlpha +(targetAlpha-currentAlpha)*positionOffset);
                    }else {//透明度减小
                        if(targetAlpha == 0){
                            //目标透明度是0，向右滑的过程中百分比增大，透明度减小
                            targetAlpha = (int) (currentAlpha * (1-positionOffset));
                        }else {
                            //目标透明度不是0，向右滑的过程中百分比增大，透明度增大
                            targetAlpha = (int) (currentAlpha - (currentAlpha-targetAlpha)*positionOffset);
                        }
                    }
                    setNavigationAlpha( targetAlpha );
                } else if (position == selectPosition - 1) {//左滑相对当前position小1
                    targetPosition = position;
                    if (targetPosition < 0
                            || currentAlpha == categoryFragmentAdapter.getItem( targetPosition ).getNavigationBgAlpha()) {
                        return;
                    }
                    targetAlpha = categoryFragmentAdapter.getItem( targetPosition ).getNavigationBgAlpha();
                    if(targetAlpha > currentAlpha){
                        targetAlpha = (int) (currentAlpha + (targetAlpha-currentAlpha)*(1-positionOffset));
                    }else {
                        if(targetAlpha == 0){
                            //目标透明度是0，向右滑的过程中百分比增大，透明度减小
                            targetAlpha = (int) (currentAlpha * positionOffset);
                        }else {
                            //目标透明度不是0，向右滑的过程中百分比增大，透明度增大
                            targetAlpha = (int) (currentAlpha - (currentAlpha -targetAlpha)*(1-positionOffset));
                        }
                    }
                    setNavigationAlpha(  targetAlpha );
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.d( TAG, "onPageSelected  position:  " + position );
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.d( TAG,"onPageScrollStateChanged  state:  "+state );
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {//state有三种状态下文会将，当手指刚触碰屏幕时state的值为1，我们就在这个时候给mViewPagerIndex 赋值。

                    selectPosition = viewPager.getCurrentItem();
                }
            }
        } );
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        mCategoryMagicIndicator = rootView.findViewById(R.id.magic_indicator);
        linearIndicatorLeft = rootView.findViewById(R.id.linear_indicator_left);
        linearIndicatorRight = rootView.findViewById(R.id.linear_indicator_right);
        indicatorContainer = rootView.findViewById(R.id.linear_indicator_container);
        secondFloorContainer = rootView.findViewById(R.id.relative_top_navigation_bg_container );
        imageTopNavigationBg = rootView.findViewById(R.id.image_top_navigation_bg );
        indicatorNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return indicatorTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                ScaleTransitionPagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                Pair<Integer,Integer> colors = TextSelectUtils.getColorPair(getContext());
                simplePagerTitleView.setNormalColor(colors.first);
                simplePagerTitleView.setSelectedColor(colors.second);
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.dp_15));
                simplePagerTitleView.setText( indicatorTitleList.get( index ) );
                simplePagerTitleView.setOnClickListener(v -> viewPager.setCurrentItem( index ,true));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setLineHeight(context.getResources().getDimensionPixelOffset(R.dimen.dp_2));
                indicator.setRoundRadius(context.getResources().getDimensionPixelOffset(R.dimen.dp_6));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                Pair<Integer,Integer> colors = TextSelectUtils.getColorPair(getContext());
                indicator.setColors(colors.second);
                return indicator;
            }
        };
        commonNavigator.setAdapter(indicatorNavigatorAdapter);
        mCategoryMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mCategoryMagicIndicator,viewPager);
        int statusHeight = getStatusHeight( getContext() );
        indicatorContainer.setPadding( 0, statusHeight,0,0);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewPager.getLayoutParams();
        params.topMargin = (int) (statusHeight + getResources().getDimension( R.dimen.dp_top_navigation_height ));
        Log.d( TAG,"|  topMargin "+params.topMargin +"   statusHeight  "+statusHeight);
        viewPager.setLayoutParams( params );
        params = (RelativeLayout.LayoutParams) secondFloorContainer.getLayoutParams();
        params.height = (int) (statusHeight + getResources().getDimension( R.dimen.dp_top_navigation_height ));
        secondFloorContainer.setLayoutParams( params );
    }

    protected void initData(){
        Bundle bundle = getFragmentArguments();
        if(bundle == null){
            Log.e(TAG,"init data error bundle is null ");
            return;
        }
        Navigation navigation = (Navigation) bundle.getSerializable("navigation");
        if(navigation == null){
            throw new RuntimeException("navigation is null");
        }
        List<BaseFragment> fragments = generateFragments(navigation);
        categoryFragmentAdapter = new CategoryFragmentAdapter(getChildFragmentManager());
        categoryFragmentAdapter.setFragmentList(fragments);
        viewPager.setAdapter( categoryFragmentAdapter );
        indicatorNavigatorAdapter.notifyDataSetChanged();
        setDefaultSelectFragment(0);
    }

    private void setDefaultSelectFragment(int index) {
        if(categoryFragmentAdapter != null && index >= 0 && categoryFragmentAdapter.getCount()>index){
            viewPager.setCurrentItem( index );

            setNavigationAlpha( categoryFragmentAdapter.getItem( index ).getNavigationBgAlpha() );
        }


    }

    private List<BaseFragment> generateFragments(Navigation navigation){
        List<BaseFragment> fragments = new ArrayList<>();
        category = navigation.getCategory();
        indicatorTitleList.clear();
        switch (category){
            case Navigation.CATEGORY_MAIN:
                MainNavigation mainNavigation = (MainNavigation) navigation;
                for (String item : mainNavigation.getChildList()) {
                    BaseRefreshFragment baseNewsFragment;
                    if(item.equals( "推荐" )){
                        baseNewsFragment = new SecondFloorNewsFragment();
                    }else if(GlobalCacheUtils.KEY_FU_LI.equals( item )){
                        baseNewsFragment = new FuliFragment();
                    }else {
                        baseNewsFragment  = new BaseNewsFragment();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("category_id",item);
                    baseNewsFragment.setArguments(bundle);
                    fragments.add(baseNewsFragment);
                    indicatorTitleList.add(item);
                }
                break;
        }
        return fragments;
    }


    @Override
    public String getFragmentName() {
        return TAG;
    }

    public void onChildHeadMoving(boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) secondFloorContainer.getLayoutParams();
        int statusHeight = getStatusHeight( getContext() );
        params.height = (int) (statusHeight + getResources().getDimension( R.dimen.dp_top_navigation_height )+offset);
        secondFloorContainer.setLayoutParams( params );
        float alpha = 1-percent;
        if(alpha<=0){
            mCategoryMagicIndicator.setVisibility( View.GONE );
        }else {
            mCategoryMagicIndicator.setVisibility( View.VISIBLE );
        }
        mCategoryMagicIndicator.setAlpha( (1-percent) );
        if(percent > 0.9){
            viewPager.setAllowLeftRightTouch(false);
        }else {
            viewPager.setAllowLeftRightTouch(true);
        }
        Log.d( TAG,"onChildHeadMoving" );
    }

    public void setNavigationAlpha(int alpha){
        setViewBackgroundAlpha( indicatorContainer, alpha);
    }

    public void onSecondFloorRefresh(String url){
        new GlideUtils.GlideUtilsBuilder()
        .setUrl( url )
        .useDefaultPlaceholder( true )
        .setContext( getContext() )
        .setRequestListener( new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Log.d( TAG,"onSecondFloorRefresh onLoadFailed" );
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                Log.d( TAG,"onSecondFloorRefresh onResourceReady" );
                imageTopNavigationBg.setImageDrawable( resource );
                return false;
            }
        } )
        .loadAsDrawable();
    }


}
