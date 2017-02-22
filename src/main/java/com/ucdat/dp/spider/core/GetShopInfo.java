package com.ucdat.dp.spider.core;

import com.ucdat.dp.spider.model.Comment;

import java.util.List;

/**
 * 解析页面模板1
 *
 * 页面模板1 由
 *
 * Created by hasee on 2017/2/22.
 */
public class GetShopInfo {



}

interface Moban {
    String getShopId();
    String getShopName();
    String getShopCityId();
    String getShopCountry();
    String getShopCity();
    String getShopRegion();
    String getShopAddress();
    String getShopLng();
    String getShopLat();
    String getShopTel();
    String getShopType();      //店面类型 如美食
    String getShopMainType(); //主要类型，如 美食下的中国菜
    String getShopSubType();  //次要类型 如中国菜 中的 湘菜
    String getMeanPrice();
    String getShopScore();
    String getKouweiScore(); //口味
    String getHuanjingScore(); //环境
    String getFuwuScore(); //服务
    String getShihuiScore() ; //实惠
    String getShoptime(); //开放时间
    String getShopFeature(); // ????
    String getFoodItems(); // 获取菜单
    String getAtmosphere(); //气氛
    String getImg_Url();//店铺图片
    String getDescription(); //店铺描述
    String getDatasource(); //数据来源
    String lnglat_source(); //经纬度数据来源
    String getMobanId(); //哪一个模板

    List<Comment> getComments(); //评论
}


/**
 * 解析页面模板1
 *
 * 页面模板1 由js中  window.shop_config 标示出来，在页面源代码中还有“ window.shop_config”，则属于模板1
 *
 */

class Moban1 implements Moban{
    private final String content;
    public Moban1(String content) {
        this.content = content;
    }


    @Override
    public String getShopId() {
        return null;
    }

    @Override
    public String getShopName() {
        return null;
    }

    @Override
    public String getShopCityId() {
        return null;
    }

    @Override
    public String getShopCountry() {
        return null;
    }

    @Override
    public String getShopCity() {
        return null;
    }

    @Override
    public String getShopRegion() {
        return null;
    }

    @Override
    public String getShopAddress() {
        return null;
    }

    @Override
    public String getShopLng() {
        return null;
    }

    @Override
    public String getShopLat() {
        return null;
    }

    @Override
    public String getShopTel() {
        return null;
    }

    @Override
    public String getShopType() {
        return null;
    }

    @Override
    public String getShopMainType() {
        return null;
    }

    @Override
    public String getShopSubType() {
        return null;
    }

    @Override
    public String getMeanPrice() {
        return null;
    }

    @Override
    public String getShopScore() {
        return null;
    }

    @Override
    public String getKouweiScore() {
        return null;
    }

    @Override
    public String getHuanjingScore() {
        return null;
    }

    @Override
    public String getFuwuScore() {
        return null;
    }

    @Override
    public String getShihuiScore() {
        return null;
    }

    @Override
    public String getShoptime() {
        return null;
    }

    @Override
    public String getShopFeature() {
        return null;
    }

    @Override
    public String getFoodItems() {
        return null;
    }

    @Override
    public String getAtmosphere() {
        return null;
    }

    @Override
    public String getImg_Url() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getDatasource() {
        return null;
    }

    @Override
    public String lnglat_source() {
        return null;
    }

    @Override
    public String getMobanId() {
        return null;
    }

    @Override
    public List<Comment> getComments() {
        return null;
    }
}


/**
 * 解析页面模板2
 *
 * 页面模板2 由js中  window.load 标示出来，在页面源代码中还有“ window.load”，则属于模板2
 *
 */

class Moban2 implements Moban{
    private final String content;
    public Moban2(String content) {
        this.content = content;
    }

    @Override
    public String getShopId() {
        return null;
    }

    @Override
    public String getShopName() {
        return null;
    }

    @Override
    public String getShopCityId() {
        return null;
    }

    @Override
    public String getShopCountry() {
        return null;
    }

    @Override
    public String getShopCity() {
        return null;
    }

    @Override
    public String getShopRegion() {
        return null;
    }

    @Override
    public String getShopAddress() {
        return null;
    }

    @Override
    public String getShopLng() {
        return null;
    }

    @Override
    public String getShopLat() {
        return null;
    }

    @Override
    public String getShopTel() {
        return null;
    }

    @Override
    public String getShopType() {
        return null;
    }

    @Override
    public String getShopMainType() {
        return null;
    }

    @Override
    public String getShopSubType() {
        return null;
    }

    @Override
    public String getMeanPrice() {
        return null;
    }

    @Override
    public String getShopScore() {
        return null;
    }

    @Override
    public String getKouweiScore() {
        return null;
    }

