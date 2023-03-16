package com.example.module_shop.data.shop;

public class ShopDataDetailBean {
    private String shopLargeUri;
    private String shopItemName;
    private String shopPrice;
    private String shopItemDescriptionUri;
    private String shopItemSellerName;
    private String shopItemSellerUri;
    private String shopItemSellerDescription;
    private String publisher;
    private String publishDate;
    private String publishId;

    public ShopDataDetailBean(String shopLargeUri, String shopName, String shopPrice, String shopItemDescriptionUri, String shopItemSellerName, String shopItemSellerUri, String shopItemSellerDescription, String publisher, String publishDate, String publishId) {
        this.shopLargeUri = shopLargeUri;
        this.shopItemName = shopName;
        this.shopPrice = shopPrice;
        this.shopItemDescriptionUri = shopItemDescriptionUri;
        this.shopItemSellerName = shopItemSellerName;
        this.shopItemSellerUri = shopItemSellerUri;
        this.shopItemSellerDescription = shopItemSellerDescription;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.publishId = publishId;
    }

    public ShopDataDetailBean() {
    }

    public String getShopLargeUri() {
        return shopLargeUri;
    }

    public void setShopLargeUri(String shopLargeUri) {
        this.shopLargeUri = shopLargeUri;
    }

    public String getShopItemName() {
        return shopItemName;
    }

    public void setShopName(String shopName) {
        this.shopItemName = shopName;
    }

    public String getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getShopItemDescriptionUri() {
        return shopItemDescriptionUri;
    }

    public void setShopItemDescriptionUri(String shopItemDescriptionUri) {
        this.shopItemDescriptionUri = shopItemDescriptionUri;
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

    public String getShopItemSellerDescription() {
        return shopItemSellerDescription;
    }

    public void setShopItemSellerDescription(String shopItemSellerDescription) {
        this.shopItemSellerDescription = shopItemSellerDescription;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }
}
