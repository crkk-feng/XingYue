package com.example.xingyue;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.xingyue.adapter.DetailListRecyclerAdapter;
import com.example.xingyue.base.BaseActivity;
import com.example.xingyue.interfaces.IAlbumDetailViewCallBack;
import com.example.xingyue.interfaces.IPlayerCallBack;
import com.example.xingyue.presenter.AlbumDetailPresenter;
import com.example.xingyue.presenter.PlayerPresenter;
import com.example.xingyue.utils.ImageBlur;
import com.example.xingyue.utils.LogUtil;
import com.example.xingyue.views.UILoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

import static com.ximalaya.ting.android.opensdk.util.Utils.getContext;

public class
DetailActivity extends BaseActivity implements IAlbumDetailViewCallBack, UILoader.OnRetryClickListener, DetailListRecyclerAdapter.onItemClickListener, IPlayerCallBack{

    private static final String TAG = "DetailActivity";
    private ImageView largeImage;
    private TextView titleTv;
    private ImageView detail_play_control;
    private TextView mSubBtn;
    private ImageView detail_arrow_image;
    private ImageView round_image;
    private TextView write_intro_tv;
    private AlbumDetailPresenter malbumDetailPresenter;
    private TextView author_tv;
    private int mCurrentPage=1;
    private RecyclerView detailListRecycler;
    private DetailListRecyclerAdapter ra;
    private FrameLayout frameLayout;
    private UILoader uiLoader;
    private int mCurrentId=-1;
    private PlayerPresenter playerPresenter;
    private ImageView play_control_btn;
    private TextView player_control_tv;
    private List<Track> mCurrentPlayList=null;
    private static int DEFAULT_PLAY_TRACK_POSITION=0;
    private boolean mIsLoaded=false;
    private String trackTitle;
    private String mCurrenttrackTitle;
//    private ISubscriptionPresenter mSP;
    private Album mCurrentAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //使得状态栏的颜色变透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        //使得APP窗口覆盖整个窗口，实现沉浸式体验
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        //初始化构件
        initView();
        initPresenter();
        //初始化进入
//        upDateSubscription();
        upDatePlayStatus(playerPresenter.isPlay());

        //设置点击事件
        initEvent();
        //获取专辑的内容
        //TODO
//        malbumDetailPresenter.getAlbumDetail();
    }

//    private void upDateSubscription() {
//        if (mSP != null) {
//            boolean is_sub = mSP.isSub(mCurrentAlbum);
//            mSubBtn.setText(is_sub ? R.string.cancel_sub_tips_text : R.string.sub_tips_text);
//        }
//    }

    private void initPresenter() {
        malbumDetailPresenter = AlbumDetailPresenter.getInstance();
        malbumDetailPresenter.registerViewCallBack(this);
        //获取PlayPresenter，用于修改图标及UI
        playerPresenter = PlayerPresenter.getmPlayerPresenter();
        playerPresenter.registerViewCallBack(this);
        //初始化订阅的Presenter
//        mSP = SubscriptionPresenter.getInstance();
//        mSP.registerViewCallBack(this);
    }


    private void initEvent() {
        play_control_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断播放器是否有播放列表，如果没有，则从当前列表的第一首歌曲进行播放
                //TODO
                if (playerPresenter != null) {
                    boolean has = playerPresenter.hasPlayList();
                    if (has) {
                        handleHasPlayList();
                    }else {
                        handleNonePlayList();
                    }
                }
            }
        });
