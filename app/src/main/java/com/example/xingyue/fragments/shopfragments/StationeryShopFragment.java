package com.example.xingyue.fragments.shopfragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.xingyue.R;
import com.example.xingyue.adapter.ShopAdapter;
import com.example.xingyue.base.BaseFragment;
import com.example.xingyue.data.ShopDataBean;

import java.util.ArrayList;
import java.util.List;

public class StationeryShopFragment extends BaseFragment {

    private RecyclerView rvStationeryContainer;
    private List<ShopDataBean> mStationeryLists=new ArrayList<>();
    private ShopAdapter shopStationeryAdapter= new ShopAdapter();


    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        View rootView=LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_stationneary_view,
                container,
                false);
        //数据先于View进行设置，防止数据访问不到
        initData();
        initView(rootView);
        return rootView;
    }

    private void initData() {
        ShopDataBean shopData1 = new ShopDataBean(R.mipmap.wenjuaccountbook,
                "The moon月亮手帐周记/全年A5自填/时间管理/灼热Flamin中秋礼物",
                                "¥268.00");
        ShopDataBean shopData2 = new ShopDataBean(R.mipmap.wenjubidai,
                "芝芝酱笔袋大容量铅笔收纳袋文具盒拉链油画适用ins女款可爱学生",
                " ¥ 28.80 ");
        ShopDataBean shopData3 = new ShopDataBean(R.mipmap.wenjunotebook,
                "捞月亮的人笔记本子手绘卡通插画创意治愈手账本学生文具日记本",
                "¥ 18.50");
        ShopDataBean shopData4 = new ShopDataBean(R.mipmap.wenjuqianbi,
                "手账本套装礼盒少女可爱心简约ins风贴纸本子女生款加厚网格方格小仙女手帐本精致网红笔记本彩页手杖小学生",
                "¥ 23.80 ");
        ShopDataBean shopData5 = new ShopDataBean(R.mipmap.wenjuxinzhi,
                "日月星辰文艺风景手信礼盒生日礼物信封信纸套装精美祝福贺卡套装",
                "¥ 9.90 ");
        ShopDataBean shopData6 = new ShopDataBean(R.mipmap.wenjuyuhangyuanbitong,
                "宇航员笔筒收纳摆件创意时尚收纳筒办公室桌面笔筒可爱毕业礼物学生男生书桌摆件简约个性家用装饰容纳笔筒",
                "¥ 38.20 ");

        if (mStationeryLists.size()==0) {
            mStationeryLists.add(shopData1);
            mStationeryLists.add(shopData2);
            mStationeryLists.add(shopData3);
            mStationeryLists.add(shopData4);
            mStationeryLists.add(shopData5);
            mStationeryLists.add(shopData6);
        }
    }

    private void initView(View rootView) {
        rvStationeryContainer = rootView.findViewById(R.id.rv_stationery_container);

        //设置布局管理器
        rvStationeryContainer.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));


        //设置适配器
        if (shopStationeryAdapter != null) {
            shopStationeryAdapter.setmShopDataList(mStationeryLists);
        }
        if (rvStationeryContainer != null) {
            rvStationeryContainer.setAdapter(shopStationeryAdapter);
        }
    }
}
