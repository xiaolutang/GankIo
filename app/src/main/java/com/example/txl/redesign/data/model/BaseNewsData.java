package com.example.txl.redesign.data.model;

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

    public String getType(){

        return type;
    }
}
