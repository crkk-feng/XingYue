package com.example.module_shop.adapter;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.module_shop.R;

import com.example.module_shop.data.ArtistBean;
import com.example.module_shop.data.LargePicBean;
import com.example.module_shop.data.MultipleBean;
import com.example.module_shop.data.PriceAndNameBean;
import com.example.module_shop.data.PublishInformationBean;
import com.example.module_shop.data.SellerInformationBean;
import com.example.module_shop.data.multipleitembean.BannerImageListBean;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;


public class MultipleShopItemDetailAdapter extends BaseMultiItemQuickAdapter<MultipleBean, BaseViewHolder> {

    public static final int TOPBANNER = 0;//顶部图片
    public static final int NAMEANDPRICE = 1;//布局一
    public static final int ITEMSELLERINTRODUCE= 2;//布局二
    public static final int BUYERRECENTYL = 3;//布局三
    public static final int COLLECTIONDESC = 4;//布局三
    public static final int COLLECTIONINFO = 5;//布局三

    //控件部分
    private Banner banner;
    private RecyclerView artistRv;


    //数据部分
    private List<ArtistBean> artistList;
    private ArtistAdapter artistAdapter;

    public MultipleShopItemDetailAdapter(List data) {
        super(data);//注册具体业务提供者
        addItemType(TOPBANNER, R.layout.mult_item1_layout);
        addItemType(NAMEANDPRICE, R.layout.mult_item_detail_1_layout);
        addItemType(ITEMSELLERINTRODUCE, R.layout.mult_item_detail_2_layout);
        addItemType(BUYERRECENTYL, R.layout.mult_item_detail_3_layout);
        addItemType(COLLECTIONDESC, R.layout.mult_item_detail_4_layout);
        addItemType(COLLECTIONINFO, R.layout.mult_item_detail_5_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleBean item) {
        switch (helper.getItemViewType()) {
            case TOPBANNER:
                if ((item instanceof BannerImageListBean)) {
                    BannerImageListBean bannerImageListBeanItem=(BannerImageListBean) item;//中间封装轮播图的数据列表项
                    banner = helper.findView(R.id.banner_shop);
                    if (banner != null) {
                        banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
                        banner.isAutoLoop(true);//设置自动播放
                        banner.setAdapter(new BannerImageAdapter(bannerImageListBeanItem.getBannerImageBeanList())).
                                setIndicator(new CircleIndicator(getContext()));
                    }
                }
                break;
            case NAMEANDPRICE:
                if ((item instanceof PriceAndNameBean)) {
                    PriceAndNameBean priceAndName=(PriceAndNameBean) item;//中间封装轮播图的数据列表项
                    TextView tvItemPrice=helper.findView(R.id.tv_Item_Price);
                    TextView tvItemName=helper.findView(R.id.tv_Item_Name);
                    if (tvItemPrice != null) {
                        tvItemPrice.setText(priceAndName.getItemPrice());
                        tvItemPrice.setVisibility(TextView.VISIBLE);
                    }
                    if (tvItemName != null) {
                        tvItemName.setText(priceAndName.getItemName());
                        tvItemName.setVisibility(TextView.VISIBLE);
                    }
                }
                break;
            case ITEMSELLERINTRODUCE:
                if ((item instanceof SellerInformationBean)) {
                    SellerInformationBean sellerInformationBean=(SellerInformationBean)item;

                    TextView tvSellerName=helper.findView(R.id.tv_seller_name);
                    TextView tvSellerIntro=helper.findView(R.id.tv_seller_intro);
                    ImageView ivSellerHead=helper.findView(R.id.seller_head);

                    tvSellerName.setText(sellerInformationBean.getSellerName());
                    tvSellerIntro.setText(sellerInformationBean.getSellerIntro());
                    Glide.with(getContext()).load(sellerInformationBean.getSellerUri()).into(ivSellerHead);
                }
                break;
            case BUYERRECENTYL:
                TextView tVRecently=helper.findView(R.id.tv_buyer_recently);
                tVRecently.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"最近购买被点击了",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case COLLECTIONDESC:
                if ((item instanceof LargePicBean)) {
                    ImageView ivDesc = helper.findView(R.id.iv_item_description);
                    LargePicBean largePicBean=(LargePicBean) item;
                    Uri largePicUri = largePicBean.getLargePicUri();
                    Glide.with(getContext()).load(largePicUri).into(ivDesc);
                }
                break;
            case COLLECTIONINFO:
                if ((item instanceof PublishInformationBean)) {
                    PublishInformationBean publishInformationBean=(PublishInformationBean) item;//中间封装轮播图的数据列表项

                    TextView tvPublishBand = helper.findView(R.id.tv_publish_band);
                    TextView tvPublishData = helper.findView(R.id.tv_publish_date);
                    TextView tvPublishNumber = helper.findView(R.id.tv_publish_number);

                    if (tvPublishBand != null) {
                        tvPublishBand.setText(publishInformationBean.getBand());
                    }
                    if (tvPublishData != null) {
                        tvPublishData.setText(publishInformationBean.getDate());
                    }
                    if (tvPublishNumber != null) {
                        tvPublishNumber.setText(publishInformationBean.getNumber());
                    }
                }
                break;
        }
    }




}
