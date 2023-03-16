package com.example.xingyue.data;

public class HorizontalDataBean {
    private String videoText;
    private int imageId;

    public HorizontalDataBean(String videoText, int imageId) {
        this.videoText = videoText;
        this.imageId = imageId;
    }

    public String getVideoText() {
        return videoText;
    }

    public void setVideoText(String videoText) {
        this.videoText = videoText;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
