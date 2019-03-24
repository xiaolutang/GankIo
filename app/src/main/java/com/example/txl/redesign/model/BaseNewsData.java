package com.example.txl.redesign.model;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/24
 * description：
 */
public class BaseNewsData {

    public enum NewsType{
        TYPE_ANDROID,
        TYPE_APP,
        TYPE_IOS,
        TYPE_VIDEO,
        TYPE_FRONT,
        TYPE_EXPANDING_RESUORCES,
        TYPE_XIA_TUI_JIAN,
        TYPE_FU_LI
    }


    public static final String TYPE_ANDROID = "Android";
    public static final String TYPE_APP = "App";
    public static final String TYPE_IOS = "iOS";
    public static final String TYPE_VIDEO = "休息视频";
    public static final String TYPE_FRONT = "前端";
    public static final String TYPE_EXPANDING_RESUORCES = "拓展资源";
    public static final String TYPE_XIA_TUI_JIAN = "瞎推荐";
    public static final String TYPE_FU_LI = "福利";

    protected String type;

    public void setmType(String mType) {
        this.type = mType;
    }

    public int getType(){
        switch (type){
            case TYPE_ANDROID:
                return NewsType.TYPE_ANDROID.ordinal();
            case TYPE_APP:
                return NewsType.TYPE_APP.ordinal();
            case TYPE_IOS:
                return NewsType.TYPE_IOS.ordinal();
            case TYPE_VIDEO:
                return NewsType.TYPE_VIDEO.ordinal();
            case TYPE_FRONT:
                return NewsType.TYPE_FRONT.ordinal();
            case TYPE_EXPANDING_RESUORCES:
                return NewsType.TYPE_EXPANDING_RESUORCES.ordinal();
            case TYPE_XIA_TUI_JIAN:
                return NewsType.TYPE_XIA_TUI_JIAN.ordinal();
            case TYPE_FU_LI:
                return NewsType.TYPE_FU_LI.ordinal();
        }
        return 0;
    }
}
