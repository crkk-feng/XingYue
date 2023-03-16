package com.example.xingyue.interfaces;

import com.example.xingyue.base.IBasePresenter;

public interface IAlbumDetailPresenter extends IBasePresenter<IAlbumDetailViewCallBack> {

    /**
     * 下拉刷新更多内容
     */
    void pullDownRefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();

    /**
     * 获取专辑详情
     * @param albumId 专辑的ID
     * @param page 专辑的详细页码
     */
    void getAlbumDetail(int albumId,int page);


}