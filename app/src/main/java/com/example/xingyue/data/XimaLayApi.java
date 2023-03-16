package com.example.xingyue.data;

import com.example.lib_base.util.Constants;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;
import com.ximalaya.ting.android.opensdk.model.album.SearchAlbumList;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;
import com.ximalaya.ting.android.opensdk.model.word.HotWordList;
import com.ximalaya.ting.android.opensdk.model.word.SuggestWords;

import java.util.HashMap;
import java.util.Map;

public class XimaLayApi {

    public XimaLayApi(){

    }

    private static XimaLayApi ximaLayApi=null;

    public static XimaLayApi getXimaLayApi(){
        if (ximaLayApi == null) {
            synchronized (XimaLayApi.class){
                if (ximaLayApi == null) {
                    ximaLayApi=new XimaLayApi();
                }
            }
        }
        return  ximaLayApi;
    }
    /**
     * 获取推荐的专辑列表
     * @param callBack 参数
     */
    public void getRecommendList(IDataCallBack<GussLikeAlbumList> callBack){
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.LIKE_COUNT, Constants.RECOMMAND_COUNT+"");
        CommonRequest.getGuessLikeAlbum(map,callBack);
    }

    /**
     * 根据专辑的id获取到专辑内容
     * @param callBack 获取专辑详情的回调接口
     * @param mcurrentAlbumId 专辑的id
     * @param mcurrentPageIndex 专辑的页码
     */
    public void getAlbumDetail(IDataCallBack<TrackList> callBack, long mcurrentAlbumId,int mcurrentPageIndex){
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.ALBUM_ID,mcurrentAlbumId+"");
        map.put(DTransferConstants.SORT, "asc");
        map.put(DTransferConstants.PAGE, mcurrentPageIndex+"");
        map.put(DTransferConstants.PAGE_SIZE, Constants.COUNT_DEFAULT+"");
        CommonRequest.getTracks(map,callBack);
    }

    /**
     * 根据关键字进行搜索
     * @param keyword
     */
    public void SearchByKeyWord(String keyword,int pageId,IDataCallBack<SearchAlbumList> callback) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.SEARCH_KEY, keyword);
        map.put(DTransferConstants.PAGE, pageId+"");
        map.put(DTransferConstants.PAGE_SIZE, Constants.COUNT_DEFAULT+"");
        CommonRequest.getSearchedAlbums(map,callback);
    }

    /**
     * 获取推荐的热词
     * @param callback
     */
    public void getHotWords(IDataCallBack<HotWordList> callback){
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.TOP, String.valueOf(Constants.COUNT_HOT_WORD));
        CommonRequest.getHotWords(map, callback);
    }

    /**
     * 根据关键词获取联想词
     * @param keyword 关键字
     * @param callback 回调
     */
    public void getSuggestWord(String keyword,IDataCallBack<SuggestWords> callback){
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.SEARCH_KEY, keyword);
        CommonRequest.getSuggestWord(map, callback);
    }
}
