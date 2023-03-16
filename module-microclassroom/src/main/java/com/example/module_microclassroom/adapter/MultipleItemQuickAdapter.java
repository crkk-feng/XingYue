package com.example.module_microclassroom.adapter;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.module_microclassroom.R;
import com.example.module_microclassroom.bean.MultipleBean;
import com.example.module_microclassroom.multipleitembean.BannerImageListBean;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;


import java.util.List;

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleBean, BaseViewHolder> {

    public static final int TOPSEARCHBAR=0;//布局零
    public static final int TOPBANNER = 1;//布局一
    public static final int NAVIGATION = 2;//布局二
    public static final int ARTIST = 3;//布局三
    public static final int SHOPBOTTOM = 4;//布局三

    //控件部分
    private Banner banner;
    private RecyclerView artistRv;


//    //数据部分
//    private List<ArtistBean> artistList;
//    private ArtistAdapter artistAdapter;

    public MultipleItemQuickAdapter(List data) {
        super(data);//注册具体业务提供者
//        addItemType(TOPSEARCHBAR, R.layout.mult_item0_layout);
        addItemType(TOPBANNER, R.layout.mult_item1_layout);
//        addItemType(NAVIGATION, R.layout.mult_item2_layout);
//        addItemType(ARTIST, R.layout.mult_item3_layout);
//        addItemType(SHOPBOTTOM, R.layout.mult_item4_layout);
    }



    @Override
    protected void convert(BaseViewHolder helper, MultipleBean item) {
        switch (helper.getItemViewType()) {
            case TOPSEARCHBAR:
                break;
            case TOPBANNER:
                if ((item instanceof BannerImageListBean)) {
                    BannerImageListBean bannerImageListBeanItem=(BannerImageListBean) item;//中间封装轮播图的数据列表项
                    banner = helper.findView(R.id.top_banner);
                    if (banner != null) {
                        banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
                        banner.isAutoLoop(true);//设置自动播放
                        banner.setAdapter(new BannerImageAdapter(bannerImageListBeanItem.getBannerImageBeanList())).
                                setIndicator(new CircleIndicator(getContext()));
                    }
                }
                break;
//            case NAVIGATION:
////                helper.setImageResource(R.id.image2, R.drawable.ic_launcher_background);
//                break;
//            case ARTIST:
//                if ((item instanceof ArtistListBean)) {
//                    ArtistListBean artistListBean=(ArtistListBean) item;//中间封装轮播图的数据列表项
//                    artistRv = helper.findView(R.id.rv_artist);
//                    //设置布局管理器
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//                    linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
//                    artistRv.setLayoutManager(linearLayoutManager);
//                    //数据部分
//                    artistList=artistListBean.getArtistBeanList();
//                    artistAdapter = new ArtistAdapter(artistList);
//                    artistRv.setAdapter(artistAdapter);
//                }
//                break;
//            case SHOPBOTTOM:
//                if ((item instanceof ShopItemListBean)) {
//                    ShopItemListBean shopItemListBean=(ShopItemListBean) item;//中间封装轮播图的数据列表项
//                    RecyclerView shopItemRv=helper.findView(R.id.rv_shop_item);
//                    //设置布局管理器
//                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
//                    //设置循环视图的布局管理器
//                    shopItemRv.setLayoutManager(gridLayoutManager);
//                    //设置item增加和删除时的动画
//                    shopItemRv.setItemAnimator(new DefaultItemAnimator());
//
//                    ShopItemFlowAdapter shopItemFlowAdapter = new ShopItemFlowAdapter();
//                    //适配器的数据
//                    List<ShopItemBean> shopItemBeanList = shopItemListBean.getShopItemBeanList();
//                    shopItemFlowAdapter.setShopItemBeanList(shopItemBeanList);
//                    //设置适配器
//                    shopItemRv.setAdapter(shopItemFlowAdapter);
//                }
//                break;
            default:
                throw new IllegalStateException("Unexpected value: " + helper.getItemViewType());
        }
    }

}
