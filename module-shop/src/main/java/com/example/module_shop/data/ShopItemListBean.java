package com.example.module_shop.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品控件的列表封装，内部为商品数据的列表集合
 */
public class ShopItemListBean extends MultipleBean{

    public ShopItemListBean(int itemType) {
        super(itemType);
    }
    private List<ShopItemBean> shopItemBeanList=new ArrayList<>();

    public List<ShopItemBean> getShopItemBeanList() {
        return shopItemBeanList;
    }

    public void setShopItemBeanList(List<ShopItemBean> shopItemBeanList) {
        this.shopItemBeanList = shopItemBeanList;
    }
}
