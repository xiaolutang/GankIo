package com.example.txl.redesign;

/**
 * @author TXL
 * description :
 */
public interface IBaseView<T> {
    /**
     * 如果presenter没有在view中进行创建，那么需要进行设置
     * if presenter object not create at view you should call this method to set
     * @param presenter
     * */
    void setPresenter(T presenter);
}
