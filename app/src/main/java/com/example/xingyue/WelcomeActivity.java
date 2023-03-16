package com.example.xingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.xingyue.dao.DataBaseHelper;

public class WelcomeActivity extends AppCompatActivity {

    private Handler mHandler;
    private TextView tvRegister;
    private TextView tvLogin;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //获取全屏视角
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //进行延时操作，启动主页面
        startMainActivity();

        //注册事件
        initView();
        initEvent();
//        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
//        dataBaseHelper.getWritableDatabase();
    }

    private void initView() {
        tvRegister=findViewById(R.id.register_text);
        tvLogin=findViewById(R.id.login_text);
    }

    private void initEvent() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.removeCallbacks(runnable);
                startActivity(new Intent(getApplicationContext(), RegisterAcitivity.class));
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.removeCallbacks(runnable);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    private void startMainActivity() {
        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //页面进行跳转
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                //欢迎页面内存释放，进行销毁
                WelcomeActivity.this.finish();
            }
        };
        //延时操作
        mHandler.postDelayed(runnable,3000);//延时三秒
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 ){
            //屏蔽返回键,点返回键的时候，为了防止点得过快，触发两次后退事件，故做此设置。
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }

}