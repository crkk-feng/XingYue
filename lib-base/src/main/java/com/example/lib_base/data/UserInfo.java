package com.example.lib_base.data;

public class UserInfo {
    public String userAccount;//登录账号
    private String userPassword;//登录密码
    public String userHeadUri=null;
    private String token;//登录拿到的token码
    private String userCredit;//用户积分


    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserHeadUri() {
        return userHeadUri;
    }

    public void setUserHeadUri(String userHeadUri) {
        this.userHeadUri = userHeadUri;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(String userCredit) {
        this.userCredit = userCredit;
    }
}
