package com.example.module_shop.data;

public class ShopItemPostBean {
    private Integer shopItemId;
    private String shopItemName;
    private String shopItemPrice;
    private String shopItemUri;
    private String shopItemSellerName;
    private String shopItemSellerUri;

    public int getShopItemId() {
        return shopItemId;
    }

    public void setShopItemId(int shopItemId) {
        this.shopItemId = shopItemId;
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

    public String getShopItemUri() {
        return shopItemUri;
    }

    public void setShopItemUri(String shopItemUri) {
        this.shopItemUri = shopItemUri;
    }

    public String getShopItemSellerName() {
        return shopItemSellerName;
    }

    public void setShopItemSellerName(String shopItemSellerName) {
        this.shopItemSellerName = shopItemSellerName;
    }

    public String getShopItemSellerUri() {
        return shopItemSellerUri;
    }

    public void setShopItemSellerUri(String shopItemSellerUri) {
        this.shopItemSellerUri = shopItemSellerUri;
    }

    public ShopItemPostBean(int shopItemId, String shopItemName, String shopItemPrice, String shopItemUri, String shopItemSellerName, String shopItemSellerUri) {
        this.shopItemId = shopItemId;
        this.shopItemName = shopItemName;
        this.shopItemPrice = shopItemPrice;
        this.shopItemUri = shopItemUri;
        this.shopItemSellerName = shopItemSellerName;
        this.shopItemSellerUri = shopItemSellerUri;
    }
}
