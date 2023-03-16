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

public class ToyShopFragment extends BaseFragment {

    private RecyclerView rvToyContainer;
    private List<ShopDataBean> mToyLists=new ArrayList<>();
    private ShopAdapter shopToyAdapter= new ShopAdapter();
    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        View rootView=LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_toyshop_view,container,false);
        //数据先于View进行设置，防止数据访问不到
        initData();
        initView(rootView);
        return rootView;
    }



    private void initData() {
        ShopDataBean shopData1 = new ShopDataBean(R.mipmap.yueliangwangjile,
                "雷诺瓦进口益智玩具/几米/月亮忘记了/300片/纸质拼图/现货经典款",
                "¥ 178.00");
        ShopDataBean shopData2 = new ShopDataBean(R.mipmap.yueqiucixuanfu,
                "3d磁悬浮月球灯月亮灯星球夜灯节日送礼物网红台灯卧室床头小夜灯",
                "¥138.00");
        ShopDataBean shopData3 = new ShopDataBean(R.mipmap.yueqiujinianbi,
                "astroreality月球纪念币月亮与六便士收藏摆件工艺品中秋生日礼物",
                "¥ 76.00");
        ShopDataBean shopData4 = new ShopDataBean(R.mipmap.yueqiumoxing,
                "AstroReality黑科技月球AR模型3D打印技术超越真实月亮天文摆件",
                "¥ 480.00");
        ShopDataBean shopData5 = new ShopDataBean(R.mipmap.yueqiuxiangkuang,
                "moon生日礼物你出生那天的月亮相框定制情侣送男女生朋友纪念意义\n",
                "¥ 44.91");
        ShopDataBean shopData6 = new ShopDataBean(R.mipmap.yueqiuxingkongdeng,
                "松潮月球灯磁悬浮月亮星球灯520创意节日送礼物生日小夜灯女生",
                "¥398.00");
        if (mToyLists.size()==0) {
            mToyLists.add(shopData1);
            mToyLists.add(shopData2);
            mToyLists.add(shopData3);
            mToyLists.add(shopData4);
            mToyLists.add(shopData5);
            mToyLists.add(shopData6);
        }
    }


    private void initView(View itemView) {
        rvToyContainer = itemView.findViewById(R.id.rv_toy_container);

        //设置布局管理器
        if (rvToyContainer != null) {
            rvToyContainer.setLayoutManager(new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL));
        }

        //设置适配器
        if (shopToyAdapter != null) {
            shopToyAdapter.setmShopDataList(mToyLists);
        }
        if (rvToyContainer != null) {
            rvToyContainer.setAdapter(shopToyAdapter);
        }
    }
}
