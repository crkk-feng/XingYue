package com.example.module_microclassroom.webview;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.module_microclassroom.R;


public class WebviewActivity extends AppCompatActivity {
    private WebView vr_wb;
    private WebSettings ws;
    private String webviewUriString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_webview);
        initView();
        initEvent();

    }

    private void initEvent() {
        //更换权限
        if (Build.VERSION.SDK_INT >= 21) {
            // 5.0及以上系统，指定可以HTTPS跨域访问HTTP
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webviewUriString=getIntent().getStringExtra("nftUriString");

        vr_wb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        vr_wb.loadUrl(webviewUriString);
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        vr_wb = findViewById(R.id.vr_wb);
        ws = vr_wb.getSettings();
        ws.setPluginState(WebSettings.PluginState.ON);
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setSupportMultipleWindows(true);
        ws.setAppCacheEnabled(true);
    }
}