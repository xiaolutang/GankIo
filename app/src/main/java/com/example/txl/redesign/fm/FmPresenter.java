package com.example.txl.redesign.fm;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.txl.redesign.api.XmlyApi;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/27
 * description：
 */
public class FmPresenter implements FmContract.Presenter {
    private String TAG = getClass().getSimpleName();
    FmContract.View<AlbumList> fmView;
    CategoryList categoryList;
    protected int page = 1;
    protected int count = 20;
    public FmPresenter(FmContract.View<AlbumList> fmView) {
        this.fmView = fmView;
    }

    /**
     * category_id	Int	是	分类ID，指定分类，为0时表示热门分类
     * tag_name	String	否	分类下对应的专辑标签，不填则为热门分类
     * calc_dimension	Int	是	计算维度，现支持最火（1），最新（2），经典或播放最多（3）
     * page	Int	否	返回第几页，必须大于等于1，不填默认为1
     * count	Int	否	每页
     * */
    int index = 0;
    @Override
    public void refresh() {
        page = 1;
        Category category = null;
        if(categoryList != null){
            int size =  categoryList.getCategories().size();
            index = (int) (size*Math.random());
            category = categoryList.getCategories().get( index );
        }
        if(category != null){
            Map<String, String> specificParams = new HashMap<>(  );
//            specificParams.put( "category_id",category.getId() +"");
            specificParams.put( "category_id","28");
//            specificParams.put( "tag_name",category.getCategoryName() +"");
            specificParams.put( "tag_name","脱口秀");
            specificParams.put( "calc_dimension",3 +"");
            specificParams.put( "page",page +"");
            specificParams.put( "count",count +"");
            Category finalCategory = category;
            XmlyApi.getAlbumList( specificParams, new IDataCallBack<AlbumList>() {
                @Override
                public void onSuccess(@Nullable AlbumList albumList) {
//                    Log.d( TAG,"onSuccess albumList =="+albumList.getAlbums().get( 0 ) );
//                    if(albumList.getTotalCount() == 0){
//                        categoryList.getCategories().remove( finalCategory );
//                    }
                    fmView.onRefreshSuccess( albumList );
                }

                @Override
                public void onError(int i, String s) {
                    fmView.onRefreshFailed();
                }
            } );
        }

    }

    @Override
    public void loadMore() {
        if(categoryList == null || categoryList.getCategories().size() <= index){
            fmView.onLoadMoreFailed();
            return;
        }
        page ++;
        Category category = categoryList.getCategories().get( index );
        if(category != null){
            Map<String, String> specificParams = new HashMap<>(  );
            //            specificParams.put( "category_id",category.getId() +"");
            specificParams.put( "category_id","28");
//            specificParams.put( "tag_name",category.getCategoryName() +"");
            specificParams.put( "tag_name","脱口秀");
            specificParams.put( "calc_dimension",3 +"");
            specificParams.put( "page",page +"");
            specificParams.put( "count",count +"");
            XmlyApi.getAlbumList( specificParams, new IDataCallBack<AlbumList>() {
                @Override
                public void onSuccess(@Nullable AlbumList albumList) {
                    Log.d( TAG,"onSuccess albumList =="+albumList.getAlbums().get( 0 ) );
                    fmView.onLoadMoreSuccess( albumList ,true);
                }

                @Override
                public void onError(int i, String s) {
                    fmView.onLoadMoreFailed();
                }
            } );
        }
    }


    @Override
    public void start() {

    }

    @Override
    public void getFmCategory() {
        XmlyApi.getXmlyCategorys( null, new IDataCallBack<CategoryList>() {
            @Override
            public void onSuccess(@Nullable CategoryList categoryList) {
                FmPresenter.this.categoryList = categoryList;
                fmView.onCategorySuccess( categoryList );
                refresh();
            }

            @Override
            public void onError(int i, String s) {
                fmView.onCategoryFailed();
            }
        } );
    }
}
