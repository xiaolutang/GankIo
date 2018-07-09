package com.example.txl.gankio.bean;

import java.util.List;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/7
 * description：
 */
public class IdelInfo {
    boolean error;
    List<InfoContent> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<InfoContent> getResults() {
        return results;
    }

    public void setResults(List<InfoContent> results) {
        this.results = results;
    }

    public class InfoContent{
        String _id;
        String content;
        String cover;
        long crawled;
        String created_at;
        boolean deleted;
        String published_at;
        String raw;
        Site site;
        String title;
        String uid;
        String url;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public long getCrawled() {
            return crawled;
        }

        public void setCrawled(long crawled) {
            this.crawled = crawled;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public String getPublished_at() {
            return published_at;
        }

        public void setPublished_at(String published_at) {
            this.published_at = published_at;
        }

        public String getRaw() {
            return raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }

        public Site getSite() {
            return site;
        }

        public void setSite(Site site) {
            this.site = site;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public class Site{
            String cat_cn;
            String cat_en;
            String desc;
            String feed_id;
            String icon;
            String id;
            String name;
            int subscribers;
            String type;
            String url;


            public String getCat_cn() {
                return cat_cn;
            }

            public void setCat_cn(String cat_cn) {
                this.cat_cn = cat_cn;
            }

            public String getCat_en() {
                return cat_en;
            }

            public void setCat_en(String cat_en) {
                this.cat_en = cat_en;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getFeed_id() {
                return feed_id;
            }

            public void setFeed_id(String feed_id) {
                this.feed_id = feed_id;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSubscribers() {
                return subscribers;
            }

            public void setSubscribers(int subscribers) {
                this.subscribers = subscribers;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
