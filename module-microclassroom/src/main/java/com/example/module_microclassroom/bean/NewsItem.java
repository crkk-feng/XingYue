package com.example.module_microclassroom.bean;

import android.net.Uri;

public class NewsItem {
    public boolean havePic;//是否含有图片
    public String title;//标题
    public String resource;//新闻来源
    public String date;//发表日期
    public Uri imageUri;//配图链接，用于显示配图
    public Uri webUri;//跳转到的网页Uri

    public boolean isHavePic() {
        return havePic;
    }

    public void setHavePic(boolean havePic) {
        this.havePic = havePic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getWebUri() {
        return webUri;
    }

    public void setWebUri(Uri webUri) {
        this.webUri = webUri;
    }
}
