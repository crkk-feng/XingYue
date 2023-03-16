package com.example.xingyue.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lib_base.util.Constants;
import com.example.module_shop.data.shop.ShopDataDetailBean;
import com.example.xingyue.utils.SecurityUtil;

public class ShopItemDetailDao {
    private static final String TAG = "ShopItemDetailDao:";
    private final DataBaseHelper dataBaseHelper;

    public ShopItemDetailDao(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    //    (sum integer,imageId integer,shopItemText varchar,price varchar)
    public void insert(String shopItemId,String pwd){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        //这里放加密的逻辑;

//        String sql="insert into "+ Constants.DB_SHOP_ITEM_DETAIL_INFO_TABLE_NAME+ "(account,password) values("+acc+","+"'"+encrptPwd+"'"+")";

//        db.execSQL(sql);

        db.close();
    }
    public void delete(String s){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String sql="delete from "+Constants.DB_SHOP_ITEM_DETAIL_INFO_TABLE_NAME+ " where shopItemId = s";
        db.execSQL(sql);
        db.close();
    }

    public ShopDataDetailBean queryShopItemInfo(int shopInfoFindId) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
       //使用Google API
        Cursor query = db.query(Constants.DB_SHOP_ITEM_DETAIL_INFO_TABLE_NAME, null, null, null, null, null, null);
        while (query.moveToNext()){
            int queryResult = query.getInt(0);
            if (queryResult==shopInfoFindId) {
                //说明找到相关纪录信息
                String shopLargeUri=query.getString(1);
                String shopName=query.getString(2);
                String shopPrice=query.getString(3);
                String shopItemDescriptionUri=query.getString(4);
                String shopItemSellerName=query.getString(5);
                String shopItemSellerUri=query.getString(6);
                String shopItemSellerDescription=query.getString(7);
                String publisher=query.getString(8);
                String publishDate=query.getString(9);
                String publishId=query.getString(10);

                ShopDataDetailBean result = new ShopDataDetailBean(shopLargeUri, shopName, shopPrice,
                        shopItemDescriptionUri, shopItemSellerName, shopItemSellerUri,
                        shopItemSellerDescription, publisher, publishDate, publishId);
                return result;
            }
            Log.d(TAG,"该账户已存在");
        }
        //说明没有相关纪录
        return null;
    }

    public void insertShopOriginalData(){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
//        "(shopItemId varchar(255),largePicUri varchar (255),shopItemName varchar(255),shopItemPrice varchar(255),shopItemDescriptionUri varchar(255)," +
//                "shopItemSellerName varchar(255),shopItemSellerUri varchar(255),shopItemSellerDescription varchar(255)," +
//                "publisher varchar(255),publishDate varchar(255),publishId varchar(255))";
        String sql="insert into "+ Constants.DB_SHOP_ITEM_DETAIL_INFO_TABLE_NAME+
                "(largePicUri,shopItemName,shopItemPrice,shopItemDescriptionUri,shopItemSellerName," +
                "shopItemSellerUri,shopItemSellerDescription,publisher,publishDate,publishId) values(?,?,?,?,?,?,?,?,?,?)";//防止sql注入
        db.execSQL(sql,new Object[]{"https://img.alicdn.com/imgextra/i2/445406692/O1CN01p97yWD1zIzD1Tss47_!!0-item_pic.jpg_430x430q90.jpg"
                        ,"月亮与六便士-新版插画珍藏版"
                        ,"19.9"
                        ,"https://img.alicdn.com/imgextra/i3/1049653664/TB2ibVDxL1TBuNjy0FjXXajyXXa_!!1049653664.jpg"
                        ,"秋彤图书专营店"
                        ,"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2Fcb%2F9f%2Fbc%2Fcb9fbccaf2a6814994df8d5e72b82b1c.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1657808358&t=1da8e3d889c839ff24ccfbb449069728"
                        ,"阅读，看到更好的世界"
                        ,"商务出版社"
                        ,"2020年1月第3次"
                        ,"2020-1-1133-2"});
        db.execSQL(sql,new Object[]{"https://gd2.alicdn.com/imgextra/i4/2210296711425/O1CN01pmLnTb1MOh7KuCe7v_!!2210296711425.jpg_400x400.jpg"
                ,"喝月光的小女孩"
                ,"58"
                ,"https://gdp.alicdn.com/imgextra/i1/3927768234/O1CN01TDISyL2AhDkjYB1zh_!!3927768234.jpg"
                ,"博雅思书店"
                ,"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2Fc9%2F63%2F77%2Fc963777ebd9c8ebf5583b39565cfa1d7.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1657809502&t=1f1d8c83c18b6dbcd554fe3c75358a02"
                ,"带给孩子更好的成长"
                ,"宁夏出版社"
                ,"2021年2月第13次"
                ,"2021-2-32131-1"});
        db.close();
    }

}
