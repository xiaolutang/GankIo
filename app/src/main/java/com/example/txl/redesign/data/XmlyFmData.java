package com.example.txl.redesign.data;

import com.example.txl.redesign.data.wanandroid.ArticleList;
import com.example.txl.redesign.data.wanandroid.WanAndroidArticle;
import com.example.txl.redesign.data.wanandroid.WanAndroidBanner;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

/**
 * @author TXL
 * description :为了在不同的adapter中可以显示不同来源的数据，把接口数据统一处理成这个类型的
 */
public class XmlyFmData {
    //xmly
    public static final int XMLY_TYPE_CATEGORY_LIST = 0;
    public static final int XMLY_TYPE_CATEGORY_ITEM = 1;
    public static final int XMLY_TYPE_ALBUN_ITEM = 2;

    //玩android
    public static final int WAN_ANDROID_TYPE_BANNER = 3;
    public static final int WAN_ANDROID_TYPE_ARTICLE = 4;

    private static void testType(int type){
        switch (type){
            case XMLY_TYPE_CATEGORY_LIST:
            case XMLY_TYPE_CATEGORY_ITEM:
            case XMLY_TYPE_ALBUN_ITEM:
            case WAN_ANDROID_TYPE_BANNER:
            case WAN_ANDROID_TYPE_ARTICLE:
        }
    }

    public XmlyFmData() {
    }

    public XmlyFmData(int type) {
        this.type = type;
    }

    /**
     * 数据类型
     * */
    private int type;
    /**
     * xmly分类
     * */
    private CategoryList categoryList;

    /**
     * xmly专辑
     * */
    private Album album;

    /**
     * wan android 首页banner
     * */
    private WanAndroidBanner wanAndroidBanner;

    /**
     * wan android 文章列表
     * */
    private ArticleList articleList;

    /**
     * wan android 文章
     * */
    private WanAndroidArticle androidArticle;

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

    public WanAndroidBanner getWanAndroidBanner() {
        return wanAndroidBanner;
    }

    public void setWanAndroidBanner(WanAndroidBanner wanAndroidBanner) {
        this.wanAndroidBanner = wanAndroidBanner;
    }

    public ArticleList getArticleList() {
        return articleList;
    }

    public void setArticleList(ArticleList articleList) {
        this.articleList = articleList;
    }

    public WanAndroidArticle getAndroidArticle() {
        return androidArticle;
    }

    public void setAndroidArticle(WanAndroidArticle androidArticle) {
        this.androidArticle = androidArticle;
    }
}
