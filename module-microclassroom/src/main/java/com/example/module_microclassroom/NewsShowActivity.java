package com.example.module_microclassroom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module_microclassroom.adapter.NewsAdapter;
import com.example.module_microclassroom.data.NewsDataRestore;
import com.example.module_microclassroom.webview.WebviewActivity;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/microclassroom/NewsShowActivity")
public class NewsShowActivity extends AppCompatActivity implements NewsAdapter.newsClickListener {
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private List<String> newsTitleList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_show);
        mRecyclerView=findViewById(R.id.rv_news);
        initData();
        initView();
    }

    private void initData() {
        newsTitleList.add("载人航天30年 听设计师们怎么说");
        newsTitleList.add("俄罗斯航天局局长：宇宙中或存在比地球更先进的生命 正在观察人类文明");
        newsTitleList.add("俄航天集团总经理：美方已用卢布支付航天员费用");
        newsTitleList.add("俄航天集团总裁罗戈津：美方已用卢布支付美宇航员乘俄飞船费");
        newsTitleList.add("俄航天局：美方已用卢布支付宇航员乘俄飞船费用");
        newsTitleList.add("航天女神”刘洋：普通家庭的孩子，是怎么上天的？");
        newsTitleList.add("全景解析中国四大航天发射场：都是怎么选出来的？");
        newsTitleList.add("航天智慧助力疫情防控 能关联健康码的人脸识别测温一体机来了");
        newsTitleList.add("记大国重器“最强大脑”设计师陈际玮：航天科研及格线就是100分");
        newsTitleList.add("航天人对细节到底有多苛求？温旭峰用4个“9”形容");
        newsTitleList.add("航天旅程丨“追火箭”的人");
        newsTitleList.add("航天旅程｜出差天宫的爸爸给孩子的一封信");
        newsTitleList.add("我的青春故事｜戈壁滩上陪你望神箭 护神舟");
        newsTitleList.add("外媒聚焦神舟十四号载人航天任务");
        newsTitleList.add("神舟十四号航天员顺利进入天舟四号 将开展货物转运");
        newsTitleList.add("神舟十四号航天员顺利进驻天和核心舱 开启6个月在轨驻留");
        newsTitleList.add("神舟十四号3名航天员顺利进驻天和核心舱");
        newsTitleList.add("航天旅程|追逐星辰的“四大金刚” 一图读懂中国航天发射场");
        newsTitleList.add("航天“智囊团”助“神舟十四号”启航：检漏仪成必需品");
        newsTitleList.add("航天旅程丨英雄出征");
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        NewsAdapter newsAdapter = new NewsAdapter();
        newsAdapter.setmNewsList(NewsDataRestore.getInstance().getmNewsList());
        newsAdapter.setmNewsClickListener(this);
        newsAdapter.setNewsTitleStringList(newsTitleList);
        mRecyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void onNewsClick(int position) {
        Uri webUri = NewsDataRestore.getInstance().getmNewsList().get(position).getWebUri();
        Intent intent = new Intent(this, WebviewActivity.class);
        intent.putExtra("nftUriString",webUri.toString());
        startActivity(intent);
    }
}