    @Override
    public String getHuanjingScore() {
        return null;
    }

    @Override
    public String getFuwuScore() {
        return null;
    }

    @Override
    public String getShihuiScore() {
        return null;
    }

    @Override
    public String getShoptime() {
        return null;
    }

    @Override
    public String getShopFeature() {
        return null;
    }

    @Override
    public String getFoodItems() {
        return null;
    }

    @Override
    public String getAtmosphere() {
        return null;
    }

    @Override
    public String getImg_Url() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getDatasource() {
        return null;
    }

    @Override
    public String lnglat_source() {
        return null;
    }

    @Override
    public String getMobanId() {
        return null;
    }

    @Override
    public List<Comment> getComments() {
        return null;
    }
}



/**
 * 解析页面模板3
 *
 * 页面模板3    由js中  facade 标示出来，在页面源代码中还有“ facade{...}”，则属于模板3
 *
 */

class Moban3 implements Moban{
    private final String content;
    public Moban3(String content) {
        this.content = content;
    }

    @Override
    public String getShopId() {
        return null;
    }

    @Override
    public String getShopName() {
        return null;
    }

    @Override
    public String getShopCityId() {
        return null;
    }

    @Override
    public String getShopCountry() {
        return null;
    }

    @Override
    public String getShopCity() {
        return null;
    }

    @Override
    public String getShopRegion() {
        return null;
    }

    @Override
    public String getShopAddress() {
        return null;
    }

    @Override
    public String getShopLng() {
        return null;
    }

    @Override
    public String getShopLat() {
        return null;
    }

    @Override
    public String getShopTel() {
        return null;
    }

    @Override
    public String getShopType() {
        return null;
    }

    @Override
    public String getShopMainType() {
        return null;
    }

    @Override
    public String getShopSubType() {
        return null;
    }

    @Override
    public String getMeanPrice() {
        return null;
    }

    @Override
    public String getShopScore() {
        return null;
    }

    @Override
    public String getKouweiScore() {
        return null;
    }

    @Override
    public String getHuanjingScore() {
        return null;
    }

    @Override
    public String getFuwuScore() {
        return null;
    }

    @Override
    public String getShihuiScore() {
        return null;
    }

    @Override
    public String getShoptime() {
        return null;
    }

    @Override
    public String getShopFeature() {
        return null;
    }

    @Override
    public String getFoodItems() {
        return null;
    }

    @Override
    public String getAtmosphere() {
        return null;
    }

    @Override
    public String getImg_Url() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getDatasource() {
        return null;
    }

    @Override
    public String lnglat_source() {
        return null;
    }

    @Override
    public String getMobanId() {
        return null;
    }

    @Override
    public List<Comment> getComments() {
        return null;
    }
}

/**
 * 解析页面模板other
 *
 * 页面模板other    其他不符合前面模板形式的，由html parser、Jsoup等按照专业网页解析
 *                  发现模板可以在前面增加
 *
 *
 */
class Moban4 implements Moban{
    private final String content;
    public Moban4(String content) {
        this.content = content;
    }

    @Override
    public String getShopId() {
        return null;
    }

    @Override
    public String getShopName() {
        return null;
    }

    @Override
    public String getShopCityId() {
        return null;
    }

    @Override
    public String getShopCountry() {
        return null;
    }

    @Override
    public String getShopCity() {
        return null;
    }

    @Override
    public String getShopRegion() {
        return null;
    }

    @Override
    public String getShopAddress() {
        return null;
    }

    @Override
    public String getShopLng() {
        return null;
    }

    @Override
    public String getShopLat() {
        return null;
    }

    @Override
    public String getShopTel() {
        return null;
    }

    @Override
    public String getShopType() {
        return null;
    }

    @Override
    public String getShopMainType() {
        return null;
    }

    @Override
    public String getShopSubType() {
        return null;
    }

    @Override
    public String getMeanPrice() {
        return null;
    }

    @Override
    public String getShopScore() {
        return null;
    }

    @Override
    public String getKouweiScore() {
        return null;
    }

    @Override
    public String getHuanjingScore() {
        return null;
    }

    @Override
    public String getFuwuScore() {
        return null;
    }

    @Override
    public String getShihuiScore() {
        return null;
    }

    @Override
    public String getShoptime() {
        return null;
    }

    @Override
    public String getShopFeature() {
        return null;
    }

    @Override
    public String getFoodItems() {
        return null;
    }

    @Override
    public String getAtmosphere() {
        return null;
    }

    @Override
    public String getImg_Url() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getDatasource() {
        return null;
    }

    @Override
    public String lnglat_source() {
        return null;
    }

    @Override
    public String getMobanId() {
        return null;
    }

    @Override
    public List<Comment> getComments() {
        return null;
    }
}




