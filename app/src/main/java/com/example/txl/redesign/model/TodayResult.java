package com.example.txl.redesign.model;

import java.util.List;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/24
 * description：
 */
public class TodayResult {
    List<NewsData> Android;
    List<NewsData> App;
    List<NewsData> iOS;
    List<NewsData> 休息视频;
    List<NewsData> 前端;
    List<NewsData> 拓展资源;
    List<NewsData> 瞎推荐;
    List<NewsData> 福利;

    public List<NewsData> getAndroid() {
        return Android;
    }

    public void setAndroid(List<NewsData> android) {
        Android = android;
    }

    public List<NewsData> getApp() {
        return App;
    }

    public void setApp(List<NewsData> app) {
        App = app;
    }

    public List<NewsData> getiOS() {
        return iOS;
    }

    public void setiOS(List<NewsData> iOS) {
        this.iOS = iOS;
    }

    public List<NewsData> get休息视频() {
        return 休息视频;
    }

    public void set休息视频(List<NewsData> 休息视频) {
        this.休息视频 = 休息视频;
    }

    public List<NewsData> get前端() {
        return 前端;
    }

    public void set前端(List<NewsData> 前端) {
        this.前端 = 前端;
    }

    public List<NewsData> get拓展资源() {
        return 拓展资源;
    }

    public void set拓展资源(List<NewsData> 拓展资源) {
        this.拓展资源 = 拓展资源;
    }

    public List<NewsData> get瞎推荐() {
        return 瞎推荐;
    }

    public void set瞎推荐(List<NewsData> 瞎推荐) {
        this.瞎推荐 = 瞎推荐;
    }

    public List<NewsData> get福利() {
        return 福利;
    }

    public void set福利(List<NewsData> 福利) {
        this.福利 = 福利;
    }
}
