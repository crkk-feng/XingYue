package com.example.xingyue.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.xingyue.CartActivity;
import com.example.xingyue.R;
import com.example.xingyue.adapter.RecyclerAdapter;
import com.example.xingyue.utils.ImageBlur;

import java.util.ArrayList;
import java.util.List;

public class MyFragment extends Fragment {
    private View rootView;
    private ImageView imageView;
    private TextView mTextView;
    private RecyclerView rvMine;
    private RecyclerAdapter adapter;//个人板块适配器

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=LayoutInflater.from(getContext()).inflate(R.layout.fragment_my_view,container,false);
        initView(rootView);
        initData();
        initEvent();
        return rootView;
    }

    private void initData() {
        List<String> items = new ArrayList<>();
        items.add("我的购物车");
        items.add("我的足迹");
        items.add("视频功能声明");
        items.add("用户协议");
        items.add("版权声明");
        items.add("关于作者");
        adapter.setNewData(items);
    }

    private void initEvent() {
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);

            }
        });
    }

    private void initView(View rootView) {
        imageView=rootView.findViewById(R.id.iv_large_cover);
        imageView.setImageResource(R.mipmap.myheadimage);
        ImageBlur.makeBlur(imageView,getContext());
        rvMine =rootView.findViewById(R.id.rv_tables);
        rvMine.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvMine.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapter();
        adapter.setFooterView(getFooterView());
        rvMine.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable  Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private View getFooterView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.user_item_footer_view, rvMine,false);
    }
}
