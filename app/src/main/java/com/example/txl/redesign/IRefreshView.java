package com.example.txl.redesign;

/**
 * @author TXL
 * description :
 */
public interface IRefreshView<T,D> extends IBaseView<T> {
    void onRefreshSuccess(D data);
    void onRefreshFailed();
    void onLoadMoreSuccess(D data,boolean hasMore);
    void onLoadMoreFailed();
}
