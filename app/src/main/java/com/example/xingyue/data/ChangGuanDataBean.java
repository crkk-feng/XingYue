package com.example.xingyue.data;

public class ChangGuanDataBean {
    private int imageId;
    private String changGuanName;
    private String star;
    private String price;
    private String address;
    private String phoneNumber;
    private String time;

    public ChangGuanDataBean(int imageId, String changGuanName, String star, String price, String address, String phoneNumber, String time) {
        this.imageId = imageId;
        this.changGuanName = changGuanName;
        this.star = star;
        this.price = price;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.time = time;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getChangGuanName() {
        return changGuanName;
    }

    public void setChangGuanName(String changGuanName) {
        this.changGuanName = changGuanName;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
