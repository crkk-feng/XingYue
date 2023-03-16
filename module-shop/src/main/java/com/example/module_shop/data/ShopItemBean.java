package com.example.module_shop.data;

import android.net.Uri;

public class ShopItemBean {
    private Uri shopImageUri;
    private String shopItemName;
    private String shopItemPrice;
    private String shopItemCustomNum;

    public ShopItemBean(Uri shopImageUri, String shopItemName, String shopItemPrice, String shopItemCustomNum) {
        this.shopImageUri = shopImageUri;
        this.shopItemName = shopItemName;
        this.shopItemPrice = shopItemPrice;
        this.shopItemCustomNum = shopItemCustomNum;
    }

    public ShopItemBean() {
    }

    public ShopItemBean(Uri shopImageUri) {
        this(shopImageUri,"测试商品","测试价格","测试人数");//统一入口
    }

    public Uri getShopImageUri() {
        return shopImageUri;
    }

    public void setShopImageUri(Uri shopImageUri) {
        this.shopImageUri = shopImageUri;
    }

    public String getShopItemName() {
        return shopItemName;
    }

    public void setShopItemName(String shopItemName) {
        this.shopItemName = shopItemName;
    }

    public String getShopItemPrice() {
        return shopItemPrice;
    }

    public void setShopItemPrice(String shopItemPrice) {
        this.shopItemPrice = shopItemPrice;
    }

    public String getShopItemCustomNum() {
        return shopItemCustomNum;
    }

    public void setShopItemCustomNum(String shopItemCustomNum) {
        this.shopItemCustomNum = shopItemCustomNum;
    }
}
