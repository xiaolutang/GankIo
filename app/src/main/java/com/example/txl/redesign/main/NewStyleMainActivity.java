package com.example.txl.redesign.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseActivity;
import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.redesign.data.MainNavigation;
import com.example.txl.redesign.fm.FmFragment;
import com.example.txl.redesign.fragment.NavigationFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TXL
 * description :
 */
public class NewStyleMainActivity extends BaseActivity {

    List<BaseFragment> fragmentList = new ArrayList<>();
    RadioButton radioButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_style_main);
        initFragment();
        initView();
    }

    private void changeFragment(BaseFragment fragment) {

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragmentManager.executePendingTransactions();
        transaction.replace(R.id.fl_main_page_content, fragment,fragment.getFragmentName());
        transaction.commitAllowingStateLoss();
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
        navigationFragment = new NavigationFragment();
        navigation = MainNavigation.buildMainNavigation();
        bundle = new Bundle();
        bundle.putParcelable("navigation",navigation);
        navigationFragment.setArguments(bundle);
        fragmentList.add(navigationFragment);

        //fm
        FmFragment fmFragment = new FmFragment();
        fragmentList.add(fmFragment);
    }

    private void initView(){
        radioButton = findViewById(R.id.rb_first);
        radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                changeFragment(fragmentList.get(0));
            }
        });
        radioButton.setChecked(true);
        radioButton = findViewById(R.id.rb_second);
        radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                changeFragment(fragmentList.get(1));
            }
        });
        radioButton = findViewById(R.id.rb_third);
        radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                changeFragment(fragmentList.get(2));
            }
        });
    }
}
