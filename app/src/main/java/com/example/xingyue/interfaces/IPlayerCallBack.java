package com.example.xingyue.interfaces;

import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

public interface IPlayerCallBack {

    /**
     * 开始播放
     */
    void onPlayStart();

    /**
     * 播放暂停
     */
    void onPlayPause();

    /**
     * 播放停止
     */
    void onPlayStop();

    /**
     * 网络错误
     */
    void onPlayError();

    /**
     * 下一首播放
     */
    void nextPlay(Track track);

    /**
     * 上一首播放
     */
    void onPrePlay(Track track);

    /**
     * 播放数据加载完成
     * @param list 播放器列表数据
     */
    void onListLoaded(List<Track> list);

    /**
     * 当播放状态改变
     * @param playMode
     */
    void onPlayModeChange(XmPlayListControl.PlayMode playMode);

    /**
     * 进度条的改变
     * @param currentProgress
     * @param total
     */
    void onProgressChanged(long currentProgress,long total);

    /**
     * 对广告进行加载
     */
    void onAdLoading();

    /**
     * 对广告停止加载
     */
    void onAdFinish();

    /**
     * 根据专辑进行更新
     * @param track 节目专辑
     */
    void updateTrack(Track track,int position);

    /**
     * 通知UI更新播放列表顺序/逆序
     * @param isReverse
     */
    void updateListOrder(Boolean isReverse);
}
