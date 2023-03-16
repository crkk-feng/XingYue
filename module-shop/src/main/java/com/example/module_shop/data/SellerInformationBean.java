package com.example.module_shop.data;

import android.net.Uri;

public class SellerInformationBean extends MultipleBean{
    private Uri sellerUri;
    private String sellerName;
    private String sellerIntro;
    public SellerInformationBean(int itemType) {
        super(itemType);
    }

    public Uri getSellerUri() {
        return sellerUri;
    }

    public void setSellerUri(Uri sellerUri) {
        this.sellerUri = sellerUri;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerIntro() {
        return sellerIntro;
    }

    public void setSellerIntro(String sellerIntro) {
        this.sellerIntro = sellerIntro;
    }
}
