package com.example.module_shop.data;

public class ShopNavigationButtonBean {
    private int topImageId;
    private String bottomText;

    public ShopNavigationButtonBean(int topImageId, String bottomText) {
        this.topImageId = topImageId;
        this.bottomText = bottomText;
    }

    public int getTopImageId() {
        return topImageId;
    }

    public void setTopImageId(int topImageId) {
        this.topImageId = topImageId;
    }

    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }
}
