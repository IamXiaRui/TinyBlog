package com.tinyblog.bean;

import java.util.List;

/**
 * @author xiarui
 * @date 2017/3/9 16:50
 * @description
 * @remark
 */

public class AdminBean {

    private String status;
    private int count;
    private List<AuthorsBean> authors;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<AuthorsBean> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorsBean> authors) {
        this.authors = authors;
    }

    public static class AuthorsBean {
        /**
         * id : 1
         * slug : admin
         * name : iamxiarui
         * first_name : 睿
         * last_name : 夏
         * nickname : iamxiarui
         * url : http://www.iamxiarui.com
         * description : 慢慢爬坑的IT少年
         */

        private int id;
        private String slug;
        private String name;
        private String first_name;
        private String last_name;
        private String nickname;
        private String url;
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
