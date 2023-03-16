package com.example.xingyue;

import android.net.Uri;

import com.example.xingyue.base.BaseApplication;
import com.example.xingyue.dao.CommentDao;
import com.example.xingyue.data.CommentDataBean;

import org.junit.Test;

public class CommentInsertTest {
    @Test
    public void insertDataTest(){
        BaseApplication baseApplication = new BaseApplication();
        //数据层
        CommentDataBean commentDataBeanTest = new CommentDataBean("想再见一面", Uri.parse("android.resource://"+baseApplication.getBaseContext().getPackageName()+"/"+R.mipmap.touxiang),
                23,
                "2021年12月30号",
                "这场讲座真是太赞了，令我印象深刻");
        //Dao层
        CommentDao commentDao = new CommentDao(baseApplication.getBaseContext());
        commentDao.insert(commentDataBeanTest);
    }
}
