package com.example.txl.redesign.fm;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

/**
 * @author TXL
 * description :对喜马拉雅fm的数据进行处理
 */
public class XmlyFmData {
    public static final int TYPE_CATEGORY_LIST = 0;
    public static final int TYPE_CATEGORY_ITEM = 1;
    public static final int TYPE_ALBUN_ITEM = 2;

    public XmlyFmData(int type) {
        this.type = type;
    }

    /**
     * 数据类型
     * */
    private int type;

    private CategoryList categoryList;

    private Album album;

    public int getType(){
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public CategoryList getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(CategoryList categoryList) {
        this.categoryList = categoryList;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
