package com.example.lib_base.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lib_base.util.Constants;
import com.example.lib_base.util.SecurityUtil;

public class UserDao {
    private static final String TAG = "Dao:";
    private final DataBaseHelper dataBaseHelper;
    private SecurityUtil securityUtil;//加密类


    public UserDao(Context context){
        dataBaseHelper = new DataBaseHelper(context);//创建数据库
    }

    //    (sum integer,imageId integer,shopItemText varchar,price varchar)
    public void insert(String acc,String pwd){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        //这里放加密的逻辑;
        String resultString="aaa";
        securityUtil = new SecurityUtil();
        String encrptPwd = securityUtil.encryptAES(pwd);
        int length = encrptPwd.length();
        Log.d(TAG,length+"");
        String sql="insert into "+ Constants.DB_USER_TABLE_NAME+ "(account,password) values("+acc+","+"'"+encrptPwd+"'"+")";

        db.execSQL(sql);
        Log.d(TAG,"插入的pwd是"+encrptPwd);

        db.close();
    }
    public void delete(String s){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String sql="delete from "+Constants.DB_USER_TABLE_NAME+ " where account = s";
        db.execSQL(sql);
        db.close();
    }
    public void update(String acc,String pwdResult){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String sql="update "+Constants.DB_USER_TABLE_NAME+ " set account" +
                " ="+acc+" where password ="+pwdResult;
        db.execSQL(sql);
        db.close();
    }
    public int query(String acc){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        //使用Google API
        Cursor query = db.query(Constants.DB_USER_TABLE_NAME, null, null, null, null, null, null);
        while (query.moveToNext()){
            String queryResult = query.getString(0);
            if (queryResult.equals(acc)) {
                return 1;
            }
            Log.d(TAG,"该账户已存在");
        }
        return 0;
    }

    public int check(String acc,String pwd){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        Cursor query = db.query(Constants.DB_USER_TABLE_NAME, null, null, null, null, null, null);
        //这里对密码进行加密逻辑处理，先不更改
        while (query.moveToNext()){
            String queryAccount = query.getString(0);
            if (queryAccount.equals(acc)) {
                String queryPassword=query.getString(1);
                String encryptAESResult = securityUtil.encryptAES(pwd);
                String ebotongResult = securityUtil.decryptAES(queryPassword);
                if (ebotongResult==null){
                    return 1;
                }
            }
            Log.d(TAG,"该账户已存在");
        }
        return 0;
    }
}
