package com.tinyblog.bean;

/**
 * @author xiarui
 * @date 2017/3/7 12:52
 * @description 知乎文章 bean 对象
 * @remark
 */

public class ZhihuPostBean {
    private String imageUrl;
    private int postId;
    private String title;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
