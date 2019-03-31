package com.example.txl.redesign.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;

import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseActivity;
import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.redesign.data.MainNavigation;
import com.example.txl.redesign.fragment.wanandroid.WanAndroidFragment;
import com.example.txl.redesign.fragment.xmlyfm.FmFragment;
import com.example.txl.redesign.fragment.NavigationFragment;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TXL
 * description :
 */
public class NewStyleMainActivity extends BaseActivity {

    private int lastSelectIndex = -1;
    List<BaseFragment> fragmentList = new ArrayList<>();
    RadioButton radioButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_style_main);
        initFragment();
        initView();
    }

    private void changeFragment(int index) {
        if(index == lastSelectIndex){
            return;
        }
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(lastSelectIndex != -1){
            transaction.hide(fragmentList.get(lastSelectIndex));
        }
        BaseFragment fragment = fragmentList.get(index);
        if(!fragment.isAdded()){
            transaction.add(R.id.fl_main_page_content,fragment,fragment.getFragmentName());
        }else {
            transaction.show(fragment);
        }
        transaction.commitAllowingStateLoss();
        lastSelectIndex = index;
        if (fragment instanceof FmFragment || fragment instanceof WanAndroidFragment) {
            //状态栏字体是黑色
            StatusBarUtil.setLightMode(this);
        } else {
            StatusBarUtil.setDarkMode(this);
        }
    }

    private void initFragment(){
        //测试
        fragmentList.clear();
        NavigationFragment navigationFragment = new NavigationFragment();
        MainNavigation navigation = MainNavigation.buildMainNavigation();
        Bundle bundle = new Bundle();
        bundle.putParcelable("navigation",navigation);
        navigationFragment.setArguments(bundle);
        fragmentList.add(navigationFragment);

        //添加一个重复的数据
        WanAndroidFragment wanAndroidFragment = new WanAndroidFragment();
        fragmentList.add(wanAndroidFragment);

        //fm
        FmFragment fmFragment = new FmFragment();
        fragmentList.add(fmFragment);
    }

    private void initView(){
        radioButton = findViewById(R.id.rb_first);
        radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                changeFragment(0);
            }
        });
        radioButton.setChecked(true);
        radioButton = findViewById(R.id.rb_second);
        radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                changeFragment(1);
            }
        });
        radioButton = findViewById(R.id.rb_third);
        radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                changeFragment(2);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
