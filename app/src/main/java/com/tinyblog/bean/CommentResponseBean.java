package com.tinyblog.bean;

import java.util.List;

/**
 * @author xiarui
 * @date 2017/3/5 14:21
 * @description 评论响应 Bean 对象
 * @remark
 */

public class CommentResponseBean {

    private ResponseBean response;
    private int code;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ResponseBean {

        private String post_id;
        private String thread_id;
        private String status;
        private String source;
        private String type;
        private String message;
        private String created_at;
        private int parent_id;
        private int root_id;
        private int reposts;
        private int comments;
        private String author_id;
        private String author_key;
        private String agent;
        private int likes;
        private int dislikes;
        private int reports;
        private AuthorBean author;
        private ThreadBean thread;
        private SiteBean site;
        private int is_top;
        private List<?> privileges;
        private List<?> parents;

        public String getPost_id() {
            return post_id;
        }

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }

        public String getThread_id() {
            return thread_id;
        }

        public void setThread_id(String thread_id) {
            this.thread_id = thread_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getRoot_id() {
            return root_id;
        }

        public void setRoot_id(int root_id) {
            this.root_id = root_id;
        }

        public int getReposts() {
            return reposts;
        }

        public void setReposts(int reposts) {
            this.reposts = reposts;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public String getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(String author_id) {
            this.author_id = author_id;
        }

        public String getAuthor_key() {
            return author_key;
        }

        public void setAuthor_key(String author_key) {
            this.author_key = author_key;
        }

        public String getAgent() {
            return agent;
        }

        public void setAgent(String agent) {
            this.agent = agent;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getDislikes() {
            return dislikes;
        }

        public void setDislikes(int dislikes) {
            this.dislikes = dislikes;
        }

        public int getReports() {
            return reports;
        }

        public void setReports(int reports) {
            this.reports = reports;
        }

        public AuthorBean getAuthor() {
            return author;
        }

        public void setAuthor(AuthorBean author) {
            this.author = author;
        }

        public ThreadBean getThread() {
            return thread;
        }

        public void setThread(ThreadBean thread) {
            this.thread = thread;
        }

        public SiteBean getSite() {
            return site;
        }

        public void setSite(SiteBean site) {
            this.site = site;
        }

        public int getIs_top() {
            return is_top;
        }

        public void setIs_top(int is_top) {
            this.is_top = is_top;
        }

        public List<?> getPrivileges() {
            return privileges;
        }

        public void setPrivileges(List<?> privileges) {
            this.privileges = privileges;
        }

        public List<?> getParents() {
            return parents;
        }

        public void setParents(List<?> parents) {
            this.parents = parents;
        }

        public static class AuthorBean {

            private String name;
            private Object avatar_url;
            private Object url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getAvatar_url() {
                return avatar_url;
            }

            public void setAvatar_url(Object avatar_url) {
                this.avatar_url = avatar_url;
            }

            public Object getUrl() {
                return url;
            }

            public void setUrl(Object url) {
                this.url = url;
            }
        }

        public static class ThreadBean {

            private String thread_id;
            private int site_id;
            private String title;
            private String created_at;
            private String thread_key;
            private String url;
            private MetaBean meta;
            private String agent;
            private String source;
            private String author_id;
            private int comments;
            private int dislikes;
            private int likes;
            private int reposts;
            private int views;
            private int post_enable;

            public String getThread_id() {
                return thread_id;
            }

            public void setThread_id(String thread_id) {
                this.thread_id = thread_id;
            }

            public int getSite_id() {
                return site_id;
            }

            public void setSite_id(int site_id) {
                this.site_id = site_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getThread_key() {
                return thread_key;
            }

            public void setThread_key(String thread_key) {
                this.thread_key = thread_key;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public MetaBean getMeta() {
                return meta;
            }

            public void setMeta(MetaBean meta) {
                this.meta = meta;
            }

            public String getAgent() {
                return agent;
            }

            public void setAgent(String agent) {
                this.agent = agent;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getAuthor_id() {
                return author_id;
            }

            public void setAuthor_id(String author_id) {
                this.author_id = author_id;
            }

            public int getComments() {
                return comments;
            }

            public void setComments(int comments) {
                this.comments = comments;
            }

            public int getDislikes() {
                return dislikes;
            }

            public void setDislikes(int dislikes) {
                this.dislikes = dislikes;
            }

            public int getLikes() {
                return likes;
            }

            public void setLikes(int likes) {
                this.likes = likes;
            }

            public int getReposts() {
                return reposts;
            }

            public void setReposts(int reposts) {
                this.reposts = reposts;
            }

            public int getViews() {
                return views;
            }

            public void setViews(int views) {
                this.views = views;
            }

            public int getPost_enable() {
                return post_enable;
            }

            public void setPost_enable(int post_enable) {
                this.post_enable = post_enable;
            }

            public static class MetaBean {

                private String guid;
                private String comment_status;
                private String ping_status;

                public String getGuid() {
                    return guid;
                }

                public void setGuid(String guid) {
                    this.guid = guid;
                }

                public String getComment_status() {
                    return comment_status;
                }

                public void setComment_status(String comment_status) {
                    this.comment_status = comment_status;
                }

                public String getPing_status() {
                    return ping_status;
                }

                public void setPing_status(String ping_status) {
                    this.ping_status = ping_status;
                }
            }
        }

        public static class SiteBean {

            private String site_id;
            private String short_name;
            private String url;
            private String name;

            public String getSite_id() {
                return site_id;
            }

            public void setSite_id(String site_id) {
                this.site_id = site_id;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
