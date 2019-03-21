package com.example.txl.redesign.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/20
 * description：用来处理全局缓存,缓存
 */
public class GlobalCacheUtils {
    /**
     * 今日干货
     * */
    public static final String KEY_TODAY = "today";
    /**
     * 闲读主分类
     * */
    public static final String KEY_XIAN_DU_CATEGORY = "xian_du_categories";
    public static final String KEY_FU_LI = "福利";
    public static final String KEY_ANDROID = "Android";
    public static final String KEY_IOS = "iOS";
    public static final String KEY_VIDEO = "休息视频";
    public static final String KEY_EXPANDING_RESUORCES = "拓展资源";
    public static final String KEY_FRONT = "前端";
    public static final String KEY_ALL = "all";


    private static Map<String, Object> globalCache = new ConcurrentHashMap<>();

    public static void cache(String key, Object value){
        globalCache.put( key,value );
    }

    public static Object getCache(String key){
        return globalCache.get( key );
    }

    /**
     * 用于测试是不是重复
     * */
    static void test(String key){
        switch (key){
            case KEY_TODAY:
            case KEY_XIAN_DU_CATEGORY:
            case KEY_FU_LI:
        }
    }
}
