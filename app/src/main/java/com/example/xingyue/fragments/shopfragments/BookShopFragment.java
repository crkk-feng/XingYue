package com.example.xingyue.fragments.shopfragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module_shop.data.shop.ShopDataDetailBean;
import com.example.xingyue.R;
import com.example.xingyue.ShopItemDetailActivity;
import com.example.xingyue.adapter.ShopAdapter;
import com.example.xingyue.base.BaseFragment;
import com.example.xingyue.dao.DataBaseHelper;
import com.example.xingyue.dao.ShopDao;
import com.example.xingyue.dao.ShopItemDetailDao;
import com.example.xingyue.data.ShopDataBean;

import java.util.ArrayList;
import java.util.List;

public class BookShopFragment extends BaseFragment implements ShopAdapter.OnShopItemClickListener {

    private RecyclerView rvBookContainer;
    private List<ShopDataBean> mBookLists=new ArrayList<>();
    private ShopAdapter shopBookAdapter= new ShopAdapter();

    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        View rootView=LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_book_view,
                container,
                false);
        //数据先于View进行设置，防止数据访问不到
        initData();
        initView(rootView);
        initEvent();
        return rootView;
    }

    private void initEvent() {
        if (shopBookAdapter != null) {
            shopBookAdapter.setOnShopItemClickListener(this);
        }
    }

    private void initData() {
        ShopDataBean shopData1 = new ShopDataBean(R.mipmap.tushuliubianshi,
                "【当当网 正版包邮】月亮与六便士 新版未删节插图珍藏版毛姆原著徐淳刚译获波比小说奖豆瓣阅读榜",
                "¥ 19.90 ");
        ShopDataBean shopData2 = new ShopDataBean(R.mipmap.tushudrinkmoon,
                "喝掉月亮的女孩 英文原版小说 纽伯瑞儿童文学奖 赠音频 The Girl Who Drank the Moon",
                " ¥ 58.00 ");
        ShopDataBean shopData3 = new ShopDataBean(R.mipmap.tushudayueliang,
                "大月亮",
                "¥ 28.50");
        ShopDataBean shopData4 = new ShopDataBean(R.mipmap.tushuyueliangmimi,
                "正版 月亮的秘密 绘本硬皮精装儿童绘本图画故事书亲子共读早教儿童读物0-3-6岁幼儿绘本阅读幼",
                "¥ 22.30 ");
        ShopDataBean shopData5 = new ShopDataBean(R.mipmap.tushusongshuxiansheng,
                "【当当网正版童书】海豚绘本花园：松鼠先生和月亮（精）乐于助人、敢想敢做的松鼠先生",
                        "¥ 15.90 ");
        ShopDataBean shopData6 = new ShopDataBean(R.mipmap.tushuundermoon,
                "在月亮下面(*级上.适合初一初二.新版)(书虫.牛津英汉双语读物)",
                "¥ 5.00 ");

        shopData1.setShopItemBeanId(1);
        shopData2.setShopItemBeanId(2);
        shopData3.setShopItemBeanId(3);
        shopData4.setShopItemBeanId(4);
        shopData5.setShopItemBeanId(5);
        if (mBookLists.size()==0) {
            mBookLists.add(shopData1);
            mBookLists.add(shopData2);
            mBookLists.add(shopData3);
            mBookLists.add(shopData4);
            mBookLists.add(shopData5);
            mBookLists.add(shopData6);
        }
    }

    private void initView(View rootView) {
        rvBookContainer = rootView.findViewById(R.id.rv_book_container);

        //设置布局管理器
        rvBookContainer.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));


        //设置适配器
        if (shopBookAdapter != null) {
            shopBookAdapter.setmShopDataList(mBookLists);
        }
        if (rvBookContainer != null) {
            rvBookContainer.setAdapter(shopBookAdapter);
        }
    }


    @Override
    public void ItemClick(int position, ShopDataBean shopDataBean) {
        //老版本跳转至商品详情页
//        Bundle bundle = new Bundle();
//        bundle.putInt("imageId",shopDataBean.getImageId());
//        bundle.putString("goodprice",shopDataBean.getGoodsName());
//        bundle.putString("goodName",shopDataBean.getPrice());
//
//        //将点击的商品放入Bundle容器内，传递至下一个商品展示页面
//        Intent intent = new Intent(getContext(), ShopItemDetailActivity.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
        int shopInfoFindId = shopDataBean.getShopItemBeanId();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());

        ShopItemDetailDao sdo = new ShopItemDetailDao(dataBaseHelper);
        ShopDataDetailBean sddb = sdo.queryShopItemInfo(shopInfoFindId);

        ARouter.getInstance().build("/shop/ShopItemDetailActivity")
                .withInt("shopItemId",shopInfoFindId)
                .withString("LargePicUri", sddb.getShopLargeUri())
                .withString("itemName",sddb.getShopItemName())
                .withString("itemPrice",sddb.getShopPrice())
                .withString("itemDescriptionUri",sddb.getShopItemDescriptionUri())
                .withString("itemSellerName",sddb.getShopItemSellerName())
                .withString("itemSellerUri",sddb.getShopItemSellerUri())
                .withString("itemSellerDescription",sddb.getShopItemSellerDescription())
                .withString("publisher",sddb.getPublisher())
                .withString("publishId",sddb.getPublishId())
                .withString("publishDate",sddb.getPublishDate())
                .navigation();
    }
}
