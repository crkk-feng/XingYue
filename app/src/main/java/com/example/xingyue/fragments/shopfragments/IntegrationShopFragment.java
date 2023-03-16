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

public class IntegrationShopFragment extends BaseFragment {
    private RecyclerView rvIntegrationContainer;
    private List<ShopDataBean> mIntegrationLists=new ArrayList<>();
    private ShopAdapter shopIntegrationAdapter= new ShopAdapter();

    
    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        View rootView=LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_integration_view,container,false);
        //数据先于View进行设置，防止数据访问不到
        initData();
        initView(rootView);
        return rootView;
    }

    private void initData() {
        ShopDataBean shopData1 = new ShopDataBean(R.mipmap.tushuliubianshi,
                "【当当网 正版包邮】月亮与六便士 新版未删节插图珍藏版毛姆原著徐淳刚译获波比小说奖豆瓣阅读榜世界名著外国小说畅销",
                "¥ 19.90 ");
        ShopDataBean shopData2 = new ShopDataBean(R.mipmap.tushudrinkmoon,
                "喝月光的女孩 喝掉月亮的女孩 英文原版小说 纽伯瑞儿童文学奖 孩 赠音频 The Girl Who Drank the Moon 进口英语书籍少儿课外读物",
                " ¥ 58.00 ");
        ShopDataBean shopData3 = new ShopDataBean(R.mipmap.tushudayueliang,
                "大月亮",
                "¥ 28.50");
        ShopDataBean shopData4 = new ShopDataBean(R.mipmap.tushuyueliangmimi,
                "正版 月亮的秘密 绘本硬皮精装儿童绘本图画故事书亲子共读早教儿童读物0-3-6岁幼儿绘本阅读幼儿园小中大班阅读书籍",
                "¥ 22.30 ");
        ShopDataBean shopData5 = new ShopDataBean(R.mipmap.tushusongshuxiansheng,
                "【当当网正版童书】海豚绘本花园：松鼠先生和月亮（精）乐于助人、敢想敢做的松鼠先生 鼓励孩子释放好奇心、积",
                "¥ 15.90 ");
        ShopDataBean shopData6 = new ShopDataBean(R.mipmap.tushuundermoon,
                "在月亮下面(*级上.适合初一初二.新版)(书虫.牛津英汉双语读物)",
                "¥ 5.00 ");

        if (mIntegrationLists.size()==0) {
            mIntegrationLists.add(shopData1);
            mIntegrationLists.add(shopData2);
            mIntegrationLists.add(shopData3);
            mIntegrationLists.add(shopData4);
            mIntegrationLists.add(shopData5);
            mIntegrationLists.add(shopData6);
        }
    }

    private void initView(View rootView) {
        rvIntegrationContainer = rootView.findViewById(R.id.rv_integration_container);

        //设置布局管理器
        rvIntegrationContainer.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));


        //设置适配器
        if (shopIntegrationAdapter != null) {
            shopIntegrationAdapter.setmShopDataList(mIntegrationLists);
        }
        if (rvIntegrationContainer != null) {
            rvIntegrationContainer.setAdapter(shopIntegrationAdapter);
        }
    }
    
}
