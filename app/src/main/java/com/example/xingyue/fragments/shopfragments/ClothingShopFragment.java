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

public class ClothingShopFragment extends BaseFragment {

    private RecyclerView rvClothingContainer;
    private List<ShopDataBean> mClothingLists=new ArrayList<>();
    private ShopAdapter shopClothingAdapter= new ShopAdapter();

    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        View itemView=LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_clothing_view,
                container,
                false);
        //数据先于View进行设置，防止数据访问不到
        initData();
        initView(itemView);
        return itemView;
    }

    private void initData() {
        ShopDataBean shopData1 = new ShopDataBean(R.mipmap.clothingsweater,
                "稻草人纯棉长袖T恤男士秋季小鱼月亮印花上衣潮牌ins休闲宽松上衣",
                        "¥ 78.00");
        ShopDataBean shopData2 = new ShopDataBean(R.mipmap.clothingbanyueqiu,
                "nasa宇航员长袖t恤男2021新款半月球宽松百搭上衣纯棉欧美打底衫",
                "¥ 69.00");
        ShopDataBean shopData3 = new ShopDataBean(R.mipmap.clothingyinhuagaoling,
                "Marine Serre女士月亮印花高领上衣FARFETCH发发奇",
                        "¥ 1780.00");
        ShopDataBean shopData4 = new ShopDataBean(R.mipmap.clothingyueqiuhuaban,
                "设无界 中国航天宇航员月球滑板潮酷短袖T恤男女个性青年体恤0015",
                        "¥ 59.90");
        ShopDataBean shopData5 = new ShopDataBean(R.mipmap.clothingyueqiuyinhua,
                "夏季月球印花短袖T恤男潮牌超火百搭ins打底衫2021新款半袖体恤衫",
                "¥68.00");
        ShopDataBean shopData6 = new ShopDataBean(R.mipmap.clothingyuexiansen,
                "兔先森秋季文字月球印花连帽卫衣青年男潮宽松百搭休闲上衣外套",
                        "¥158.00");
        if (mClothingLists.size()==0) {
            mClothingLists.add(shopData1);
            mClothingLists.add(shopData2);
            mClothingLists.add(shopData3);
            mClothingLists.add(shopData4);
            mClothingLists.add(shopData5);
            mClothingLists.add(shopData6);
        }

    }

    private void initView(View itemView) {
        rvClothingContainer = itemView.findViewById(R.id.rv_clothing_container);

        //设置布局管理器
        if (rvClothingContainer != null) {
            rvClothingContainer.setLayoutManager(new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL));
        }

        //设置适配器
        if (shopClothingAdapter != null) {
            shopClothingAdapter.setmShopDataList(mClothingLists);
        }
        if (rvClothingContainer != null) {
            rvClothingContainer.setAdapter(shopClothingAdapter);
        }
    }
}
