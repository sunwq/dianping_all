package com.ucdat.dp.spider.model;

import java.util.List;
import java.util.Map;

/**
 * Created by liyan on 16-12-6.
 */
public class Task {
    private Category classfy;
    private Category region;
    private int page;
    private String code;
    private String url;
    private List<Map<String,Object>> shopInfo;

    public Category getClassfy() {
        return classfy;
    }

    public void setClassfy(Category classfy) {
        this.classfy = classfy;
    }

    public Category getRegion() {
        return region;
    }

    public void setRegion(Category region) {
        this.region = region;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Map<String,Object>> getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(List<Map<String,Object>> shopInfo) {
        this.shopInfo = shopInfo;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Task{");
        sb.append("classfy=").append(classfy);
        sb.append(", region=").append(region);
        sb.append(", page=").append(page);
        sb.append(", code='").append(code).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", shopInfo=").append(shopInfo);
        sb.append('}');
        return sb.toString();
    }
}
