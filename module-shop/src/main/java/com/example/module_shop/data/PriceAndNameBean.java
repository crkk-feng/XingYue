package com.example.module_shop.data;

public class PriceAndNameBean extends MultipleBean{
    private String itemPrice;
    private String itemName;
    public PriceAndNameBean(int itemType) {
        super(itemType);
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
