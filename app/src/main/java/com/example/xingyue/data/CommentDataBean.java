package com.example.xingyue.data;

import android.net.Uri;

public class CommentDataBean {
    private String userName;
    private Uri headSculptureUri;
    private int sumOfThumbsUp;
    private String dataTime;
    private String contentOfComment;

    public CommentDataBean(String userName,
                           Uri headSculptureUri,
                           int sumOfThumbsUp,
                           String dataTime,
                           String contentOfComment) {
        this.userName = userName;
        this.headSculptureUri = headSculptureUri;
        this.sumOfThumbsUp = sumOfThumbsUp;
        this.dataTime = dataTime;
        this.contentOfComment = contentOfComment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Uri getHeadSculptureUri() {
        return headSculptureUri;
    }

    public void setHeadSculptureUri(Uri headSculptureUri) {
        this.headSculptureUri = headSculptureUri;
    }

    public int getSumOfThumbsUp() {
        return sumOfThumbsUp;
    }

    public void setSumOfThumbsUp(int sumOfThumbsUp) {
        this.sumOfThumbsUp = sumOfThumbsUp;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getContentOfComment() {
        return contentOfComment;
    }

    public void setContentOfComment(String contentOfComment) {
        this.contentOfComment = contentOfComment;
    }
}
