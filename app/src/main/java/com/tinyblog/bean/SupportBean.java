package com.tinyblog.bean;

import java.util.List;

/**
 * @author xiarui
 * @date 2017/3/6 22:54
 * @description
 * @remark
 */

public class SupportBean {

    private List<LibsBean> libs;

    public List<LibsBean> getLibs() {
        return libs;
    }

    public void setLibs(List<LibsBean> libs) {
        this.libs = libs;
    }

    public static class LibsBean {


        private String name;
        private String author;
        private String url;
        private String desc;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
