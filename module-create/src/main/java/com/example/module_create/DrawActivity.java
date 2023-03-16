package com.example.module_create;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Route;


@Route(path = "/draw/DrawActivity")
public class DrawActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        webView = findViewById(R.id.create_webview);
        WebViewExhibition(webView);
    }
    /*
   对通过webview展示模型进行封装
    */
    private void WebViewExhibition(WebView webview){
        WebSettings webSettings = webView.getSettings();
        //支持获取手势焦点，输入用户名、密码或其他
        webView.requestFocusFromTouch();
        //支持JS
        webSettings.setJavaScriptEnabled(true);
        //支持插件，没有该方法，
        //webSettings.setPluginsEnabled(true);

        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);//将图片调整到合适webview的大小
        webSettings.setLoadWithOverviewMode(true);//缩放至屏幕的大小

        //支持缩放，默认为true，是下面那个的前提
        webSettings.setSupportZoom(true);
        //设置内置的缩放控件，若上面是false，则该webview不可缩放，这个不管设置什么都不能缩放
        webSettings.setBuiltInZoomControls(true);

        //隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);

        //支持内容重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //多窗口
        webSettings.supportMultipleWindows();
        //关闭webView中缓存
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //当webView调用rewuestFocus时为webView设置节点
        webSettings.setNeedInitialFocus(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");

        webView.loadUrl("http://120.24.169.142:8888/index.html");

    }
}