package com.example.txl.redesign.data;

import java.util.List;

/**
 * @author TXL
 * description :
 */
public abstract class Navigation<T>{
    public static final String CATEGORY_MAIN = "main";
    public static final String CATEGORY_XINA_DU = "xian_du";
    public static final String CATEGORY_FM = "fm";

    /**
     * 现在分成3大类型
     * 1 gank io 其他类型数据（today 福利 Android iOS 拓展资源 前端 all ） 人为构建这些数据
     * 2 gank io 闲读数据
     * 3 喜马拉雅FM
     * */
    protected String category;


    protected List<T> childList;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<T> getChildList() {
        return childList;
    }

    public void setChildList(List<T> childList) {
        this.childList = childList;
    }
}
