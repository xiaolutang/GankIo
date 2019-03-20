package com.example.txl.redesign.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/20
 * description：用来处理全局缓存
 */
public class GlobalCacheUtils {
    private Map<String, Object> map = new ConcurrentHashMap<>();
}
