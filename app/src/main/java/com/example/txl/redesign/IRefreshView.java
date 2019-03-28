package com.example.txl.redesign;

/**
 * @author TXL
 * description :
 */
public interface IRefreshView<T> extends IBaseView<T> {
    void onRefreshSuccess();
    void onRefreshFailed();
    void onLoadMoreSuccess();
    void onLoadMoreFailed();
}
