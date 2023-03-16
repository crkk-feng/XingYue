package com.example.xingyue.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.lib_base.util.Constants;
import com.example.xingyue.data.CommentDataBean;

import java.util.ArrayList;
import java.util.List;

public class CommentDao {
    private static final String TAG = "Dao:";
    private final DataBaseHelper dataBaseHelper;


    public CommentDao(Context context){
        dataBaseHelper = new DataBaseHelper(context);//创建数据库
    }

    public void insert(CommentDataBean commentDataBean){
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        //插入评论的具体内容;
        String userName=commentDataBean.getUserName();
        Uri headSculptureUri = commentDataBean.getHeadSculptureUri();
        Log.d(TAG,headSculptureUri.toString());
        String uriRoute=commentDataBean.getHeadSculptureUri().toString();
        int sumOfThumbsUp=commentDataBean.getSumOfThumbsUp();
        String dateTime=commentDataBean.getDataTime().toString();
        String contentOfComment=commentDataBean.getContentOfComment();
        String sql="insert into "+ Constants.DB_COMMENT_TABLE_NAME+ "(userName,uriRoute,numOfThumbsUp,dateTime,contentOfComment) " +
                "values(?,?,?,?,?)";
        db.execSQL(sql,new Object[]{userName,uriRoute,sumOfThumbsUp,dateTime,contentOfComment});
        Log.d(TAG,"插入的userName是"+userName);
        db.close();
    }

    /**
     * 取出全部的评论列表并返回，从而进行数据展示
     */
    public List<CommentDataBean> queryAll(){
        List<CommentDataBean> commentDataBeanList=new ArrayList<>();
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        //使用Google API
        Cursor query = db.query(Constants.DB_COMMENT_TABLE_NAME, null, null, null, null, null, null);
        while (query.moveToNext()){
            String userName=query.getString(0);
            Uri uriRoute=Uri.parse(query.getString(1));
            int sumOfThumbsUp=query.getInt(2);
            String dateTime=query.getString(3);
            String contentOfComment=query.getString(4);
            CommentDataBean dataResult = new CommentDataBean(userName,
                    uriRoute,
                    sumOfThumbsUp,
                    dateTime,
                    contentOfComment);
            if (commentDataBeanList != null) {
                commentDataBeanList.add(dataResult);
            }
        }
        return commentDataBeanList;
    }
}
