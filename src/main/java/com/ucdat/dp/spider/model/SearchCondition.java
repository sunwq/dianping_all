package com.ucdat.dp.spider.model;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * Created by liyan on 16-11-21.
 */
public class SearchCondition implements Serializable{
    private String parent;
    private String name;
    private String url;
    private String urlId;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("parent", parent)
                .add("name", name)
                .add("url", url)
                .add("urlId", urlId)
                .toString();
    }
}
