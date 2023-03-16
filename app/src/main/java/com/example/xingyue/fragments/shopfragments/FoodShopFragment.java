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

public class FoodShopFragment extends BaseFragment {

    private RecyclerView rvFoodContainer;
    private List<ShopDataBean> mFoodLists =new ArrayList<>();
    private ShopAdapter shopFoodAdapter = new ShopAdapter();
    
    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        View itemView=LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_food_view,
                container,
                false);
        //数据先于View进行设置，防止数据访问不到
        initData();
        initView(itemView);
        return itemView;
    }

    private void initData() {
        ShopDataBean shopData1 = new ShopDataBean(R.mipmap.fooddangao,
                "网红冰皮月亮蛋糕巧克力爆浆麻薯糯米糍大福雪媚娘面包零食非罗森",
                        "¥ 19.90");
        ShopDataBean shopData2 = new ShopDataBean(R.mipmap.foodgaodian,
                "盒马AKOKO瑶台升明月礼盒中秋月饼流心奶黄网红糕点节日礼物",
                        "¥ 299.00");
        ShopDataBean shopData3 = new ShopDataBean(R.mipmap.foodguoba,
                "渠河月亮锅巴20袋装河南特色小吃混装网红休闲零食办公室宿舍零嘴",
                        "¥19.90");
        ShopDataBean shopData4 = new ShopDataBean(R.mipmap.foodholi,
                "好利来×NASA联名太空档案X蛋黄月饼糕点礼盒中秋送礼6枚装",
                "¥ 218.00");
        ShopDataBean shopData5 = new ShopDataBean(R.mipmap.foodyuebing,
                "十三妈妈中秋节月饼礼盒装送礼豆沙广式水果流心奶黄豆沙故宫礼品",
                        "¥ 158.00");
        ShopDataBean shopData6 = new ShopDataBean(R.mipmap.foodyueliangsu,
                "天宜园香蕉味月亮酥38g*40袋8090后童年儿时校园怀旧膨化零食小吃",
                "¥33.80");
        if (mFoodLists.size()==0) {
            mFoodLists.add(shopData1);
            mFoodLists.add(shopData2);
            mFoodLists.add(shopData3);
            mFoodLists.add(shopData4);
            mFoodLists.add(shopData5);
            mFoodLists.add(shopData6);
        }
    }

    private void initView(View itemView) {
        rvFoodContainer = itemView.findViewById(R.id.rv_clothing_container);

        //设置布局管理器
        if (rvFoodContainer != null) {
            rvFoodContainer.setLayoutManager(new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL));
        }

        //设置适配器
        if (shopFoodAdapter != null) {
            shopFoodAdapter.setmShopDataList(mFoodLists);
        }
        if (rvFoodContainer != null) {
            rvFoodContainer.setAdapter(shopFoodAdapter);
        }
    }
}
