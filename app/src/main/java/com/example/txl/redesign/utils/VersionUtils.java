package com.example.txl.redesign.utils;

import android.os.Build;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/31
 * description：
 */
public class VersionUtils {
    /**
     * 判断当前的版本是不是大与android 7
     * */
    public static boolean isAndroidOOrHigherN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }
}
