package com.example.txl.gankio.change.mvp.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.txl.redesign.data.wanandroid.WanAndroidBanner;

import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/18
 * description：
 */
@Dao
public interface WanAndroidBannerDao {
    @Query( "select * from tb_wan_android_banner_data" )
    List<WanAndroidBanner.Data> getDatas();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(WanAndroidBanner.Data dataList);

    @Delete()
    void deleteAllData(List<WanAndroidBanner.Data> dataList);
}
