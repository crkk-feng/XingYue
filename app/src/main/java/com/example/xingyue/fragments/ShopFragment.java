package com.example.xingyue.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.xingyue.R;
import com.example.xingyue.adapter.IndicatorAdapter;
import com.example.xingyue.adapter.MainContentAdapter;
import com.example.xingyue.base.BaseFragment;
import com.example.xingyue.data.ShopDataBean;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class ShopFragment extends BaseFragment{
    private View rootView;
    private ViewPager mViewPager;
    private MagicIndicator magicIndicator;
    private IndicatorAdapter indicatorAdapter;

    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        rootView=LayoutInflater.from(getContext()).inflate(R.layout.fragment_shop_view,container,false);
        initView(rootView);
        initEvent();
        return rootView;
    }


    private void initView(View rootView) {
        magicIndicator = rootView.findViewById(R.id.magic_indicator);
        magicIndicator.setBackgroundColor(Color.parseColor("#FA7254"));

        //创建Indicator的Adapter
        indicatorAdapter = new IndicatorAdapter(getContext());
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        //commonNavigator.setAdjustMode(true);//自我调节，平分位置
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(indicatorAdapter);



        //ViewPager的部分
        MainContentAdapter mainContentAdapter = new MainContentAdapter(getFragmentManager(),
                -1);
        mViewPager = rootView.findViewById(R.id.shop_viewpager);
        mViewPager.setAdapter(mainContentAdapter);


        //把ViewPager和Indicator绑定在一起
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    public void onActivityCreated(@Nullable  Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initEvent() {
        indicatorAdapter.setOnIndicatorTabClickListener(new IndicatorAdapter.OnIndicatorTabListener() {
            @Override
            public void onTabClick(int index) {
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(index,false);
                }
            }
        });
    }

}
