package com.tinyblog.bean;

/**
 * @author xiarui
 * @date 2017/3/9 18:09
 * @description 草稿数据 Bean
 * @remark
 */

public class DraftBean {
    private String title;
    private String content;
    private String time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
