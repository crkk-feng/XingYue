package com.example.xingyue.data;

import java.net.URL;

public class ShopDataBean {

    private int ImageId;
    private int shopItemBeanId;
    private String goodsName;
    private String price;

    public ShopDataBean(int imageId, int shopItemBeanId, String goodsName, String price) {
        ImageId = imageId;
        this.shopItemBeanId = shopItemBeanId;
        this.goodsName = goodsName;
        this.price = price;
    }

    public ShopDataBean(int imageId, String goodsName, String price) {
        ImageId = imageId;
        this.goodsName = goodsName;
        this.price = price;
    }

    public void setShopItemBeanId(int shopItemBeanId) {
        this.shopItemBeanId = shopItemBeanId;
    }

    public int getShopItemBeanId() {
        return shopItemBeanId;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
