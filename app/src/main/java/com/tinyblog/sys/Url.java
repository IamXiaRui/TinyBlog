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

    /*====== DuoShuo Comment Url =====*/
    public static String SEND_COMMENT = "http://api.duoshuo.com/posts/create.json";


    //天气URL
    public static String NOW_WEATHER = "http://wthrcdn.etouch.cn/weather_mini?city=";

}
