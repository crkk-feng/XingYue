package com.example.module_microclassroom.bean;

import android.net.Uri;

/**
 * 轮播图的数据项
 */
public class BannerImageBean {
    private Uri imageRes;

    public BannerImageBean(Uri imageRes) {
        this.imageRes = imageRes;
    }

    public Uri getImageRes() {
        return imageRes;
    }

    public void setImageRes(Uri imageRes) {
        this.imageRes = imageRes;
    }
}
