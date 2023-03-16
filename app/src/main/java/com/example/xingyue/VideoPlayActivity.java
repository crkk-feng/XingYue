package com.example.xingyue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.module_shop.data.ShopItemPostBean;
import com.example.xingyue.adapter.HorizontalTextAdapter;
import com.example.xingyue.adapter.XingYueClassCommentsAdapter;
import com.example.xingyue.dao.CommentDao;
import com.example.xingyue.dao.DataBaseHelper;
import com.example.xingyue.data.CommentDataBean;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.ximalaya.ting.android.opensdk.util.Utils.getContext;


public class VideoPlayActivity extends AppCompatActivity implements HorizontalTextAdapter.onVideoTextItemClickListener {
    private static final String TAG = "VideoPlayActivity";
    private RecyclerView rvVideoContainer;
    private HorizontalTextAdapter horizontalTextAdapter;
    private TextView videoTvComment;
    private BottomSheetDialog dialog;
    StandardGSYVideoPlayer videoPlayer;

    OrientationUtils orientationUtils;

    private Button publishButton;
    //测试的视频链接地址
    String source1 = "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/812358b69886e576c66a01f1f00affe9.mp4";
    String []videoaddress={
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/812358b69886e576c66a01f1f00affe9.mp4",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/11c70c96529b6e6938567ec1aa0910e0.mp4",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/7cdabcaa763392c86b944eaf4e68d6a3.mp4",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/812358b69886e576c66a01f1f00affe9.mp4",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/11c70c96529b6e6938567ec1aa0910e0.mp4",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/7cdabcaa763392c86b944eaf4e68d6a3.mp4",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/812358b69886e576c66a01f1f00affe9.mp4",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/11c70c96529b6e6938567ec1aa0910e0.mp4",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/7cdabcaa763392c86b944eaf4e68d6a3.mp4",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/812358b69886e576c66a01f1f00affe9.mp4",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/11c70c96529b6e6938567ec1aa0910e0.mp4",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/7cdabcaa763392c86b944eaf4e68d6a3.mp4"
    };
    String []keTangName={"星月课堂第一讲","星月课堂第二讲","星月课堂第三讲",
            "星月课堂第四讲","星月课堂第五讲","星月课堂第六讲",
            "星月课堂第七讲","星月课堂第八讲","星月课堂第九讲",
            "星月课堂第十讲","星月课堂第十一讲"};
    private CommentDao commentDao=new CommentDao(getContext());
    private RecyclerView rvCommentsContainer;
    private XingYueClassCommentsAdapter xingYueClassCommentsAdapter;
    private List<CommentDataBean> commentDataBeanList;

//    private JCVideoPlayer videoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        //GSYVideoPlayer gsyVideoPlayer = new GSYVideoPlayer();
        initData();
        initView();
        initEvent();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        dataBaseHelper.getWritableDatabase();


    }

    /**
     * 从数据库中将用户评论拿出
     */
    private void initData() {
        //评论数据
        commentDataBeanList = commentDao.queryAll();
        getComment();
    }

    private void initEvent() {
        videoPlayer.setUp(source1, true, "星月课堂第一讲");

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.xingyuegushi);
        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ------- ！！！如果不需要旋转屏幕，可以不调用！！！-------
                // 不需要屏幕旋转，还需要设置 setNeedOrientationUtils(false)
                //orientationUtils.resolveByClick();
                finish();
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ///不需要屏幕旋转
        videoPlayer.setNeedOrientationUtils(false);
        videoPlayer.startPlayLogic();
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调用方法
                initCommentDialog();
            }
        });
    }

    private void initCommentDialog() {

    }

    private void initView() {
        rvVideoContainer = findViewById(R.id.rv_video_container);
        videoPlayer=findViewById(R.id.video_player);
        publishButton=findViewById(R.id.comment_bt);
        horizontalTextAdapter = new HorizontalTextAdapter();
        videoTvComment=findViewById(R.id.video_tv_comment);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvVideoContainer.setLayoutManager(linearLayoutManager);
        //适配器设置点击事件
        horizontalTextAdapter.setOnVideoTextItemClickListener(this);
        //设置适配器
        rvVideoContainer.setAdapter(horizontalTextAdapter);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        rvCommentsContainer = findViewById(R.id.rv_comments_container);
        rvCommentsContainer.setLayoutManager(linearLayoutManager1);
        xingYueClassCommentsAdapter = new XingYueClassCommentsAdapter();
        if (xingYueClassCommentsAdapter != null) {
            xingYueClassCommentsAdapter.setCommentDataList(commentDataBeanList);
        }
        if (rvCommentsContainer != null) {
            rvCommentsContainer.setAdapter(xingYueClassCommentsAdapter);
        }
        videoTvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentDialog();
            }
        });
    }



    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
///       不需要回归竖屏
//        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            videoPlayer.getFullscreenButton().performClick();
//            return;
//        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

    @Override
    public void itemClick(int position) {
        if (videoPlayer != null) {
            videoPlayer.setUp(videoaddress[position],true,keTangName[position%3]);
        }
    }

    private void showCommentDialog() {
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.dialog_comment,null);
        final EditText commentText = commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);

        //解决显示不全的情况
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){
                    dialog.dismiss();
                    postComment(commentContent);
                    Toast.makeText(VideoPlayActivity.this,"留言成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(VideoPlayActivity.this,"留言内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialog.show();
    }

    private void postComment(String commentContent){
        OkHttpClient okHttpClient= new OkHttpClient.Builder().connectTimeout(1000, TimeUnit.MILLISECONDS).build();

        /**
         * 创建请求
         */
        MediaType mediaType= MediaType.parse("multipart/form-data");


        RequestBody requestBody= new FormBody.Builder()
                .add("username", "Julian")
                .add("headUri","https://img.woyaogexing.com/2015/12/01/1c009f2064f543d0!200x200.jpg")
                .add("sumofThumbsUp", String.valueOf(new Integer(1)))
                .add("contentOfComment",commentContent)
                .build();
        Request request= new Request.Builder().
                post(requestBody).
                url("http://120.24.169.142:8888/comment/addComment").
                build();

        /**
         * 浏览器创建数据
         */
        Call task= okHttpClient.newCall(request);
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"onFailure->"+e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code=response.code();
                Log.d(TAG,"code->"+code);
                ResponseBody body= response.body();
                Log.d(TAG,"body->"+body.string());
            }
        });
    }

    private void getComment(){
        String requestUri="http://120.24.169.142:8888/comment/queryComment/1";
        Log.d(TAG,requestUri);
        /*
        要有客户端，类似于我们要有一个浏览器
         */
        OkHttpClient okHttpClient= new OkHttpClient.Builder().connectTimeout(1000, TimeUnit.MILLISECONDS).build();
        /*
        创建链接，请求内容
         */
        Request request= new Request.Builder().get().url(requestUri).build();

        /*
        用client创建请求任务
         */
        Call task=okHttpClient.newCall(request);

        /*
        异步请求
         */
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("TAG","onFailure->"+e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code=response.code();
                Log.d("TAG","code->"+code);
                ResponseBody body= response.body();
                Log.d(TAG,body.string());
            }
        });
    }
}