package com.example.txl.redesign;

/**
 * @author TXL
 * description :
 */
public interface IRefreshView<T> extends IBaseView<T> {
    void onRefresh();
    void onLoadMoer();
}
