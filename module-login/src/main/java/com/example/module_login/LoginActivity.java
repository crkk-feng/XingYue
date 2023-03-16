package com.example.module_login;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib_base.dao.UserDao;
import com.example.lib_base.ui.CustomVideoView;
import com.makeramen.roundedimageview.RoundedImageView;

@Route(path = "/loginx/LoginActivity")
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomVideoView videoView;
    private Button btn_enter;
    private EditText editText_account;
    private EditText editText_password;
    private RoundedImageView roundedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        initView();
        initEvent();
    }

    private void initEvent() {
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = editText_account.getText().toString();
                String s2 = editText_password.getText().toString();
                UserDao userDao = new UserDao(getApplicationContext());
                if (userDao.check(s1,s2)==1) {
                    ARouter.getInstance().build("/navigation/NavigationActivity")
                            .withLong("key1", 666L)
                            .withString("key2", "888")
                            .navigation();
                }else{
                    Toast.makeText(getApplicationContext(),"账号或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        roundedImageView = findViewById(R.id.headSculpture);
        editText_account=findViewById(R.id.edit_account);
        editText_password=findViewById(R.id.edit_password);
        btn_enter = (Button) findViewById(R.id.btn_enter);//制造异常
        btn_enter.setOnClickListener(this);

        videoView = (CustomVideoView) findViewById(R.id.videoview);
        videoView.setVideoURI( Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.loginvideo));

        //播放
        videoView.start();
        //循环播放
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });

        roundedImageView.setImageResource(R.mipmap.xingyuelogo);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}