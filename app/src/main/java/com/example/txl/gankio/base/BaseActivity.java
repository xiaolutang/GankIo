package com.example.txl.gankio.base;

import android.content.Context;
import android.content.Intent;

import com.example.txl.gankio.R;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public abstract class BaseActivity extends BaseFragmentActivity implements IBaseView{
    protected String TAG = this.getClass().getSimpleName();

    @Override
    public Context getContext() {
        return this;
    }

    protected void startActivity(Class<?> pClass) {
        Intent _Intent = new Intent();
        _Intent.setClass(this, pClass);
        startActivity(_Intent);
        overridePendingTransition(R.anim.trans_next_in, R.anim.trans_next_out);
    }

    protected void startActivityByIntent(Intent pIntent){
        startActivity(pIntent);
        overridePendingTransition(R.anim.trans_next_in, R.anim.trans_next_out);
    }


}
