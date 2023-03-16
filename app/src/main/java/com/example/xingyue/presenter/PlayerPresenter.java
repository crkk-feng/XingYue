package com.example.xingyue.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;


import com.example.xingyue.base.BaseApplication;
import com.example.xingyue.data.XimaLayApi;
import com.example.xingyue.interfaces.IPlayerCallBack;
import com.example.xingyue.interfaces.IPlayerPresenter;
import com.example.xingyue.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.advertis.Advertis;
import com.ximalaya.ting.android.opensdk.model.advertis.AdvertisList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.advertis.IXmAdsStatusListener;
import com.ximalaya.ting.android.opensdk.player.constants.PlayerConstants;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP;

public class PlayerPresenter implements IPlayerPresenter, IXmAdsStatusListener, IXmPlayerStatusListener {

    private List<IPlayerCallBack> mIPlayerCallBacks=new ArrayList<>();
    private String TAG="PlayerPresenter";

    private static XmPlayerManager playerManager;
    private Boolean isPlayListSet=false;
    private String mtrackTitle;
    private Track mCurrentTrack;
    private int mCurrentIndex=0;
    private final SharedPreferences playModeMap;
    private XmPlayListControl.PlayMode mCurrentPlayMode;
    private Boolean mIsReverse=false;

    public static final int PLAY_MODEL_LIST_INT = 0;
    public static final int PLAY_MODEL_LIST_LOOP_INT = 1;
    public static final int PLAY_MODEL_RANDOM_INT = 2;
    public static final int PLAY_MODEL_SINGLE_LOOP_INT = 3;

    //sp's key and name
    public static final String PLAY_MODE_SP_NAME = "PlayMod";
    public static final String PLAY_MODE_SP_KEY = "currentPlayMode";
    private int mCurrentProgressPosition=0;
    private int mProgressDuration=0;

    private PlayerPresenter(){
        playerManager = XmPlayerManager.getInstance(BaseApplication.getAppContext());
        playerManager.addAdsStatusListener(this);//添加广告物料回调
        playerManager.addPlayerStatusListener(this);//获取播放器相关接口
        //记录当前的播放模式
        playModeMap = BaseApplication.getAppContext()
                .getSharedPreferences("PlayMode",
                        Context.MODE_PRIVATE);
    }

    private static PlayerPresenter mPlayerPresenter;

    public static PlayerPresenter getmPlayerPresenter(){
        if (mPlayerPresenter == null) {
            synchronized (PlayerPresenter.class){
                if (mPlayerPresenter == null) {
                    mPlayerPresenter=new PlayerPresenter();
                }
            }
        }
        return mPlayerPresenter;
    }

    public void setPlayList(List<Track> playList,int index){
        if (playerManager != null) {
            playerManager.setPlayList(playList,index);
//            LogUtil.d(TAG,playerManager.getPlayList().toString());
            mCurrentTrack=playList.get(index);
            mCurrentIndex = index;
            isPlayListSet=true;
        }else {
            Log.d(TAG,"playerManager is null");
        }
    }
    @Override
    public void Play() {
        if (isPlayListSet==true) {
            //进行播放
            playerManager.play();
        }
    }

    @Override
    public void pause() {
        if (isPlayListSet==true) {
            //进行播放
            playerManager.pause();
        }
    }

    @Override
    public void playPre() {
        if (playerManager != null) {
            playerManager.playPre();
        }
    }

    @Override
    public void playNext() {
        if (playerManager != null) {
            playerManager.playNext();
        }
    }

    @Override
    public void switchPlayStatus(XmPlayListControl.PlayMode playMode) {
        if (playerManager != null) {
            mCurrentPlayMode=playMode;
            playerManager.setPlayMode(playMode);
            for (IPlayerCallBack mIPlayerCallBack : mIPlayerCallBacks) {
                mIPlayerCallBack.onPlayModeChange(playMode);
            }
            SharedPreferences.Editor edit = playModeMap.edit();
            edit.putInt(PLAY_MODE_SP_KEY,getIntByPlayMode(playMode));
            edit.commit();
        }
    }

    private int getIntByPlayMode(XmPlayListControl.PlayMode mode) {
        switch(mode) {
            case PLAY_MODEL_SINGLE_LOOP:
                return PLAY_MODEL_SINGLE_LOOP_INT;
            case PLAY_MODEL_LIST_LOOP:
                return PLAY_MODEL_LIST_LOOP_INT;
            case PLAY_MODEL_RANDOM:
                return PLAY_MODEL_RANDOM_INT;
            case PLAY_MODEL_LIST:
                return PLAY_MODEL_LIST_INT;
        }

        return PLAY_MODEL_LIST_INT;
    }

