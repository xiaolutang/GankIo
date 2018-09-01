package com.example.txl.gankio.change.mvp.about;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.txl.gankio.R;
import com.example.txl.gankio.base.BaseActivity;


public class AboutActivity extends BaseActivity implements View.OnClickListener {

    TextView tvAboutVersion, tvCheckUpdate, tvAboutUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_about );
        initView();
    }

    private void initView() {
        tvAboutVersion = findViewById( R.id.tv_about_version );
        tvAboutVersion.setText( "当前版本："+packageCode(this)+"."+packageName(this) );
        tvCheckUpdate = findViewById( R.id.tv_about_check_update );
        tvCheckUpdate.setOnClickListener( this );
        tvAboutUpdate = findViewById( R.id.tv_about_update );
        tvAboutUpdate.setOnClickListener( this );
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_about_check_update:
            case R.id.tv_about_update:
        }
    }

    private int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    private String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }
}
