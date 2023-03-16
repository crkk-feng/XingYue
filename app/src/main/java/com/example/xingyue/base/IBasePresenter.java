package com.example.xingyue.base;

/**
 * 抽取IPrsenter接口的基类
 * @param <T>
 */
public interface IBasePresenter<T> {
    /**
     * UI注册事件
     */
    void registerViewCallBack(T t);

    /**
     * UI取消注册事件
     */
    void unRegisterViewCallBack(T t);

}
