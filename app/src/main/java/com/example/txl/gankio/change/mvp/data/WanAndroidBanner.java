package com.example.txl.gankio.change.mvp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.txl.gankio.change.mvp.wan.android.IDataModel;

import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/18
 * description：
 */

public class WanAndroidBanner  implements IDataModel {
    List<Data> data;
    int errorCode;
    String errorMsg;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public int getDataModelType() {
        return IDataModel.TYPE_BANNER;
    }

    @Entity(tableName = "tb_wan_android_banner_data")
    public static class Data{
        @ColumnInfo
        String desc;
        @PrimaryKey
        int id;
        @ColumnInfo(name = "image_path")
        String imagePath;
        @ColumnInfo(name ="is_visible")
        int isVisible;
        @ColumnInfo
        int order;
        @ColumnInfo
        String title;
        @ColumnInfo
        int type;
        @ColumnInfo
        String url;

        @Ignore
        public Data() {
        }

        public Data(String desc, int id, int isVisible, int order, String title, int type, String url) {
            this.desc = desc;
            this.id = id;
            this.isVisible = isVisible;
            this.order = order;
            this.title = title;
            this.type = type;
            this.url = url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public int getIsVisible() {
            return isVisible;
        }

        public void setIsVisible(int isVisible) {
            this.isVisible = isVisible;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "desc='" + desc + '\'' +
                    ", id=" + id +
                    ", imagePath='" + imagePath + '\'' +
                    ", isVisible=" + isVisible +
                    ", order=" + order +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", url='" + url + '\'' +
                    '}';
        }

    }
}
