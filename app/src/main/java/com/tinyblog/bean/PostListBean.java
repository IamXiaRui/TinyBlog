package com.tinyblog.bean;

import java.util.List;

/**
 * @author xiarui
 * @date 2017/2/23 18:49
 * @description
 * @remark
 */

public class PostListBean {

    private String status;
    private int count;
    private List<PostsBean> posts;

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

    public List<PostsBean> getPosts() {
        return posts;
    }

    public void setPosts(List<PostsBean> posts) {
        this.posts = posts;
    }

    public static class PostsBean {

        private int id;
        private String title;
        private String excerpt;
        private String date;
        private CustomFieldsBean custom_fields;
        private List<AttachmentsBean> attachments;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getExcerpt() {
            return excerpt;
        }

        public void setExcerpt(String excerpt) {
            this.excerpt = excerpt;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public CustomFieldsBean getCustom_fields() {
            return custom_fields;
        }

        public void setCustom_fields(CustomFieldsBean custom_fields) {
            this.custom_fields = custom_fields;
        }

        public List<AttachmentsBean> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<AttachmentsBean> attachments) {
            this.attachments = attachments;
        }

        public static class CustomFieldsBean {
            private List<String> views;

            public List<String> getViews() {
                return views;
            }

            public void setViews(List<String> views) {
                this.views = views;
            }
        }

        public static class AttachmentsBean {

            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
