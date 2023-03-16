package com.example.module_microclassroom.util;

import android.net.Uri;
import android.util.Log;

import com.example.module_microclassroom.bean.NewsItem;
import com.example.module_microclassroom.data.NewsDataRestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Json的操作类
 */
public class JsonParseUtil {


    /**
     * 用 JSONObject 解析
     * @param newsItemJsonArray 需要解析的数据
     */
    public static void parseJsonDataToNewsItem(JSONArray newsItemJsonArray) {
        try {
            ArrayList<NewsItem> newsItems = new ArrayList<>();
            // 把需要解析的数据传入到 JSONArray 对象中
            for (int i = 0;i < newsItemJsonArray.length();i++){
                JSONObject jsonObject = newsItemJsonArray.getJSONObject(i);
                Log.d("T",jsonObject.toString());

                boolean havePic=jsonObject.getBoolean("havePic");
                String title=jsonObject.getString("title");
                String resource=jsonObject.getString("source");
                String dateString=jsonObject.getString("pubDate");
                String linkUriString=jsonObject.getString("link");
                Uri linkUri=Uri.parse(linkUriString);//跳转链接
                Uri imgUri=null;
                if (havePic) {
                    String imgUriString=jsonObject.getString("img");
                    //跳转链接
                    imgUri = Uri.parse(imgUriString);
                }


                NewsItem newsItem = new NewsItem();
                newsItem.setTitle(title);
                newsItem.setHavePic(havePic);
                newsItem.setDate(dateString);
                newsItem.setResource(resource);
                newsItem.setImageUri(imgUri);
                newsItem.setWebUri(linkUri);

                /*
                取得结果传入List
                 */
                newsItems.add(newsItem);
            }
            NewsDataRestore.getInstance().setmNewsList(newsItems);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 用 JSONObject 解析
     *
     */
    public static void parseJsonDataToHotItem(JSONArray hotRankJsonArray) {
        try {
            ArrayList<NewsItem> newsItems = new ArrayList<>();
            // 把需要解析的数据传入到 JSONArray 对象中

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