    private XmPlayListControl.PlayMode getModeByInt(int index) {
        switch(index) {
            case PLAY_MODEL_SINGLE_LOOP_INT:
                return PLAY_MODEL_SINGLE_LOOP;
            case PLAY_MODEL_LIST_LOOP_INT:
                return PLAY_MODEL_LIST_LOOP;
            case PLAY_MODEL_RANDOM_INT:
                return PLAY_MODEL_RANDOM;
            case PLAY_MODEL_LIST_INT:
                return PLAY_MODEL_LIST;
        }
        return PLAY_MODEL_LIST;
    }
    @Override
    public void getPlayList() {
        if (mPlayerPresenter != null) {
            List<Track> playList = playerManager.getPlayList();
            for (IPlayerCallBack mIPlayerCallBack : mIPlayerCallBacks) {
                mIPlayerCallBack.onListLoaded(playList);
            }
        }
    }

    @Override
    public void playByIndex(int index) {
        //切换播放器到index的位置进行播放
        if (playerManager != null) {
            playerManager.play(index);
        }
    }

    @Override
    public void seekTo(int progress) {
        //更新播放器的进度
        playerManager.seekTo(progress);
    }

    @Override
    public boolean isPlay() {
        //返回当前是否正在播放
        return playerManager.isPlaying();
    }

    @Override
    public void reversePlayList() {
        //把播放列表反转
        List<Track> playList = playerManager.getPlayList();
        Collections.reverse(playList);
        mIsReverse=!mIsReverse;
        //第一个参数是播放列表，第二个参数是开始播放的下标
        mCurrentIndex=playList.size()-1-mCurrentIndex;
        playerManager.setPlayList(playList,mCurrentIndex);
        //更新UI
        mCurrentTrack= (Track) playerManager.getCurrSound();
        for (IPlayerCallBack mIPlayerCallBack : mIPlayerCallBacks) {
            mIPlayerCallBack.onListLoaded(playList);
            mIPlayerCallBack.updateTrack(mCurrentTrack,mCurrentIndex);
            mIPlayerCallBack.updateListOrder(mIsReverse);
        }
    }

