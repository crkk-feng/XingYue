package com.example.xingyue.data;

public class ShopCartDataBean {
    private int sum;
    private int imageId;
    private String shopItemText;
    private String price;

    public ShopCartDataBean(int sum, int imageId, String shopItemText, String price) {
        this.sum = sum;
        this.imageId = imageId;
        this.shopItemText = shopItemText;
        this.price = price;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getShopItemText() {
        return shopItemText;
    }

    public void setShopItemText(String shopItemText) {
        this.shopItemText = shopItemText;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
