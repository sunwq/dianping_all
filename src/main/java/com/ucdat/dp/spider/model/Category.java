package com.ucdat.dp.spider.model;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * 类别条件
 * @author liyan
 * @version 1.0
 * @since JDK1.8
 */
public class Category implements Serializable{
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equal(urlId, category.urlId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(urlId);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Category{");
        sb.append("parent='").append(parent).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", urlId='").append(urlId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
