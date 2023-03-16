package com.example.module_shop.data;

import android.net.Uri;

public class LargePicBean extends MultipleBean{
    private Uri largePicUri;
    public LargePicBean(int itemType) {
        super(itemType);
    }

    public Uri getLargePicUri() {
        return largePicUri;
    }

    public void setLargePicUri(Uri largePicUri) {
        this.largePicUri = largePicUri;
    }
}
