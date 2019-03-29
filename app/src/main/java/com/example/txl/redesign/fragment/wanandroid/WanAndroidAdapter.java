package com.example.txl.redesign.fragment.wanandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.example.txl.gankio.R;
import com.example.txl.redesign.adpter.BaseAdapter;
import com.example.txl.redesign.data.XmlyFmData;
import com.example.txl.redesign.fragment.xmlyfm.XmlyFmViewHolder;

/**
 * @author TXL
 * description :
 */
public class WanAndroidAdapter extends BaseAdapter<XmlyFmData, XmlyFmViewHolder> {
    public WanAndroidAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public XmlyFmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case XmlyFmData.WAN_ANDROID_TYPE_BANNER:
                return new WanAndroidBannerViewHolder(mInflater.inflate( R.layout.wan_android_banner,parent,false ));
            case XmlyFmData.WAN_ANDROID_TYPE_ARTICLE:
                return new WanAndroidArticleViewHolder(mInflater.inflate( R.layout.wan_android_item_article,parent,false ));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return getNewsData().get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull XmlyFmViewHolder holder, int position) {
        holder.onBindViewHolder(position,listData.get(position));
    }
}
