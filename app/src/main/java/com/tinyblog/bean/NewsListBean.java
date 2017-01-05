package com.tinyblog.bean;

/**
 * @author xiarui
 * @date 2017/1/4 20:54
 * @desc 动态页列表 Bean
 * @remark 1.0
 */

public class NewsListBean {

    //文章配图
    private String imageUrl;
    //文章标题
    private String title;
    //文章摘要
    private String brief;
    //文章阅读数
    private String readNum;
    //文章喜欢数
    private String likeNum;
    //文章评论数
    private String commentNum;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getReadNum() {
        return readNum;
    }

    public void setReadNum(String readNum) {
        this.readNum = readNum;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }
}
