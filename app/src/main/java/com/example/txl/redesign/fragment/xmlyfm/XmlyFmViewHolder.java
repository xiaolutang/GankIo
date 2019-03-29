package com.example.txl.redesign.fragment.xmlyfm;

import android.view.View;

import com.example.txl.redesign.adpter.BaseViewHolder;
import com.example.txl.redesign.data.XmlyFmData;

/**
 * @author TXL
 * description :使用XmlyFmData这个数据类型的都可以直接继承
 */
public abstract class XmlyFmViewHolder extends BaseViewHolder<XmlyFmData> {
    public XmlyFmViewHolder(View itemView) {
        super(itemView);
    }
}
