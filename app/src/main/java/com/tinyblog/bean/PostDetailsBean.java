package com.tinyblog.bean;

import java.util.List;

/**
 * @author xiarui
 * @date 2017/2/21 12:34
 * @desc 文章详情的 Bean 对象
 * @remark 1.0
 */

public class PostDetailsBean {

    private String status;
    private PostBean post;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PostBean getPost() {
        return post;
    }

    public void setPost(PostBean post) {
        this.post = post;
    }

    /**
     * 文章具体内容
     */
    public static class PostBean {

        private int id;
        private String title;
        private String content;
        private String excerpt;
        private String date;
        private int comment_count;
        private CustomFieldsBean custom_fields;
        private List<CategoriesBean> categories;
        private List<TagsBean> tags;

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

        public List<CategoriesBean> getCategories() {
            return categories;
        }

        public void setCategories(List<CategoriesBean> categories) {
            this.categories = categories;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        /**
         * 文章自定义字段
         */
        public static class CustomFieldsBean {
            private List<String> views;
            private List<String> duoshuo_thread_id;
            private List<String> kratos_love;
            private List<String> specs_zan;

            public List<String> getViews() {
                return views;
            }

            public void setViews(List<String> views) {
                this.views = views;
            }

            public List<String> getDuoshuo_thread_id() {
                return duoshuo_thread_id;
            }

            public void setDuoshuo_thread_id(List<String> duoshuo_thread_id) {
                this.duoshuo_thread_id = duoshuo_thread_id;
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
         * 文章分类
         */
        public static class CategoriesBean {

            private int id;
            private String title;
            private int post_count;

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

            public int getPost_count() {
                return post_count;
            }

            public void setPost_count(int post_count) {
                this.post_count = post_count;
            }
        }

        /**
         * 文章标签
         */
        public static class TagsBean {

            private int id;
            private String title;
            private int post_count;

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

            public int getPost_count() {
                return post_count;
            }

            public void setPost_count(int post_count) {
                this.post_count = post_count;
            }
        }
    }
}
