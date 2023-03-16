package com.example.xingyue.interfaces;

import com.example.xingyue.base.IBasePresenter;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

public interface IPlayerPresenter extends IBasePresenter<IPlayerCallBack> {

    /**
     * 播放
     */
    void Play();

    /**
     * 暂停
     */
    void pause();

    /**
     *切换上一首
     */
    void playPre();

    /**
     * 切换下一首
     */
    void playNext();

    /**
     * 切换播放模式
     */
    void switchPlayStatus(XmPlayListControl.PlayMode playMode);

    /**
     * 获取播放列表
     */
    void getPlayList();

    /**
     * 根据节目的位置进行播放列表的切换
     * @param index
     */
    void playByIndex(int index);

    /**
     * 切换播放进度
     * @param progress
     */
    void seekTo(int progress);

    /**
     * 判断播放器是否在播放
     *
     * @return
     */
    boolean isPlay();

    /**
     * 将播放列表反转
     */
    void reversePlayList();

    /**
     * 此接口用于实现进入时播放专辑的第一首歌曲
     * @param id 专辑ID
     */
    void playByAlbumId(long id);
}