//        mSubBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mSP != null) {
//                    if (mSP.isSub(mCurrentAlbum)) {
//                        mSP.deleteSubscription(mCurrentAlbum);
//                    }else {
//                        mSP.addSubscription(mCurrentAlbum);
//                    }
//                }
//            }
//        });
    }

    private void handleNonePlayList() {
        playerPresenter.setPlayList(mCurrentPlayList,DEFAULT_PLAY_TRACK_POSITION);
    }

    private void handleHasPlayList() {
        if (playerPresenter.isPlay()) {
            playerPresenter.pause();
        }else{
            playerPresenter.Play();
        }
    }

    private void initView() {
        frameLayout = findViewById(R.id.detail_list_container);
        //使用UILoader
        if (uiLoader == null) {
            //UILoader为空时进行创建
            uiLoader = new UILoader(this) {
                @Override
                protected View getSuccessView(ViewGroup container) {
                    return createSuccessView(container);
                }
            };
        }

        largeImage = findViewById(R.id.iv_large_cover);
        titleTv = findViewById(R.id.tv_album_title);
        round_image = findViewById(R.id.viv_small_cover);
        detail_play_control = findViewById(R.id.detail_play_control);
        author_tv = findViewById(R.id.tv_album_author);
        player_control_tv = findViewById(R.id.play_control_tv);
        mSubBtn = findViewById(R.id.detail_sub_btn);
        player_control_tv.setSelected(true);
        detail_arrow_image = findViewById(R.id.iv_large_cover);
        play_control_btn = findViewById(R.id.detail_play_control);

        frameLayout.removeAllViews();//添加之前先去处所有的View
        uiLoader.setOnRetryClickListener(this);
        frameLayout.addView(uiLoader);

    }

    private View createSuccessView(ViewGroup container) {
        View detailListView=LayoutInflater.from(this).inflate(R.layout.item_detail_list,container,false);
        detailListRecycler = detailListView.findViewById(R.id.detail_reclcyer_list);
        //寻找到控件
//        refreshLayout = detailListView.findViewById(R.id.refresh_layout);
        //ink设置RecyclerView,第一步：设置布局管理器
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        detailListRecycler.setLayoutManager(linearLayoutManager);
        //第二部设置适配器
        ra = new DetailListRecyclerAdapter();
        ra.setOnItemClickListener(this);
        detailListRecycler.setAdapter(ra);
        detailListRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top= UIUtil.dip2px(getContext(),5);
                outRect.bottom=UIUtil.dip2px(getContext(),5);
                outRect.left=UIUtil.dip2px(getContext(),5);
                outRect.right=UIUtil.dip2px(getContext(),5);
            }
        });

//        BezierLayout bezierLayout = new BezierLayout(this);
//        refreshLayout.setHeaderView(bezierLayout);
        //给refreshLayout设置刷新事件
