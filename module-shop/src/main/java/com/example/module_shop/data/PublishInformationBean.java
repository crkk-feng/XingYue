package com.example.module_shop.data;

public class PublishInformationBean  extends MultipleBean{
    private String band;
    private String date;
    private String number;

    public PublishInformationBean(int itemType) {
        super(itemType);
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
