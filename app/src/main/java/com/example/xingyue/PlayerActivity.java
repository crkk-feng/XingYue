package com.example.xingyue;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;


import com.example.xingyue.adapter.PlayerTrackPagerAdapter;
import com.example.xingyue.base.BaseActivity;
import com.example.xingyue.interfaces.IPlayerCallBack;
import com.example.xingyue.presenter.PlayerPresenter;
import com.example.xingyue.utils.LogUtil;
import com.example.xingyue.views.SobPopWindow;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP;

public class PlayerActivity extends BaseActivity implements IPlayerCallBack, ViewPager.OnPageChangeListener {

    private ImageView iV_play_or_stop;
    private ImageView iV_mode_switch_btn;
    private ImageView iV_play_pre;
    private ImageView iV_play_next;
    private ImageView iV_play_list;
    private PlayerPresenter playerPresenter;

    private SimpleDateFormat minFormat=new SimpleDateFormat("mm:ss");
    private SimpleDateFormat mhourFormat=new SimpleDateFormat("hh:mm:ss");
    private TextView tv_current_position;
    private TextView tv_track_duration;
    private TextView text_track_title;
    private SeekBar mSeekBar;

    private int currentPosition=0;

    private boolean isFromUser=false;
    private ViewPager viewPager_cover;
    private PlayerTrackPagerAdapter playerTrackPagerAdapter;
    private Boolean mIsUserSlidePager=false;

    private  String mTrackTitleText=null;
    private String TAG="PlayerActivity";
    private ImageView playModeSwitch;
    private XmPlayListControl.PlayMode mCurrentMode= XmPlayListControl.PlayMode.PLAY_MODEL_LIST;
    private Boolean textOrder=false;

    private static Map<XmPlayListControl.PlayMode,XmPlayListControl.PlayMode> sPlayMode=new HashMap<>();

    public final int BG_ANIMATION_DURATION=800;

    //处理播放模式的切换
    //1、默认的是：PLAY_MODEL_LIST
    //2、列表循环：PLAY_MODEL_LIST_LOOP
    //3、随机播放：PLAY_MODEL_RANDOM
    //4、单曲循环：PLAY_MODEL_SINGLE_LOOP
    static {
        sPlayMode.put(PLAY_MODEL_LIST, PLAY_MODEL_LIST_LOOP);
        sPlayMode.put(PLAY_MODEL_LIST_LOOP, PLAY_MODEL_RANDOM);
        sPlayMode.put(PLAY_MODEL_RANDOM, PLAY_MODEL_SINGLE_LOOP);
        sPlayMode.put(PLAY_MODEL_SINGLE_LOOP, PLAY_MODEL_LIST);
    }

    private ImageView iV_mPlayModeSwitchBtn;
    private ImageView mPlayListBtn;
    private SobPopWindow mSubPopWin;
    private ValueAnimator enterBgAnimator;
    private ValueAnimator exitBgAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        //测试播放
        initView();

        playerPresenter = PlayerPresenter.getmPlayerPresenter();
        playerPresenter.registerViewCallBack(this);

