package com.example.txl.gankio.viewimpl;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.txl.gankio.App;
import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseActivity;
import com.example.txl.gankio.base.BaseFragment;
import com.example.txl.gankio.bean.IdelReaderCategoryRoot;
import com.example.txl.gankio.change.mvp.login.LoginActivity;
import com.example.txl.gankio.change.mvp.video.VideoFragment;
import com.example.txl.gankio.change.mvp.video.VideoPresenter;
import com.example.txl.gankio.presenter.MainPresenter;
import com.example.txl.gankio.utils.ThemeUtils;
import com.example.txl.gankio.viewimpl.idelread.IdelReadFragment;
import com.example.txl.gankio.viewimpl.video.FuLiFragment;
import com.example.txl.gankio.viewinterface.IGetMainDataView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IGetMainDataView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbarlayout)
    AppBarLayout appbarlayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.coordinatorlayout)
    CoordinatorLayout coordinatorlayout;
    @BindView(R.id.navigationView)
    NavigationView navigationview;
    @BindView(R.id.drawerLayout_home)
    DrawerLayout drawerLayoutHome;
    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;

    List<BaseFragment> fragmentList = new ArrayList<>(  );
    IdelReadFragment idelReadFragment;
    FuLiFragment fuLiFragment;
    VideoFragment videoFragment;

    private int currentFragmentIndex = 0;

    private final int idelReadFragmentIndex = 0;
    private final int videoFragmentIndex = 1;
    private final int allDataFragmentIndex = 2;

    MainPresenter mainPresenter;
    VideoPresenter videoPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        ButterKnife.bind(this);
        initData();
        prepareMainData();
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void prepareMainData(){
        mainPresenter = new MainPresenter( this );
        mainPresenter.prepareMainData( this );
    }

    @Override
    public void initData(){
        fragmentList.clear();
        idelReadFragment = new IdelReadFragment();
        fuLiFragment = new FuLiFragment();
        videoFragment = new VideoFragment();
        fragmentList.add( idelReadFragment );
        fragmentList.add( fuLiFragment );
        fragmentList.add( videoFragment );
        videoPresenter = new VideoPresenter(videoFragment  );
//        videoPresenter.start();
    }

    @Override
    public void initView(){
        setSupportActionBar( toolbar );
        toolbar.setBackgroundColor( ThemeUtils.getToolBarColor());
        // 设置DrawerLayout开关指示器，即Toolbar最左边的那个icon
        ActionBarDrawerToggle mActionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayoutHome, toolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        drawerLayoutHome.addDrawerListener(mActionBarDrawerToggle);
        onNavigationViewMenuItemSelected( navigationview );
        viewpager.setAdapter(new FragmentPagerAdapter( getSupportFragmentManager()){

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get( position );
            }
        });
        viewpager.setOffscreenPageLimit(3);
        viewpager.addOnPageChangeListener(onPageChangeListener);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_home:
                        currentFragmentIndex = idelReadFragmentIndex;
                        break;
                    case R.id.rb_dynamic:
                        currentFragmentIndex = videoFragmentIndex;
                        break;
                    case R.id.rb_message:
                        currentFragmentIndex = allDataFragmentIndex;
                        break;

                }
                int index = currentFragmentIndex < fragmentList.size() ? currentFragmentIndex : fragmentList.size();
                viewpager.setCurrentItem( index, false);

            }
        });
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    radioGroup.check(R.id.rb_home);
                    break;
                case 1:
                    radioGroup.check(R.id.rb_dynamic);
                    break;
                case 2:
                    radioGroup.check(R.id.rb_message);
                    break;
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };


    private void onNavigationViewMenuItemSelected(NavigationView mNav) {
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_menu_home:
                        break;
                    case R.id.nav_menu_categories:
                        break;
                    case R.id.nav_menu_recommend:
                        break;
                    case R.id.nav_menu_feedback:
                        break;
                    case R.id.nav_menu_setting:
                        break;
                    case R.id.nav_menu_theme:

//                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_theme_color, null, false);
//                        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.theme_recycler_view);
//                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
//                        recyclerView.setAdapter(themeColorAdapter);
//                        android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                        builder.setTitle("主题选择")
//                                .setView(view)
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        ThemeUtils.setThemeColor( getResources().getColor(themeColorList.get(themeColorAdapter.getPosition()).getColor()));// 不要变换位置
//                                        ThemeUtils.setThemePosition(themeColorAdapter.getPosition());
//                                        // finish();
//                                        new Handler().postDelayed( new Runnable() {
//                                            public void run() {
//                                                ActivityCollector.getInstance().refreshAllActivity();
//                                                // closeHandler.sendEmptyMessageDelayed(MSG_CLOSE_ACTIVITY, 300);
//                                            }
//                                        }, 100);
//                                    }
//                                })
//                                .show();

                        break;
                    case R.id.nav_menu_login:
                        if(App.getLoginUser() != null){
                            Toast.makeText( MainActivity.this,"您已经登陆了！",Toast.LENGTH_SHORT ).show();
                            break;
                        }
                        startActivity( LoginActivity.class );
                        break;
                }

                // Menu item点击后选中，并关闭Drawerlayout
                menuItem.setChecked(true);
                //drawerlayoutHome.closeDrawers();
                // Toast.makeText(MainActivity.this,msgString,Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }


    @Override
    public void getIdelReaderSuccess(IdelReaderCategoryRoot root) {
        Log.d( TAG,"current thread "+Thread.currentThread().getName() );
        if(currentFragmentIndex == idelReadFragmentIndex){
            idelReadFragment.updateData( root.getCategories() );
        }


    }

    @Override
    public void getIdelReaderFailed(Object o) {

    }

    @Override
    public void getVideoSuccess(Object o) {

    }

    @Override
    public void getVideoFailed(Object o) {

    }

    @Override
    public void getAllDataSuccess(Object o) {

    }

    @Override
    public void getAllDataFailed(Object o) {

    }
}
