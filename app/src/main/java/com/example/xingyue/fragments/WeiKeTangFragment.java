package com.example.xingyue.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module_microclassroom.bean.NewsItem;
import com.example.module_microclassroom.data.NewsDataRestore;
import com.example.module_microclassroom.util.JsonParseUtil;
import com.example.xingyue.DetailActivity;
import com.example.xingyue.R;
import com.example.xingyue.VideoPlayActivity;
import com.example.xingyue.adapter.HorizontalVideoAdapter;
import com.example.xingyue.adapter.HorizontalRadioAdapter;
import com.example.xingyue.adapter.ImageAdapter;
import com.example.xingyue.data.BannerImageDataBean;
import com.example.xingyue.data.HorizontalDataBean;
import com.example.xingyue.interfaces.ISearchCallback;
import com.example.xingyue.presenter.AlbumDetailPresenter;
import com.example.xingyue.presenter.SearchPresenter;
import com.example.xingyue.utils.LogUtil;
import com.example.xingyue.views.UILoader;
import com.show.api.ShowApiRequest;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.word.HotWord;
import com.ximalaya.ting.android.opensdk.model.word.QueryResult;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeiKeTangFragment extends Fragment implements View.OnClickListener, ISearchCallback, HorizontalRadioAdapter.onAlbumItemClickListener, HorizontalVideoAdapter.onVideoItemClickListener {

    private static final String TAG = "WeiKeTangFragment";
    private View rootView;
    private Banner banner;
    private SearchPresenter mSearchPresenter;
    private List<BannerImageDataBean> bannerImageDataBeanList=new ArrayList<>();
    private List<HorizontalDataBean> videoDataBeanLists=new ArrayList<HorizontalDataBean>();//用于存储水平Video展示Item的
    private List<Album> radioItemLists=new ArrayList<>();//用于存储Audio展示数据
    private UILoader mUiLoader;

    private RecyclerView rvVideoContainer;
    private RecyclerView rvAudioContainer;
    private HorizontalRadioAdapter horizontalRadioAdapter;
    private HorizontalVideoAdapter hvAdapter;
    private int init=0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=LayoutInflater.from(getContext()).inflate(R.layout.fragment_weiketang_view,container,false);
        initPresenter();
        initData();
        initView(rootView);
        initEvent();
        return rootView;
    }

    /**
     * 设置各种事件
     */
    private void initEvent() {
        if (mSearchPresenter != null) {
            mSearchPresenter.doSearch("航天");
        }
    }

    /**
     * 注册相关的Presenter事件
     */
    private void initPresenter() {
        mSearchPresenter=SearchPresenter.getSearchPresenter();
        mSearchPresenter.registerViewCallBack(this);
    }

    /**
     * 设置Banner的图片相关数据
     *
     */
    private void initData() {
        bannerImageDataBeanList.add(new BannerImageDataBean(R.mipmap.lunbofirst));
        bannerImageDataBeanList.add(new BannerImageDataBean(R.mipmap.lunbosecond));
        bannerImageDataBeanList.add(new BannerImageDataBean(R.mipmap.lunbothird));
        bannerImageDataBeanList.add(new BannerImageDataBean(R.mipmap.lunbofouth));

        videoDataBeanLists.add(new HorizontalDataBean("星月资讯",R.mipmap.xingyuegushi));
        videoDataBeanLists.add(new HorizontalDataBean("星月课堂",R.mipmap.xingyuehistoryketang));
        videoDataBeanLists.add(new HorizontalDataBean("星云游戏",R.mipmap.xingyuegame));
        new Thread(new XingYueNewsRunnable()).start();
    }

    public class XingYueNewsRunnable implements Runnable {
        private JSONObject jsonObject;
        private int date;

        @Override
        public void run() {

            Date lastDate = NewsDataRestore.getInstance().getLastDate();
            if (lastDate != null) {
                date = lastDate.getDay();
            }else{
                init = 1;
            }
            Date dateNow = new Date();
            if (dateNow.getDay()!=date||init==1) {
                try {
                    String res=new ShowApiRequest("http://route.showapi.com/109-35",
                            "1002431",
                            "ae996d87754f480c910e0bd539f4f12e")
                            .addTextPara("channelId","")
                            .addTextPara("channelName","")
                            .addTextPara("title","航天")
                            .addTextPara("page","1")
                            .addTextPara("needContent","0")
                            .addTextPara("needHtml","0")
                            .addTextPara("needAllList","0")
                            .addTextPara("maxResult","20")
                            .addTextPara("id","")
                            .post();
                    Log.d(TAG,res);
                    jsonObject = new JSONObject(res);
                    JSONObject resJsonBody = jsonObject.getJSONObject("showapi_res_body");
                    JSONObject pageJsonBean = resJsonBody.getJSONObject("pagebean");
                    JSONArray contentJsonList = pageJsonBean.getJSONArray("contentlist");
                    Log.d(TAG,contentJsonList.toString());
                    JsonParseUtil.parseJsonDataToNewsItem(contentJsonList);
                    NewsDataRestore newsDataRestoreInstance = NewsDataRestore.getInstance();
                    List<NewsItem> newsItemsAll = newsDataRestoreInstance.getmNewsList();
                    Log.d(TAG,newsItemsAll.toString());
                    newsDataRestoreInstance.setLastDate(dateNow);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 找到相关控件
     * @param view
     */
    private void initView(View view) {
        //星月频道
        rvVideoContainer=view.findViewById(R.id.xingyue_video_container);
        //星月电台
        rvAudioContainer=view.findViewById(R.id.xingyue_audio_container);
        //首部轮播图
        banner = view.findViewById(R.id.ketangBanner);
        banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
        banner.isAutoLoop(true);//设置自动播放
        banner.setAdapter(new ImageAdapter(bannerImageDataBeanList)).setIndicator(new CircleIndicator(getContext()));

        //星月频道数据添加
        hvAdapter = new HorizontalVideoAdapter();
        //给适配器设置数据
        hvAdapter.setmVideoData(videoDataBeanLists);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        hvAdapter.setMonVideoItemClickListener(this);
        //设置布局管理器
        if (rvVideoContainer != null) {
            rvVideoContainer.setAdapter(hvAdapter);
            rvVideoContainer.setLayoutManager(linearLayoutManager);
        }
        //数据检验
        for (Album radioItemList : radioItemLists) {
            LogUtil.d(TAG,radioItemList.getAlbumTitle());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stop();//banner停止播放，防止内存泄露
    }

    @Override
    public void onActivityCreated(@Nullable  Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSearchResultLoaded(List<Album> result) {
        for (Album album : result) {
            LogUtil.d(TAG,album.getAlbumTitle());
        }
        for (int i = 0; i < 5; i++) {
            radioItemLists.add(result.get(i));
        }
        for (Album radioItem : radioItemLists) {
            LogUtil.d(TAG,radioItem.getAlbumTitle());
        }
        //星月电台数据添加
        horizontalRadioAdapter = new HorizontalRadioAdapter();
        //设置数据
        horizontalRadioAdapter.setmRadioList(radioItemLists);
        LinearLayoutManager linearLayoutManager_radio = new LinearLayoutManager(getContext());
        linearLayoutManager_radio.setOrientation(RecyclerView.HORIZONTAL);        //设置布局管理器
        if (rvAudioContainer != null) {
            rvAudioContainer.setAdapter(horizontalRadioAdapter);
            rvAudioContainer.setLayoutManager(linearLayoutManager_radio);
            horizontalRadioAdapter.notifyDataSetChanged();
        }
        horizontalRadioAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onHotWordLoaded(List<HotWord> hotWordList) {

    }

    @Override
    public void onLoadMoreResult(List<Album> result, boolean isOkay) {

    }

    @Override
    public void onRecommendWordLoaded(List<QueryResult> keyWordList) {

    }

    @Override
    public void onError(int errorCode, String errorMsg) {

    }

    @Override
    public void onItemClick(int position, Album album) {
        AlbumDetailPresenter.getInstance().setTargetAlbum(album);

        //根据位置拿参数,页面进行跳转获取数据
        Intent intent=new Intent(getContext(), DetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void itemClick(int position) {
        switch (position){
            case 0:
                LogUtil.d(TAG,"0");
                ARouter.getInstance().build("/microclassroom/NewsShowActivity")
                        .withLong("key1", 666L)
                        .withString("key2", "888")
                        .navigation();
                break;
            case 1:
                LogUtil.d(TAG,"1");
                Intent intent = new Intent(getContext(), VideoPlayActivity.class);
                startActivity(intent);
                break;
            case 2:
                LogUtil.d(TAG,"2");
                ARouter.getInstance().build("/hand/HandActivity")
                        .navigation();
                break;
            default:
                break;
        }
    }
}