//        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
//            @Override
//            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
//                BaseApplication.getHandler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        LogUtil.d(TAG,"进入刷新界面了");
//                        Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT);
//                        refreshLayout.finishRefreshing();
//                    }
//                },2000);
//            }
//
//            @Override
//            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
//                super.onLoadMore(refreshLayout);
//                //TODO:去加载更多的内容
//                if (malbumDetailPresenter != null) {
//                    malbumDetailPresenter.loadMore();
//                    mIsLoaded=true;
//                }
////                BaseApplication.getHandler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        LogUtil.d(TAG,"进入刷新界面二了");
////                        Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT);
////                        refreshLayout.finishLoadmore();
////                    }
////                },2000);
//            }
//        });
        return detailListView;
    }


    @Override
    public void onDetailListLoaded(List<Track> tracks) {
        this.mCurrentPlayList=tracks;
//        if (mIsLoaded && refreshLayout!=null) {
//            mIsLoaded=false;
//            refreshLayout.finishLoadmore();
//        }
        //判断更新数据，根据结果控制UI显示
        if (tracks == null || tracks.size()==0) {
            if (uiLoader != null) {
                uiLoader.updateStatus(UILoader.UIStatus.EMPTY);
            }
        }else if(uiLoader!=null){
            uiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
        }
        //更新或者设置UI
        ra.setData(tracks);
    }

    @Override
    public void onAlbumLoaded(Album album) {
        mCurrentAlbum = album;
        long id=album.getId();
        if (malbumDetailPresenter != null) {
            malbumDetailPresenter.getAlbumDetail((int)id,mCurrentPage);
        }
        mCurrentId= (int) id;
        //拿到列表，显示Loading状态
        if (uiLoader != null) {
            uiLoader.updateStatus(UILoader.UIStatus.LOADING);
        }
        if (album != null &&titleTv!=null) {
            titleTv.setText(album.getAlbumTitle() );
        }
        if (album!=null && round_image!=null) {
            Picasso.with(this).load(album.getCoverUrlLarge()).into(round_image);
        }
        if (album != null&& author_tv!=null) {
            author_tv.setText(album.getAnnouncer().getNickname());
        }
        /**
         * 做高斯模糊的效果
         */
        if (largeImage != null&&album != null && null!=largeImage) {
            /**
             * Picasso请求是异步的
             */
            Picasso.with(this).load(album.getCoverUrlLarge()).into(largeImage, new Callback() {
                @Override
                public void onSuccess() {
                    Drawable drawable = largeImage.getDrawable();
                    if (drawable != null) {
                        //到这里才说明是有图片的
                        ImageBlur.makeBlur(largeImage, DetailActivity.this);
                    }
                }

                @Override
                public void onError() {
                    LogUtil.d(TAG, "onError");
                }
            });
        }
    }

    @Override
    public void onNetworkError(int errorcode, String errorMessage) {
        //请求发生错误，显示网络异常状态
        uiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
    }

    @Override
    public void onLoaderMoreFinish(int size) {
        if (size>0) {
            Toast.makeText(this,"成功加上条目",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"没有更多的节目",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefreshFinish(int size) {

    }

    @Override
    public void onRetryClick() {
        //表示用户网络不佳时进行重新点击
        if (malbumDetailPresenter != null) {
            malbumDetailPresenter.getAlbumDetail((int)mCurrentId,mCurrentPage);
        }
    }

    @Override
    public void onItemClick(List<Track> mtrackList, int position) {
        //TODO:跳转到播放器页面，设置播放器的数据
        playerPresenter = PlayerPresenter.getmPlayerPresenter();
        if (playerPresenter != null) {
            playerPresenter.setPlayList(mtrackList,position);
            LogUtil.d(TAG,"position now is -->"+position);
        }
        Intent intent = new Intent(this, PlayerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPlayStart() {
        //文字修改为正在播放
        upDatePlayStatus(true);
    }

    private void upDatePlayStatus(boolean play) {
        if (play_control_btn!=null) {
            play_control_btn.setImageResource(play?
                    R.drawable.selector_play_control_pause:
                    R.drawable.selector_play_control_play);
            if (play) {
                player_control_tv.setText(R.string.pause_tips_next);
            }else {
                if (!TextUtils.isEmpty(mCurrenttrackTitle)) {
                    player_control_tv.setText(mCurrenttrackTitle);
                }
            }
        }
    }

    @Override
    public void onPlayPause() {
        //文字修改为暂停
        upDatePlayStatus(false);
    }

    @Override
    public void onPlayStop() {
        upDatePlayStatus(false);
    }

    @Override
    public void onPlayError() {

    }

    @Override
    public void nextPlay(Track track) {

    }

    @Override
    public void onPrePlay(Track track) {

    }

    @Override
    public void onListLoaded(List<Track> list) {

    }

    @Override
    public void onPlayModeChange(XmPlayListControl.PlayMode playMode) {

    }

    @Override
    public void onProgressChanged(long currentProgress, long total) {

    }

    @Override
    public void onAdLoading() {

    }

    @Override
    public void onAdFinish() {

    }

    @Override
    public void updateTrack(Track track, int position) {
        if (track != null) {
            mCurrenttrackTitle = track.getTrackTitle();
            player_control_tv.setText(trackTitle);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (malbumDetailPresenter != null) {
            malbumDetailPresenter.unRegisterViewCallBack(this);
        }
        if (playerPresenter != null) {
            playerPresenter.unRegisterViewCallBack(this);
        }
//        if (mSP != null) {
//            mSP.unRegisterViewCallBack(this);
//        }
    }

    @Override
    public void updateListOrder(Boolean isReverse) {

    }

//    @Override
//    public void onAddResult(boolean isSuccess) {
//        if (isSuccess) {
//            //如果成功了，那就修改UI成取消订阅
//            mSubBtn.setText(R.string.cancel_sub_tips_text);
//        }
//        //给个toast
//        String tipsText = isSuccess ? "订阅成功" : "订阅失败";
//        Toast.makeText(this, tipsText, Toast.LENGTH_SHORT).show();
//
//    }

//    @Override
//    public void onDeleteResult(boolean isSuccess) {
//        if (isSuccess) {
//            //如果成功了，那就修改UI成取消订阅
//            mSubBtn.setText(R.string.sub_tips_text);
//        }
//        //给个toast
//        String tipsText = isSuccess ? "删除成功" : "删除失败";
//        Toast.makeText(this, tipsText, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onSubscriptionsLoaded(List<Album> albums) {
//
//    }
//
//    @Override
//    public void onSubFull() {
//        //处理一个即可
//        Toast.makeText(DetailActivity.this,"订阅太多，不得超过100",Toast.LENGTH_SHORT).show();
//    }
}