        initEvent();
        initBgAnimation();
    }

    private void initBgAnimation() {
        enterBgAnimator = ValueAnimator.ofFloat(1.0f,0.7f);
        enterBgAnimator.setDuration(BG_ANIMATION_DURATION);
        enterBgAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value=(float) animation.getAnimatedValue();
                upDateBgAlpha(value);
            }
        });
        exitBgAnimator = ValueAnimator.ofFloat(0.7f, 1.0f);
        exitBgAnimator.setDuration(BG_ANIMATION_DURATION);
        exitBgAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value_exit=(float)animation.getAnimatedValue();
                upDateBgAlpha(value_exit);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerPresenter != null) {
            playerPresenter.unRegisterViewCallBack(this);//上方注册后就要取消注册
            playerPresenter=null;//释放资源
        }

    }


    private void initEvent() {
        iV_play_or_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                //如果现在的状态是开始播放，我们就放播放器停止播放；如果现在的状态是停止播放，那么我们就让播放器开始播放

                if (playerPresenter.isPlay()) {
                    playerPresenter.pause();
                }else {
                    playerPresenter.Play();
                }
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    currentPosition=progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isFromUser=true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (isFromUser) {
                    playerPresenter.seekTo(currentPosition);
                    isFromUser=false;
                }
            }
        });
        iV_play_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerPresenter.playPre();
            }
        });
        iV_play_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerPresenter.playNext();
            }
        });

        viewPager_cover.addOnPageChangeListener(this);
        viewPager_cover.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        mIsUserSlidePager=true;
                        break;
                }
                return false;
            }
        });
        playModeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //处理播放模式的切换，默认是PLAY_MODEL_SINGLE
                //根据当前的Mode获取到下面给Mode
                switchPlayMode();
            }
        });
        mPlayListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:展示播放列表
                mSubPopWin.showAtLocation(v,Gravity.BOTTOM,0,0);
                //处理一下透明的背景
                enterBgAnimator.start();
            }
        });
        mSubPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //Pop窗体消失以后，恢复
                exitBgAnimator.start();
            }
        });
        mSubPopWin.setPlayListItemClickListener(new SobPopWindow.PlayListItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //播放列表里的Item被点击
                if (playerPresenter != null) {
                    playerPresenter.playByIndex(position);
                }
            }
        });

        /**
         * 设置播放模式的切换
         * 设置顺序播放及逆序播放
         */
        mSubPopWin.setPlayListActionListener(new SobPopWindow.PlayListActionListener() {
            @Override
            public void onPlayModeClick() {
                switchPlayMode();
            }

            @Override
            public void onOrderClick() {
                if (playerPresenter != null) {
                    playerPresenter.reversePlayList();
                }
            }
        });
    }

    private void switchPlayMode() {
        XmPlayListControl.PlayMode playMode=sPlayMode.get(mCurrentMode);
        if (playerPresenter != null) {
            playerPresenter.switchPlayStatus(playMode);
        }
    }

    private void upDateBgAlpha(float alpha) {
        Window window=getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha=alpha;
        window.setAttributes(attributes);
    }

    /**
     * 根据当前的状态，更新播放模式的图标
     * //处理播放模式的切换
     *     //1、默认的是：PLAY_MODEL_LIST
     *     //2、列表循环：PLAY_MODEL_LIST_LOOP
     *     //3、随机播放：PLAY_MODEL_RANDOM
     *     //4、单曲循环：PLAY_MODEL_SINGLE_LOOP
     * @param playMode
     */
    private void updatePlayModeBtnImg(XmPlayListControl.PlayMode playMode) {
        int resId=R.drawable.selector_palyer_list;
        switch (mCurrentMode){
            case PLAY_MODEL_LIST:
                LogUtil.d(TAG,"selector_palyer_list...");
                resId=R.drawable.selector_palyer_list;
                break;
            case PLAY_MODEL_RANDOM:
                LogUtil.d(TAG,"selector_paly_mode_random...");
                resId=R.drawable.selector_paly_mode_random;
                break;
            case PLAY_MODEL_LIST_LOOP:
                LogUtil.d(TAG,"selector_paly_mode_list_order_looper...");
                resId=R.drawable.selector_paly_mode_list_order_looper;
                break;
            case PLAY_MODEL_SINGLE_LOOP:
                LogUtil.d(TAG,"selector_paly_mode_single_loop...");
                resId=R.drawable.selector_paly_mode_single_loop;
                break;
        }
        iV_mPlayModeSwitchBtn.setImageResource(resId);
    }

    private void initView() {
        iV_play_or_stop = findViewById(R.id.play_or_pause_btn);
        iV_mode_switch_btn = findViewById(R.id.player_mode_switch_btn);
        iV_play_pre = findViewById(R.id.play_pre);
        iV_play_next = findViewById(R.id.play_next);
        iV_play_list = findViewById(R.id.player_list);
        tv_current_position = findViewById(R.id.current_position);
        tv_track_duration = findViewById(R.id.track_duration);
        iV_mPlayModeSwitchBtn = findViewById(R.id.player_mode_switch_btn);
        mSeekBar=findViewById(R.id.track_seek_bar);

        text_track_title = findViewById(R.id.track_title);
        viewPager_cover = findViewById(R.id.track_pager_view);
        mPlayListBtn = findViewById(R.id.player_list);

        //创建适配器
        playerTrackPagerAdapter = new PlayerTrackPagerAdapter();
        //配置适配器
        viewPager_cover.setAdapter(playerTrackPagerAdapter);

        //切换播放模式的按钮
        playModeSwitch = findViewById(R.id.player_mode_switch_btn);
        //弹窗初始化
        mSubPopWin = new SobPopWindow();
    }

    @Override
    public void onPlayStart() {
        if (iV_play_or_stop != null) {
            iV_play_or_stop.setImageResource(R.drawable.selector_palyer_pause);//开始播放，修改UI层，使其成为暂停的按钮
        }
    }

    @Override
    public void onPlayPause() {
        if (iV_play_or_stop != null) {
            iV_play_or_stop.setImageResource(R.drawable.selector_palyer_play);//开始播放，修改UI层，使其成为暂停的按钮
        }
    }

    @Override
    public void onPlayStop() {

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
        //数据设置到适配器里
        if (playerTrackPagerAdapter != null) {
            Log.d(TAG,"playerTrackAdapter is not null..");
            Log.d(TAG,"Size of list is-->"+list.size());
            playerTrackPagerAdapter.setData(list);
        }
        //数据回来后，也要给UI层一份
        //TODO
        if (mSubPopWin != null) {
            mSubPopWin.setListData(list);
        }
    }

    @Override
    public void onPlayModeChange(XmPlayListControl.PlayMode playMode) {
        //状态改变，UI改变
        mCurrentMode=playMode;
        //改变SubPopWindow里的播放模式
        mSubPopWin.updatePlayMode(mCurrentMode);
        updatePlayModeBtnImg(playMode);
    }

    @Override
    public void onProgressChanged(long currentProgress, long total) {
        mSeekBar.setMax((int)total);
        String totalDuration;
        String currentPosition;
        //更新进度条
        if (total>60*60*1000) {
            totalDuration = mhourFormat.format(total);
            currentPosition = mhourFormat.format(currentProgress);
        }else{
            totalDuration = minFormat.format(total);
            currentPosition = minFormat.format(currentProgress);
        }
        //更新UI
        tv_current_position.setText(currentPosition);
        tv_track_duration.setText(totalDuration);
        if (!isFromUser) {
            mSeekBar.setProgress((int) currentProgress);
        }
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
            this.mTrackTitleText=track.getTrackTitle();
        }
        if (text_track_title != null) {
            //设置当前节目的标题
            text_track_title.setText(mTrackTitleText);
        }
        //当节目改变时候，根据Position更新当前的ViewPager,同时更新播放列表
        //TODO
        if (viewPager_cover != null) {
            viewPager_cover.setCurrentItem(position,true);
        }
        //设置播放列表里的位置
        if (mSubPopWin != null) {
            mSubPopWin.setCurrentPlayPosition(position);
        }
    }

    @Override
    public void updateListOrder(Boolean isReverse) {
        mSubPopWin.updateOrderIcon(isReverse);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //当页面选中时候，就去切换播放的内容
        Log.d(TAG,"onPageSelected");
        if (playerPresenter!=null && mIsUserSlidePager) {
            playerPresenter.playByIndex(position);
        }
        mIsUserSlidePager=false;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
