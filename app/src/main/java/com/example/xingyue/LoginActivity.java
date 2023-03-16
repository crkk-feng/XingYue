
package com.example.xingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lib_base.data.UserLoginBean;
import com.example.xingyue.dao.UserDao;
import com.example.xingyue.utils.CustomVideoView;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private CustomVideoView videoView;
    private Button btn_enter;
    private EditText editText_account;
    private EditText editText_password;
    private RoundedImageView roundedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
    }

    private void initEvent() {
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = editText_account.getText().toString().trim();
                String s2 = editText_password.getText().toString().trim();
                UserDao userDao = new UserDao(getApplicationContext());
                loginAccount(s1,s2);
                if (userDao.check(s1,s2)==1) {
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
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

    private void loginAccount(String acc,String pass){
        OkHttpClient okHttpClient= new OkHttpClient.Builder().connectTimeout(1000, TimeUnit.MILLISECONDS).build();

        /**
         * 提交验证的账号密码信息
         */
        UserLoginBean userLoginBean = new UserLoginBean(acc, pass);
        Gson gson = new Gson();
        String postJsonStr="["+gson.toJson(userLoginBean)+"]";

        /**
         * 创建请求
         */
        MediaType mediaType= MediaType.parse("application/json");
        RequestBody requestBody= RequestBody.create(postJsonStr,mediaType);
        Request request= new Request.Builder().
                post(requestBody).
                url("http://47.98.179.53:8080/qjy_war/Hello/Login").
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
}