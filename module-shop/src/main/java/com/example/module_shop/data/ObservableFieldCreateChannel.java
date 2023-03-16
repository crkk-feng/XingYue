package com.example.module_shop.data;

import android.net.Uri;

import androidx.databinding.ObservableField;

public class ObservableFieldCreateChannel {
    public ObservableField<String> channelName;
    public ObservableField<String> channelDescription;
    public ObservableField<Uri> channelImageUri;

    public ObservableFieldCreateChannel(ObservableField<String> channelName, ObservableField<String> channelDescription, ObservableField<Uri> channelImageUri) {
        this.channelName = channelName;
        this.channelDescription = channelDescription;
        this.channelImageUri = channelImageUri;
    }

    public ObservableField<String> getChannelName() {
        return channelName;
    }

    public void setChannelName(ObservableField<String> channelName) {
        this.channelName = channelName;
    }

    public ObservableField<String> getChannelDescription() {
        return channelDescription;
    }

    public void setChannelDescription(ObservableField<String> channelDescription) {
        this.channelDescription = channelDescription;
    }

    public ObservableField<Uri> getChannelImageUri() {
        return channelImageUri;
    }

    public void setChannelImageUri(ObservableField<Uri> channelImageUri) {
        this.channelImageUri = channelImageUri;
    }
}
