package com.tinyblog.sys;

/**
 * @author xiarui
 * @date 2017/1/5 12:57
 * @desc 所有请求的 Url
 * @remark 1.0
 */

public class Url {
    /*====== WordPress Url =====*/
    public static String GET_RECENT_POSTS = "http://www.iamxiarui.com/xr-api/get_recent_posts/";
    public static String GET_POST_DETAILS  = "http://www.iamxiarui.com/xr-api/get_post/?id=";
    public static String GET_CATEGORY_INDEX = "http://www.iamxiarui.com/xr-api/get_category_index/";
    public static String GET_TAG_INDEX = "http://www.iamxiarui.com/xr-api/get_tag_index/";
    public static String GET_CATEGORY_POSTS = "http://www.iamxiarui.com/xr-api/get_category_posts/";
    public static String GET_TAG_POSTS = "http://www.iamxiarui.com/xr-api/get_tag_posts/";
    public static String GET_POST_COMMENT = "http://www.iamxiarui.com/xr-api/get_post/?id=";
    public static String CREATE_POST_ISSUE = "http://www.iamxiarui.com/xr-api/posts/create_post/?nonce=e4b749f30e&status=publish";
    public static String CREATE_POST_SAVE = "http://www.iamxiarui.com/xr-api/posts/create_post/?nonce=e4b749f30e&status=draft";

    /*====== DuoShuo Comment Url =====*/
    public static String SEND_COMMENT = "http://api.duoshuo.com/posts/create.json";


    //天气URL
    public static String NOW_WEATHER = "http://wthrcdn.etouch.cn/weather_mini?city=";
    //知乎Url
    public static String ZHIHU_RECENT = "http://news-at.zhihu.com/api/4/news/latest";
    //知乎详情 Url
    public static String ZHIHU_POST_DETAIL = "http://news-at.zhihu.com/api/4/news/";
    //Github Trending Url
    public static String GITHUB_TRENDING = "http://anly.leanapp.cn/api/github/trending/java?since=daily";
}
