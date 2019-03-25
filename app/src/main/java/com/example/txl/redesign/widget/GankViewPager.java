package com.example.txl.redesign.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * @author TXL
 * description :
 */
public class GankViewPager extends ViewPager {
    private boolean allowLeftRightTouch = true;
    float downX,downY;

    public GankViewPager(@NonNull Context context) {
        super(context);
    }

    public GankViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAllowLeftRightTouch(boolean allowLeftRightTouch) {
        this.allowLeftRightTouch = allowLeftRightTouch;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if(ev.getAction()==MotionEvent.ACTION_DOWN)
        {
            downX=ev.getX();
            downY=ev.getY();
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_MOVE){
            float moveX = ev.getX();
            float moveY = ev.getY();
            if (!allowLeftRightTouch && Math.abs(moveX - downX) > Math.abs(moveY - downY)) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
