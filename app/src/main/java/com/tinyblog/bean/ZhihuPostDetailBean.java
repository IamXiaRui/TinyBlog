package com.tinyblog.bean;

/**
 * @author xiarui
 * @date 2017/3/7 16:00
 * @description 知乎文章详情 Bean 对象
 * @remark
 */

public class ZhihuPostDetailBean {

    private String body;
    private String title;
    private String image;
    private String share_url;
    private int id;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
