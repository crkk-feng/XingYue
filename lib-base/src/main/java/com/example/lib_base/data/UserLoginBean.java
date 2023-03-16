package com.example.lib_base.data;

public class UserLoginBean {
    public String Account;
    public String Password;

    public UserLoginBean(String account, String password) {
        Account = account;
        Password = password;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
