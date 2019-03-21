package com.example.txl.redesign.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseActivity;
import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.redesign.data.MainNavigation;
import com.example.txl.redesign.fragment.NavigationFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TXL
 * description :
 */
public class NewStyleMainActivity extends BaseActivity {

    List<BaseFragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_style_main);
        //测试
        NavigationFragment navigationFragment = new NavigationFragment();
        MainNavigation navigation = MainNavigation.buildMainNavigation();
        Bundle bundle = new Bundle();
        bundle.putParcelable("navigation",navigation);
        navigationFragment.setArguments(bundle);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragmentManager.executePendingTransactions();
        transaction.add(R.id.fl_main_page_content, navigationFragment,navigation.getCategory());
        transaction.commitAllowingStateLoss();
    }
}
