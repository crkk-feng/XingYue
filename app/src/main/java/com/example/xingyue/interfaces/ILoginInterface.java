package com.example.xingyue.interfaces;

public interface ILoginInterface {
    /**
     * 传输账号密码到服务端
     */
    void onPostAccAndPass(String account,String password);

    /**
     * 接收服务端返回的报文
     */
    void onReceiveEncryptionString(String result);
}
