package com.example.txl.redesign.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshContent;

/**
 * @author TXL
 * description :
 */
public class GankSmartRefreshLayout extends SmartRefreshLayout {
    public GankSmartRefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public GankSmartRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GankSmartRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){

    }

    public RefreshContent getRefreshContent(){
        return mRefreshContent;
    }
}
