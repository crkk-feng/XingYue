package com.example.xingyue.presenter;


import com.example.lib_base.util.Constants;
import com.example.xingyue.data.XimaLayApi;
import com.example.xingyue.interfaces.ISearchCallback;
import com.example.xingyue.interfaces.ISearchPresenter;
import com.example.xingyue.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.SearchAlbumList;
import com.ximalaya.ting.android.opensdk.model.word.HotWord;
import com.ximalaya.ting.android.opensdk.model.word.HotWordList;
import com.ximalaya.ting.android.opensdk.model.word.QueryResult;
import com.ximalaya.ting.android.opensdk.model.word.SuggestWords;


import java.util.ArrayList;
import java.util.List;

public class SearchPresenter implements ISearchPresenter {
    private static final String TAG = "SearchPresenter";
    private List<Album> mSearchResult = new ArrayList<>();

    private List<ISearchCallback> mCallback = new ArrayList<>();
    private String mCurrentKeyword=null;//保存当前的搜索字段，方便用于下次搜索

    private SearchPresenter() {
        mXimalayApi = XimaLayApi.getXimaLayApi();
    }
    private XimaLayApi mXimalayApi;
    private static final int DEFAULT_PAGE = 1;
    private int mCurrentPage = DEFAULT_PAGE;

    private static SearchPresenter sSearchPresenter = null;
    private Boolean mIsLoadMore=false;
    private int mCurrentResultList;


    public static SearchPresenter getSearchPresenter() {
        if (sSearchPresenter == null) {
            synchronized (SearchPresenter.class) {
                if (sSearchPresenter == null) {
                    sSearchPresenter = new SearchPresenter();
                }
            }
        }
        return sSearchPresenter;
    }


    @Override
    public void doSearch(String keyword) {
        mCurrentPage=DEFAULT_PAGE;
        mSearchResult.clear();
        //用于新搜索
        this.mCurrentKeyword = keyword;
        Search(keyword);
    }

    private void Search(String keyword) {
        mXimalayApi.SearchByKeyWord(keyword, mCurrentPage, new IDataCallBack<SearchAlbumList>() {
            @Override
            public void onSuccess(SearchAlbumList searchAlbumList) {
                List<Album> albums = searchAlbumList.getAlbums();
                mSearchResult.addAll(albums);
                if (albums != null) {
                    LogUtil.d(TAG,"The size of albums is -->"+albums.size());
                    if (mIsLoadMore) {
                        for (ISearchCallback iSearchCallback : mCallback) {
                            iSearchCallback.onLoadMoreResult(mSearchResult,albums.size()!=0);
                        }
                        mIsLoadMore=false;
                    }
                    else {
                        for (ISearchCallback iSearchCallback : mCallback) {
                            if (albums != null) {
                                iSearchCallback.onSearchResultLoaded(mSearchResult);
                            }
                        }
                        LogUtil.d(TAG,"The size of albums is -->"+"null");
                    }
                }
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                    LogUtil.d(TAG,"The errorCode is -->"+errorCode);
                    LogUtil.d(TAG,"The errorMsg is -->"+errorMsg);
                for (ISearchCallback iSearchCallback : mCallback) {
                    if (mIsLoadMore) {
                        iSearchCallback.onLoadMoreResult(mSearchResult, false);
                        mCurrentPage--;
                        mIsLoadMore = false;
                    } else {
                        iSearchCallback.onError(errorCode, errorMsg);
                    }
                }
            }
        });
    }

    @Override
    public void reSearch() {
        Search(mCurrentKeyword);
    }

    @Override
    public void loadMore() {
        //实现下拉加载更多的逻辑
        if (mSearchResult.size()< Constants.COUNT_DEFAULT) {
            //没有必要加载更多
            for (ISearchCallback iSearchCallback : mCallback) {
                iSearchCallback.onLoadMoreResult(mSearchResult,false);
            }
        }else{
            //实现加载更多
            mIsLoadMore=true;
            mCurrentPage++;
            reSearch();
        }
    }

    @Override
    public void getHotWord() {
        mXimalayApi.getHotWords(new IDataCallBack<HotWordList>() {
            @Override
            public void onSuccess(HotWordList hotWordList) {
                if (hotWordList != null) {
                    List<HotWord> hotWords = hotWordList.getHotWordList();
                    LogUtil.d(TAG, "hotWords size -- > " + hotWords.size());
                    for (ISearchCallback iSearchCallback : mCallback) {
                        iSearchCallback.onHotWordLoaded(hotWords);
                    }
                }
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                LogUtil.d(TAG, "getHotWord errorCode -- > " + errorCode);
                LogUtil.d(TAG, "getHotWord errorMsg -- > " + errorMsg);

            }
        });
    }

    @Override
    public void getRecommendWord(String keyword) {
            mXimalayApi.getSuggestWord(keyword, new IDataCallBack<SuggestWords>() {
                @Override
                public void onSuccess(SuggestWords suggestWords) {
                    if (suggestWords != null) {
                        List<QueryResult> keyWordList = suggestWords.getKeyWordList();
                        LogUtil.d(TAG,"keyWordList size -->"+keyWordList.size());
                        for (ISearchCallback iSearchCallback : mCallback) {
                            iSearchCallback.onRecommendWordLoaded(keyWordList);//UI进行回调
                        }
                    }
                }


                @Override
                public void onError(int errorCode, String errorMsg) {
                    LogUtil.d(TAG, "getHotWord errorCode -- > " + errorCode);
                    LogUtil.d(TAG, "getHotWord errorMsg -- > " + errorMsg);
                }
            });
    }

    @Override
    public void registerViewCallBack(ISearchCallback iSearchCallback) {
        if (!mCallback.contains(iSearchCallback)) {
            mCallback.add(iSearchCallback);
        }
    }

    @Override
    public void unRegisterViewCallBack(ISearchCallback iSearchCallback) {
        mCallback.remove(iSearchCallback);
    }
}
