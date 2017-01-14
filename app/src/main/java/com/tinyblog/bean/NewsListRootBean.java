package com.tinyblog.bean;

import java.util.List;

/**
 * @author xiarui
 * @date 2017/1/5 14:04
 * @desc 最近动态的 Bean 对象
 * @remark 1.0
 */

public class NewsListRootBean {

    private String status;              //状态
    private List<PostsBean> posts;  //最近文章集合

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PostsBean> getPosts() {
        return posts;
    }

    public void setPosts(List<PostsBean> posts) {
        this.posts = posts;
    }

    /**
     * 文章 Bean 对象
     */
    public static class PostsBean {

        //文章 Id
        private int id;
        //文章 Url
        private String url;
        //文章标题
        private String title;
        //文章内容
        private String content;
        //文章简介
        private String excerpt;
        //创建文章时间
        private String date;
        //评论数
        private int comment_count;
        //自定义字段：阅读数、喜欢数等
        private CustomFieldsBean custom_fields;
        //文章分类
        private List<PostCategoriesBean> categories;
        //文章缩略图
        private List<AttachmentsBean> attachments;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

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

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public CustomFieldsBean getCustom_fields() {
            return custom_fields;
        }

        public void setCustom_fields(CustomFieldsBean custom_fields) {
            this.custom_fields = custom_fields;
        }


        public List<PostCategoriesBean> getCategories() {
            return categories;
        }

        public void setCategories(List<PostCategoriesBean> categories) {
            this.categories = categories;
        }

        public List<AttachmentsBean> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<AttachmentsBean> attachments) {
            this.attachments = attachments;
        }

        /**
         * 自定义字段 Bean
         */
        public static class CustomFieldsBean {
            //查看数
            private List<String> views;
            //喜欢数 可能无此字段
            private List<String> kratos_love;
            //点赞数 可能无字段
            private List<String> specs_zan;

            public List<String> getViews() {
                return views;
            }

            public void setViews(List<String> views) {
                this.views = views;
            }

            public List<String> getKratos_love() {
                return kratos_love;
            }

            public void setKratos_love(List<String> kratos_love) {
                this.kratos_love = kratos_love;
            }

            public List<String> getSpecs_zan() {
                return specs_zan;
            }

            public void setSpecs_zan(List<String> specs_zan) {
                this.specs_zan = specs_zan;
            }
        }

        /**
         * 缩略图 Bean
         */
        public static class AttachmentsBean {
            //缩略图 Url
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        /**
         * 文章分类 Bean
         */
        public static class PostCategoriesBean {

            //分类 Id
            private int id;
            //分类标题
            private String title;

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
        }
    }
}