    @Override
    public void playByAlbumId(long id) {
        //TODO
        //1.获取到专辑的列表内容
        XimaLayApi ximaLayApi=XimaLayApi.getXimaLayApi();
        ximaLayApi.getAlbumDetail(new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(TrackList trackList) {
                List<Track> tracks = trackList.getTracks();
                if (tracks!=null && tracks.size()>0) {
                    playerManager.setPlayList(tracks,0);
                    mCurrentTrack=tracks.get(0);
                    mCurrentIndex = 0;
                    isPlayListSet=true;

                }
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                Toast.makeText(BaseApplication.getAppContext(),"请求失败...",Toast.LENGTH_SHORT).show();
            }
        },id,1);
        //2.把专辑内容设置给播放
        //3.播放器进行播放

    }

    @Override
    public void registerViewCallBack(IPlayerCallBack iPlayerCallBack) {
        if (mIPlayerCallBacks != null) {
            mIPlayerCallBacks.add(iPlayerCallBack);
        }
        //更新之前，先让UI获取数据
        getPlayList();
        iPlayerCallBack.updateTrack(mCurrentTrack,mCurrentIndex);
        iPlayerCallBack.onProgressChanged(mCurrentProgressPosition,mProgressDuration);
        //更新状态
        handlePlayState(iPlayerCallBack);
        //从sp里面拿
        int modelIndex=playModeMap.getInt(PLAY_MODE_SP_KEY,PLAY_MODEL_LIST_INT);
        //
        mCurrentPlayMode=getModeByInt(modelIndex);
        iPlayerCallBack.onPlayModeChange(mCurrentPlayMode);

    }

    private void handlePlayState(IPlayerCallBack iPlayerCallBack) {
        int playerStatus = playerManager.getPlayerStatus();
        //根据状态调用接口方法
        if (PlayerConstants.STATE_STARTED==playerStatus) {
            iPlayerCallBack.onPlayStart();
        }else{
            iPlayerCallBack.onPlayStop();
        }
    }


    @Override
    public void unRegisterViewCallBack(IPlayerCallBack iPlayerCallBack) {
        if (mIPlayerCallBacks != null) {
            mIPlayerCallBacks.remove(iPlayerCallBack);
        }
    }

    //=============================广告相关的回调方法 start=============================
    @Override
    public void onStartGetAdsInfo() {
        LogUtil.d(TAG,"onStartGetAdsInfo..");
    }

    @Override
    public void onGetAdsInfo(AdvertisList advertisList) {
        LogUtil.d(TAG,"onStartGetAdsInfo..");
    }

    @Override
    public void onAdsStartBuffering() {
        LogUtil.d(TAG,"onAdsStartBuffering..");
    }

    @Override
    public void onAdsStopBuffering() {
        LogUtil.d(TAG,"onAdsStopBuffering..");
    }

    @Override
    public void onStartPlayAds(Advertis advertis, int i) {
        LogUtil.d(TAG,"onStartPlayAds..");
    }

    @Override
    public void onCompletePlayAds() {
        LogUtil.d(TAG,"onCompletePlayAds..");
    }

    @Override
    public void onError(int what, int extra) {
        LogUtil.d(TAG,"onError Message is-->"+what+"\"onError Extra-->\""+extra);
    }
    //=============================广告相关的回调方法 end=============================

    //=============================播放器相关的回调方法 start=============================
    @Override
    public void onPlayStart() {
        LogUtil.d(TAG,"onPlayStart..");
        for (IPlayerCallBack mIPlayerCallBack : mIPlayerCallBacks) {
            mIPlayerCallBack.onPlayStart();
        }
    }

    @Override
    public void onPlayPause() {
        LogUtil.d(TAG,"onPlayPause..");
        for (IPlayerCallBack mIPlayerCallBack : mIPlayerCallBacks) {
            mIPlayerCallBack.onPlayPause();
        }
    }

    @Override
    public void onPlayStop() {
        LogUtil.d(TAG,"onPlayStop..");
        for (IPlayerCallBack mIPlayerCallBack : mIPlayerCallBacks) {
            mIPlayerCallBack.onPlayStop();
        }
    }

    @Override
    public void onSoundPlayComplete() {
        LogUtil.d(TAG,"onSoundPlayComplete..");

    }

    @Override
    public void onSoundPrepared() {
        LogUtil.d(TAG,"onSoundPrepared..");
        if (playerManager != null) {
            playerManager.setPlayMode(mCurrentPlayMode);
        }
        if (playerManager.getPlayerStatus()== PlayerConstants.STATE_PREPARED) {
            //播放器准备完成，可以进行播放
            playerManager.play();
        }
    }

    @Override
    public void onSoundSwitch(PlayableModel lastModel, PlayableModel curModel) {
        //lastModel:上一首modeL,可能为空
        //curModel:目前正在播放的model
        if (curModel != null) {
            if (curModel instanceof Track) {
                Track curTrack=(Track) curModel;
                mCurrentIndex=playerManager.getCurrentIndex();
                HistoryPresenter historyPresenter = HistoryPresenter.getHistoryPresenter();
                historyPresenter.addHistory(curTrack);
                for (IPlayerCallBack mIPlayerCallBack : mIPlayerCallBacks) {
                    mIPlayerCallBack.updateTrack(curTrack,mCurrentIndex);
                }
                LogUtil.d(TAG,curTrack.getTrackTitle());
            }
        }
        LogUtil.d(TAG,"onSoundSwitch..");
    }

    @Override
    public void onBufferingStart() {
        LogUtil.d(TAG,"onBufferingStart..");
    }

    @Override
    public void onBufferingStop() {
        LogUtil.d(TAG,"onBufferingStop..");
    }

    @Override
    public void onBufferProgress(int currentProgress) {
        LogUtil.d(TAG,"onBufferProgress.."+currentProgress);
    }

    @Override
    public void onPlayProgress(int currentProgress, int duration) {
        this.mCurrentProgressPosition=currentProgress;
        this.mProgressDuration=duration;
        //单位是毫秒
        for (IPlayerCallBack mIPlayerCallBack : mIPlayerCallBacks) {
            mIPlayerCallBack.onProgressChanged(currentProgress,duration);
        }
        LogUtil.d(TAG,"onPlayProgress:"+currentProgress+"duration:"+duration);
    }

    @Override
    public boolean onError(XmPlayerException e) {
        LogUtil.d(TAG,"onError.."+e.toString());
        return false;
    }

    /**
     * 判断是否有播放的播放列表
     * @return
     */
    public boolean hasPlayList() {
        return  isPlayListSet;
    }
    //=============================播放器相关的回调方法 end=============================
}
