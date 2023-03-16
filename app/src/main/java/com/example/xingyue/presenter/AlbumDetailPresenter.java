package com.example.xingyue.presenter;

import android.util.Log;


import com.example.xingyue.data.XimaLayApi;
import com.example.xingyue.interfaces.IAlbumDetailPresenter;
import com.example.xingyue.interfaces.IAlbumDetailViewCallBack;
import com.example.xingyue.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.List;

public class AlbumDetailPresenter implements IAlbumDetailPresenter {

    private static final String TAG = "AlbumDetailPresenter";
    private List<IAlbumDetailViewCallBack> iAlbumDetailCallBacks=new ArrayList<>();
    private Album mTargetAlbum;
    private int mcurrentAlbumId=-1;//当前专辑ID
    private int mcurrentPageIndex=0;//当前页
    private List<Track> mTracks=new ArrayList<>();

    private AlbumDetailPresenter(){}

    private static AlbumDetailPresenter sInstance=null;

    public static AlbumDetailPresenter getInstance(){
        if (sInstance==null) {
            synchronized (AlbumDetailPresenter.class){
                if (sInstance == null) {
                    sInstance=new AlbumDetailPresenter();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void pullDownRefreshMore() {

    }

    /**
     * 下拉加载更多
     */
    @Override
    public void loadMore() {
        mcurrentPageIndex++;
        //传入true,结果追加到列表后方
        doLoaded(true);
    }

    private void doLoaded(boolean isLoadedMore){
        XimaLayApi ximaLayApi=XimaLayApi.getXimaLayApi();
        ximaLayApi.getAlbumDetail(new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(TrackList trackList) {
                if (trackList != null) {
                    List<Track> tracks = trackList.getTracks();
                    Log.d(TAG,"tracks size is -->"+tracks.size());
                    if (isLoadedMore) {
                        mTracks.addAll(tracks);//上拉刷新，放置于列表后部
                        int size=tracks.size();
                        handlerLoaderMoreResult(size);
                    }else{
                        mTracks.addAll(0,tracks);//下拉刷新，放置于列表前部
                    }
                    handlerAlbumDetailResult( mTracks);
                }
            }

            @Override
            public void onError(int errorcode, String errorMessage) {
                Log.d(TAG,"The error code is -->"+errorcode);
                Log.d(TAG,"The error message is -->"+errorMessage);
                handlerError(errorcode,errorMessage);
            }
        },mcurrentAlbumId,mcurrentPageIndex);
    }

    /**
     * 处理加载更多的结果
     * @param size
     */
    private void handlerLoaderMoreResult(int size) {
        for (IAlbumDetailViewCallBack iAlbumDetailCallBack : iAlbumDetailCallBacks) {
            iAlbumDetailCallBack.onLoaderMoreFinish(size);
        }
    }

    @Override
    public void getAlbumDetail(int albumId, int page) {
        mTracks.clear();
        this.mcurrentAlbumId=albumId;
        this.mcurrentPageIndex=page;
        //根据页码和id获取列表
        doLoaded(false);
    }

    /**
     * 当网络发生错误时，通知UI
     * @param errorcode
     * @param errorMessage
     */
    private void handlerError(int errorcode, String errorMessage) {
        for (IAlbumDetailViewCallBack iAlbumDetailCallBack : iAlbumDetailCallBacks) {
            iAlbumDetailCallBack.onNetworkError(errorcode,errorMessage);
        }
    }

    public void handlerAlbumDetailResult(List<Track> trackList){
        for (IAlbumDetailViewCallBack iAlbumDetailCallBack : iAlbumDetailCallBacks) {
            iAlbumDetailCallBack.onDetailListLoaded(trackList);
        }
    }
    @Override
    public void registerViewCallBack(IAlbumDetailViewCallBack iAlbumDetailViewCallBack) {
        if (iAlbumDetailViewCallBack != null&&!iAlbumDetailCallBacks.contains(iAlbumDetailViewCallBack)) {
            iAlbumDetailCallBacks.add(iAlbumDetailViewCallBack);
            if (mTargetAlbum != null) {
                iAlbumDetailViewCallBack.onAlbumLoaded(mTargetAlbum);
            }
        }
    }

    @Override
    public void unRegisterViewCallBack(IAlbumDetailViewCallBack iAlbumDetailViewCallBack) {
        if (iAlbumDetailViewCallBack != null&&iAlbumDetailCallBacks.contains(iAlbumDetailViewCallBack)) {
            iAlbumDetailCallBacks.remove(iAlbumDetailViewCallBack);
        }
    }


    public void setTargetAlbum(Album album){
        mTargetAlbum = album;
        LogUtil.d(TAG,"album is -->"+album.getAlbumTitle());
    }
}
