package com.tinyblog.sys;

/**
 * @author xiarui
 * @date 2017/1/5 12:57
 * @desc 所有请求的 Url
 * @remark 1.0
 */

public class Url {
    /*====== WordPress Url =====*/
    //获取最近文章列表
    public static String GET_RECENT_POSTS = "http://www.iamxiarui.com/xr-api/get_recent_posts/";
    //获取文章详情 需要文章 ID
    public static String GET_POST_DETAILS  = "http://www.iamxiarui.com/xr-api/get_post/?id=";
    //得到全部分类列表
    public static String GET_CATEGORY_INDEX = "http://www.iamxiarui.com/xr-api/get_category_index/";
    //得到全部标签列表
    public static String GET_TAG_INDEX = "http://www.iamxiarui.com/xr-api/get_tag_index/";
    //获取分类下的所有文章 需要分类 ID
    public static String GET_CATEGORY_POSTS = "http://www.iamxiarui.com/xr-api/get_category_posts/";
    //获取标签下的所有文章 需要标签 ID
    public static String GET_TAG_POSTS = "http://www.iamxiarui.com/xr-api/get_tag_posts/";
    //获取文章评论 需要文章 ID
    public static String GET_POST_COMMENT = "http://www.iamxiarui.com/xr-api/get_post/?id=";
    //发表文章 需要设备序列 文章状态 标题 内容等参数
    public static String CREATE_POST_ISSUE = "http://www.iamxiarui.com/xr-api/posts/create_post/";

    /*====== 其他 Url =====*/
    //多说发表评论
    public static String SEND_COMMENT = "http://api.duoshuo.com/posts/create.json";
    //获取实时天气
    public static String NOW_WEATHER = "http://api.k780.com:88/?app=weather.today&weaid=";
    //实时天气 Key
    public static String NOW_WEATHER_KEY = "&appkey=24558&sign=b3c35c3f390287fe5dc70e8d008ef1b5&format=json";
    //获取知乎日报每日内容
    public static String ZHIHU_RECENT = "http://news-at.zhihu.com/api/4/news/latest";
    //获取日报文章详情 需要文章 ID
    public static String ZHIHU_POST_DETAIL = "http://news-at.zhihu.com/api/4/news/";
    //获取 Github Trending
    public static String GITHUB_TRENDING = "http://anly.leanapp.cn/api/github/trending/";
}
