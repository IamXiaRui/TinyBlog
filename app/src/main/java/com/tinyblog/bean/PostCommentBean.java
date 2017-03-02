package com.tinyblog.bean;

import java.util.List;

/**
 * @author xiarui
 * @date 2017/3/2 14:47
 * @desc 文章评论Bean
 * @remark
 */

public class PostCommentBean {

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

    public static class PostBean {

        private int id;
        private int comment_count;
        private CustomFieldsBean custom_fields;
        private List<CommentsBean> comments;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public static class CustomFieldsBean {
            private List<String> duoshuo_thread_id;

            public List<String> getDuoshuo_thread_id() {
                return duoshuo_thread_id;
            }

            public void setDuoshuo_thread_id(List<String> duoshuo_thread_id) {
                this.duoshuo_thread_id = duoshuo_thread_id;
            }
        }

        public static class CommentsBean {

            private int id;
            private String name;
            private String date;
            private String content;
            private int parent;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getParent() {
                return parent;
            }

            public void setParent(int parent) {
                this.parent = parent;
            }
        }
    }
}
