package com.example.xingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lib_base.data.UserLoginBean;
import com.example.xingyue.dao.UserDao;
import com.example.lib_base.util.StringUtil;
import com.google.gson.Gson;

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

public class RegisterAcitivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText editText;
    private EditText editTextPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_acitivity);
        initView();
        initEvent();
    }

    private void initEvent() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDao userDao = new UserDao(getApplicationContext());
                String acc = editText.getText().toString();
                String pwd = editTextPwd.getText().toString();

                if (!StringUtil.isEmpty(acc)&&!StringUtil.isEmpty(pwd)) {
                    if (userDao.query(acc)==1) {
                        Toast.makeText(getApplicationContext(),"该账号已存在",Toast.LENGTH_SHORT).show();
                    }else {
                        userDao.insert(acc,pwd);
                        Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initView() {
        btnRegister = findViewById(R.id.button_register);
        editText = findViewById(R.id.edit_id_Num);
        editTextPwd = findViewById(R.id.edit_Password);
    }


}