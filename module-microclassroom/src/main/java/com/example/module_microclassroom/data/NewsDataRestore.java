package com.example.module_microclassroom.data;


import com.example.module_microclassroom.bean.NewsItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 存储新闻数据的相关货舱
 *
 * 发生数据改变的前提
 * 1.时间不是当天
 * 2.当前货舱内数据为空
 */
public class NewsDataRestore {
    private static Date lastDate;
    private static volatile NewsDataRestore mNewsDataRestore;
    private List<NewsItem> mNewsList=new ArrayList<>();
    private NewsDataRestore(){};

    public static synchronized NewsDataRestore getInstance(){
        if (mNewsDataRestore==null){//1
            synchronized(NewsDataRestore.class){
                if(mNewsDataRestore==null) mNewsDataRestore= new NewsDataRestore();
            }
        }
        return mNewsDataRestore;
    }

    public static Date getLastDate() {
        return lastDate;
    }

    public static void setLastDate(Date lastDate) {
        NewsDataRestore.lastDate = lastDate;
    }

    public List<NewsItem> getmNewsList() {
        return mNewsList;
    }

    public void setmNewsList(List<NewsItem> mNewsList) {
        this.mNewsList = mNewsList;
    }
}
