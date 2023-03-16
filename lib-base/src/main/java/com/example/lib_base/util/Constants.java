package com.example.lib_base.util;

public class Constants {
    /**
     * 获取推荐列表的专辑数量
     */
    public static int RECOMMAND_COUNT=80;

    //默认列表请求数量
    public static int COUNT_DEFAULT = 50;

    //热词的数量
    public static int COUNT_HOT_WORD = 10;

    //数据库相关的常量
    public static final String DB_NAME = "ximala.db";
    //数据库的版本
    public static final int DB_VERSION_CODE = 1;
    //订阅的表名
    public static final String SUB_TB_NAME = "subTb";
    public static final String SUB_ID = "_id";
    public static final String SUB_COVER_URL = "coverUrl";
    public static final String SUB_TITLE = "title";
    public static final String SUB_DESCRIPTION = "description";
    public static final String SUB_TRACKS_COUNT = "tracksCount";
    public static final String SUB_PLAY_COUNT = "playCount";
    public static final String SUB_AUTHOR_NAME = "authorName";
    public static final String SUB_ALBUM_ID = "albumId";
    //订阅最多个数
    public static final int MAX_SUB_COUNT = 100;
    //历史记录的表名
    public static final String HISTORY_TB_NAME = "tb_history";
    public static final String HISTORY_ID = "_id";
    public static final String HISTORY_TRACK_ID = "historyTrackId";
    public static final String HISTORY_TITLE = "historyTitle";
    public static final String HISTORY_PLAY_COUNT = "historyPlayCount";
    public static final String HISTORY_DURATION = "historyDuration";
    public static final String HISTORY_UPDATE_TIME = "historyUpdateTime";
    public static final String HISTORY_COVER = "historyCover";
    public static final String HISTORY_AUTHOR = "history_author";
    //最大的历史记录数
    public static final int MAX_HISTORY_COUNT = 100;

    //需要使用的接口地址
    public static String APIHEAD="";
    public static  String LOGINAPI="";


    //评论数据库相关的常量
    public static final String DB_XINGYUE_HANGXING_NAME = "xingyue_hangxing.db";
    //数据库的版本
    public static final int DB_COMMENT_VERSION_CODE = 1;
    //与购物车有关的表名
//    public static final String DB_SHOP_ITEM_TABLE_NAME = "usercomment";
    //评论表名
    public static final String DB_USER_TABLE_NAME = "user";
    public static final String DB_COMMENT_TABLE_NAME = "usercomment";
    //与商品详情有关的表
    public static final String DB_SHOP_ITEM_DETAIL_INFO_TABLE_NAME = "shopitemdetailinfo";

}
