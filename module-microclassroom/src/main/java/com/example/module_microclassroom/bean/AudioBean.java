package com.example.module_microclassroom.bean;

import android.net.Uri;

public class AudioBean {
    private String artistName;
    private Uri artistImageId;

    public AudioBean(String artistName, Uri artistImageId) {
        this.artistName = artistName;
        this.artistImageId = artistImageId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Uri getArtistImageId() {
        return artistImageId;
    }

    public void setArtistImageId(Uri artistImageId) {
        this.artistImageId = artistImageId;
    }
}
