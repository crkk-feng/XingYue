package com.example.xingyue.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public interface IAlbumDetailViewCallBack {

    /**
     * 更新列表，加载专辑详情
     * @param tracks
     */
    void onDetailListLoaded(List<Track> tracks);

    /**
     * 将Album传给UI使用
     * @param album
     */
    void onAlbumLoaded(Album album);

    /**
     * 网络错误的接口
     */
    void onNetworkError(int errorcode, String errorMessage);

    /**
     * 加载更多的结果
     * @param size>0,加载成功,否则加载失败
     */
    void onLoaderMoreFinish(int size);

    /**
     * 上拉加载的结果
     * @param size>0,加载成功,否则加载失败
     */
    void onRefreshFinish(int size);
}
