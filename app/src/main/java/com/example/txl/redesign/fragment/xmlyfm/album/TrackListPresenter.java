package com.example.txl.redesign.fragment.xmlyfm.album;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.txl.redesign.api.XmlyApi;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/31
 * description：
 */
public class TrackListPresenter implements TrackListContract.Presenter {
    private String TAG = getClass().getSimpleName();
    TrackListContract.View<TrackList> view;

    private int page = 1;
    private int count = 30;
    /**
     * 专辑
     * */
    private Album album;

    public TrackListPresenter(TrackListContract.View<TrackList> view, Album album) {
        this.view = view;
        this.album = album;
    }

    @Override
    public void refresh() {
        page = 1;
        Map<String, String> map = new HashMap<String, String>();
        map.put( DTransferConstants.ALBUM_ID, album.getId()+"");
        map.put(DTransferConstants.SORT, "asc");
        map.put(DTransferConstants.PAGE, page+"");
        map.put(DTransferConstants.PAGE_SIZE, count+"");
        XmlyApi.getTracks( map, new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(@Nullable TrackList trackList) {
                view.onRefreshSuccess( trackList );
            }

            @Override
            public void onError(int i, String s) {
                Log.e( TAG,"refresh onError "+"i "+i+" s "+s );
                view.onLoadMoreFailed();
            }
        } );
    }

    @Override
    public void loadMore() {
        page ++;
        Map<String, String> map = new HashMap<String, String>();
        map.put( DTransferConstants.ALBUM_ID, album.getId()+"");
        map.put(DTransferConstants.SORT, "asc");
        map.put(DTransferConstants.PAGE, page+"");
        map.put(DTransferConstants.PAGE_SIZE, count+"");
        XmlyApi.getTracks( map, new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(@Nullable TrackList trackList) {
                view.onLoadMoreSuccess( trackList ,true);
            }

            @Override
            public void onError(int i, String s) {
                Log.e( TAG,"refresh onError "+"i "+i+" s "+s );
                view.onLoadMoreFailed();
            }
        } );
    }

    @Override
    public void start() {

    }
}
