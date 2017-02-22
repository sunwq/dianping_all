package com.ucdat.dp.spider.model;


import java.util.List;
import java.util.Map;

/**
 * Created by liyan on 16-12-9.
 */
public class ShopInfo {
    private String shop_name;
    private String mean_price;
    private String tag_addr;
    private String comment_score;
    private String img_url;
    private String shop_url;
    private String shop_score;
    private String comment_url;
    private String comment_count;
    private Boolean has_freewifi;
    private Boolean has_freestop;
    private Boolean has_onlinebooking;
    private String classfy_parent;
    private String classfy;
    private String region_parent;
    private String region;

    ////////////////////////////////
    private String shop_address;
    private String shop_lng;
    private String shop_lat;
    private String shop_tel;
    private Boolean has_tuan;
    private Boolean has_waimai;
    private Boolean has_ka;
    private String shop_time;
    private String shop_summary;
    private List<String> breadcrumb_info;
    private List<Sale> sales;
    private List<String> cai_titles;
    private List<CaiItem> cai_items;
    private List<String> huanjings;
    private List<String> jiamus;
    private Map<String, String> photo_album;
    private List<String> comment_keys;
    private List<Comment> comment_list;

    public String getShop_lng() {
        return shop_lng;
    }

    public void setShop_lng(String shop_lng) {
        this.shop_lng = shop_lng;
    }

    public String getShop_lat() {
        return shop_lat;
    }

    public void setShop_lat(String shop_lat) {
        this.shop_lat = shop_lat;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getMean_price() {
        return mean_price;
    }

    public void setMean_price(String mean_price) {
        this.mean_price = mean_price;
    }

    public String getTag_addr() {
        return tag_addr;
    }

    public void setTag_addr(String tag_addr) {
        this.tag_addr = tag_addr;
    }

    public String getComment_score() {
        return comment_score;
    }

    public void setComment_score(String comment_score) {
        this.comment_score = comment_score;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getShop_url() {
        return shop_url;
    }

    public void setShop_url(String shop_url) {
        this.shop_url = shop_url;
    }

    public String getShop_score() {
        return shop_score;
    }

    public void setShop_score(String shop_score) {
        this.shop_score = shop_score;
    }

    public String getComment_url() {
        return comment_url;
    }

    public void setComment_url(String comment_url) {
        this.comment_url = comment_url;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public Boolean getHas_freewifi() {
        return has_freewifi;
    }

    public void setHas_freewifi(Boolean has_freewifi) {
        this.has_freewifi = has_freewifi;
    }

    public Boolean getHas_freestop() {
        return has_freestop;
    }

    public void setHas_freestop(Boolean has_freestop) {
        this.has_freestop = has_freestop;
    }

    public Boolean getHas_onlinebooking() {
        return has_onlinebooking;
    }

    public void setHas_onlinebooking(Boolean has_onlinebooking) {
        this.has_onlinebooking = has_onlinebooking;
    }

    public String getClassfy_parent() {
        return classfy_parent;
    }

    public void setClassfy_parent(String classfy_parent) {
        this.classfy_parent = classfy_parent;
    }

    public String getClassfy() {
        return classfy;
    }

    public void setClassfy(String classfy) {
        this.classfy = classfy;
    }

    public String getRegion_parent() {
        return region_parent;
    }

    public void setRegion_parent(String region_parent) {
        this.region_parent = region_parent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getShop_tel() {
        return shop_tel;
    }

    public void setShop_tel(String shop_tel) {
        this.shop_tel = shop_tel;
    }

    public Boolean getHas_tuan() {
        return has_tuan;
    }

    public void setHas_tuan(Boolean has_tuan) {
        this.has_tuan = has_tuan;
    }

    public Boolean getHas_waimai() {
        return has_waimai;
    }

    public void setHas_waimai(Boolean has_waimai) {
        this.has_waimai = has_waimai;
    }

    public Boolean getHas_ka() {
        return has_ka;
    }

    public void setHas_ka(Boolean has_ka) {
        this.has_ka = has_ka;
    }

    public String getShop_time() {
        return shop_time;
    }

    public void setShop_time(String shop_time) {
        this.shop_time = shop_time;
    }

    public String getShop_summary() {
        return shop_summary;
    }

    public void setShop_summary(String shop_summary) {
        this.shop_summary = shop_summary;
    }

    public List<String> getBreadcrumb_info() {
        return breadcrumb_info;
    }

    public void setBreadcrumb_info(List<String> breadcrumb_info) {
        this.breadcrumb_info = breadcrumb_info;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public List<String> getCai_titles() {
        return cai_titles;
    }

    public void setCai_titles(List<String> cai_titles) {
        this.cai_titles = cai_titles;
    }

    public List<CaiItem> getCai_items() {
        return cai_items;
    }

    public void setCai_items(List<CaiItem> cai_items) {
        this.cai_items = cai_items;
    }

    public List<String> getHuanjings() {
        return huanjings;
    }

    public void setHuanjings(List<String> huanjings) {
        this.huanjings = huanjings;
    }

    public List<String> getJiamus() {
        return jiamus;
    }

    public void setJiamus(List<String> jiamus) {
        this.jiamus = jiamus;
    }

    public Map<String, String> getPhoto_album() {
        return photo_album;
    }

    public void setPhoto_album(Map<String, String> photo_album) {
        this.photo_album = photo_album;
    }

    public List<String> getComment_keys() {
        return comment_keys;
    }

    public void setComment_keys(List<String> comment_keys) {
        this.comment_keys = comment_keys;
    }

    public List<Comment> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<Comment> comment_list) {
        this.comment_list = comment_list;
    }
}
