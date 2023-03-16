package com.example.lib_base.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.lib_base.util.Constants;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";

    /**
     *
     * @param context 上下文
     * @param name 数据库名称
     * @param factory 游标工厂,null表示默认
     * @param version 版本号
     */
    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 修改后的构造方法，更加简洁
     * @param context
     */
    public DataBaseHelper(@Nullable Context context) {
        super(context, Constants.DB_XINGYUE_HANGXING_NAME, null, Constants.DB_VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建时的回调,只有当数据库不存在时，才会被调用，当数据库已经存在时，再不会被调用
        Log.d(TAG,"数据库创建了");
        //第一次实例化数据库时候，创建表里面的字段
        String sql1="create table "+Constants.DB_USER_TABLE_NAME+"(account varchar(255),password varchar(255));";
        String sql2="create table "+Constants.DB_COMMENT_TABLE_NAME+"(userName varchar(255),uriRoute varchar(255)," +
                "numOfThumbsUp int,dateTime varchar(255),contentOfComment varchar(255));";
//        String sql3="create table "+Constants.DB_SHOP_ITEM_TABLE_NAME+"(sum integer,imageId integer,shopItemText varchar,price varchar)";
        String sql4="create table "+Constants.DB_SHOP_ITEM_DETAIL_INFO_TABLE_NAME+
                "(shopItemId integer PRIMARY KEY AUTOINCREMENT,largePicUri varchar (255),shopItemName varchar(255),shopItemPrice varchar(255),shopItemDescriptionUri varchar(255)," +
                "shopItemSellerName varchar(255),shopItemSellerUri varchar(255),shopItemSellerDescription varchar(255)," +
                "publisher varchar(255),publishDate varchar(255),publishId varchar(255));";
        //TODO
        db.execSQL(sql1);
        db.execSQL(sql2);
//        db.execSQL(sql3);
        db.execSQL(sql4);
    }

    /**
     * 升级数据库，只有当版本号发生改变时，才会执行该方法
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //升级数据库时的回调
        Log.d(TAG,"数据库升级了");
        Log.d(TAG,"旧版本是"+oldVersion);
        Log.d(TAG,"新版本是"+newVersion);

        //这里放数据库升级进行的操作
    }

}
