package com.ucdat.dp.spider.core;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ucdat.dp.spider.model.Task;
import com.ucdat.dp.spider.utils.GsonUtils;
import com.ucdat.dp.spider.utils.JsoupUtils;
import com.ucdat.dp.spider.utils.ProxyTest;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ucdat.dp.spider.utils.JsoupUtils.getText;

/**
 * Created by liyan on 16-12-6.
 */
public class GetShopInfoTool {

    public void getShopInfo(String taskFile,String dataOutFile,String errOutFile,Set<String> successed){
        try(Scanner scanner = new Scanner(new FileInputStream(taskFile),"UTF-8");
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(dataOutFile), Charsets.UTF_8),true);
            PrintWriter error = new PrintWriter(new OutputStreamWriter(new FileOutputStream(errOutFile),Charsets.UTF_8),true)){
            String line = null;
            while (scanner.hasNext()){
                line = scanner.nextLine();
                if(!Strings.isNullOrEmpty(line)){
                    Task task = GsonUtils.GSON.fromJson(line, Task.class);
                    System.out.println(task.getCode());
                    if(successed.contains(task.getCode())){
                        continue;
                    }
                    if(task.getShopInfo() != null){
                        List<Map<String,Object>> shopInfos = task.getShopInfo();
                        for(Map<String,Object> shopInfo:shopInfos) {
                            String shopUrl = MoreObjects.firstNonNull(shopInfo.get("shop_url"), "").toString().trim();
                            if (!Strings.isNullOrEmpty(shopUrl)) {
                                String url = "http://www.dianping.com" + shopUrl;
                                System.out.println("===>" + url);
                                boolean isSuccess = false;
                                Map<String, Object> data = null;
                                for (int j = 0; j < 3; j++) {
                                    try {
                                        Thread.sleep(200);
                                        Document doc = ProxyTest.getPage(url, "http://www.dianping.com/");
                                        data = Maps.newHashMap();
                                        data.putAll(shopInfo);
                                        //地址
                                        data.put("shop_address", getText(doc.select("#basic-info > div.expand-info.address"), ""));
                                        //经纬度
                                        String str_lnglat = doc.select("#aside > script").first().toString();
                                        int lngStart = str_lnglat.indexOf("({lng:");
                                        int lngEnd = str_lnglat.indexOf(",lat:");
                                        int latEnd = str_lnglat.indexOf("});");
                                        String lng = "",lat = "";
                                        if(lngStart > 0 && lngEnd > 0 && latEnd > 0){
                                            lng = str_lnglat.substring(lngStart+6, lngEnd);
                                            lat = str_lnglat.substring(lngEnd+5, latEnd);
                                        }
                                        data.put("shop_lng",lng);
                                        data.put("shop_lat",lat);
                                        //电话
                                        data.put("shop_tel", getText(doc.select("#basic-info > p.expand-info.tel"), ""));
                                        //特色
                                        data.put("has_tuan", JsoupUtils.getAttr(doc.select("#basic-info > div.promosearch-wrapper > p > a.tag-tuan-b"), "class", "").contains("tuan"));
                                        data.put("has_waimai", JsoupUtils.getAttr(doc.select("#basic-info > div.promosearch-wrapper > p > a.tag-wai-b"), "class", "").contains("wai"));
                                        data.put("has_ka", JsoupUtils.getAttr(doc.select("#basic-info > div.promosearch-wrapper > p > a.tag-ka-b"), "class", "").contains("ka"));
                                        Elements otherInfos = doc.select("#basic-info > div.other.J-other > p.info.info-indent");
                                        for (Element ele : otherInfos) {
                                            String str = getText(ele, "");
                                            if (Strings.isNullOrEmpty(str)) {
                                                continue;
                                            }
                                            if (str.startsWith("营业时间：")) {
                                                data.put("shop_time", str.substring("营业时间：".length()));
                                            } else if (str.startsWith("餐厅简介：")) {
                                                data.put("shop_summary", str.substring("餐厅简介：".length()));
                                            }
                                        }
                                        //////////////////////////////////////////////////////
                                        String[] ppp = JsoupUtils.getTexts(doc.select("#body > div.body-content.clearfix > div.breadcrumb > a"));
                                        data.put("breadcrumb_info", ppp);
                                        ///////////////////////////////////////////////////////
                                        //团购
                                        Element sales = doc.getElementById("sales");
                                        List<Map<String, String>> saleMap = Lists.newArrayList();
                                        if (sales != null) {
                                            Elements itmes1 = sales.select("div.group.clearfix div.item.big");
                                            Elements itmes2 = sales.select("div.group.clearfix a.item.small");
                                            for (Element ele : itmes1) {
                                                String name = JsoupUtils.getText(ele.select("p"), "");
                                                String href = JsoupUtils.getAttr(ele.select("a"), "href", "");
                                                if (!Strings.isNullOrEmpty(name) && !Strings.isNullOrEmpty(href)) {
                                                    Map<String, String> sale = Maps.newHashMap();
                                                    sale.put("name", name);
                                                    sale.put("href", href);
                                                    saleMap.add(sale);
                                                }
                                            }
                                            for (Element ele : itmes2) {
                                                String name = ele.ownText();
                                                String href = ele.attr("href");
                                                if (!Strings.isNullOrEmpty(name) && !Strings.isNullOrEmpty(href) && !href.contains("javascript")) {
                                                    Map<String, String> sale = Maps.newHashMap();
                                                    sale.put("name", name);
                                                    sale.put("href", href);
                                                    saleMap.add(sale);
                                                }
                                            }
                                        }
                                        data.put("sales", saleMap);
                                        /////////////////////////////////////////////////////
                                        //推荐菜、环境、价目表、官方相册
                                        Elements tts = doc.select("#shop-tabs > h2.mod-title > a.item.J_item");
                                        Elements divs = null;
                                        Elements xx = null;
                                        if (doc.getElementById("shop-tabs") != null) {
                                            xx = doc.getElementById("shop-tabs").getElementsByTag("script");
                                        }
                                        if (xx != null && xx.size() > 0) {
                                            Document doc2 = Jsoup.parse(xx.html(), doc.baseUri());
                                            divs = doc2.getElementsByClass("J-panel");
                                        } else {
                                            divs = doc.select("#shop-tabs > *.J-panels > div.J-panel");
                                        }
                                        int index = -1;
                                        //菜品数据
                                        for (int i = 0; i < tts.size(); i++) {
                                            Element ele = tts.get(i);
                                            if (getText(ele, "").trim().equals("推荐菜")) {
                                                index = i;
                                                break;
                                            }
                                        }
                                        if (index >= 0 && index < divs.size()) {
                                            Element div = divs.get(index);
                                            Elements cais = div.select("p.recommend-name > a");
                                            data.put("cai_titles", JsoupUtils.getAttrs(cais, "title"));
                                            Elements pp = div.select("ul.recommend-photo > li.item");
                                            List<Map<String, Object>> items = Lists.newArrayList();
                                            for (Element ele : pp) {
                                                Map<String, Object> temp = Maps.newHashMap();
                                                temp.put("name", getText(ele.select("p.name"), ""));
                                                temp.put("price", getText(ele.select("span.price"), ""));
                                                temp.put("img", JsoupUtils.getAttr(ele.select("img"), "src", ""));
                                                items.add(temp);
                                            }
                                            data.put("cai_items", items);
                                        }
                                        index = -1;
                                        //环境数据
                                        for (int i = 0; i < tts.size(); i++) {
                                            Element ele = tts.get(i);
                                            if (getText(ele, "").trim().equals("环境")) {
                                                index = i;
                                                break;
                                            }
                                        }
                                        if (index >= 0 && index < divs.size()) {
                                            Element div = divs.get(index);
                                            Elements huanjings = div.select("div > a > img");
                                            data.put("huanjings", JsoupUtils.getAttrs(huanjings, "src"));
                                        }
                                        index = -1;
                                        //价目表
                                        for (int i = 0; i < tts.size(); i++) {
                                            Element ele = tts.get(i);
                                            if (getText(ele, "").trim().equals("价目表")) {
                                                index = i;
                                                break;
                                            }
                                        }
                                        if (index >= 0 && index < divs.size()) {
                                            Element div = divs.get(index);
                                            Elements jiamus = div.select("div > a > img");
                                            data.put("jiamus", JsoupUtils.getAttrs(jiamus, "src"));
                                        }
                                        index = -1;
                                        //官方相册
                                        for (int i = 0; i < tts.size(); i++) {
                                            Element ele = tts.get(i);
                                            if (getText(ele, "").trim().equals("官方相册")) {
                                                index = i;
                                                break;
                                            }
                                        }
                                        if (index >= 0 && index < divs.size()) {
                                            Element div = divs.get(index);
                                            Elements xiangces = div.select("div > div.item");
                                            Map<String, String> picMap = Maps.newHashMap();
                                            for (Element xiangce : xiangces) {
                                                String name = xiangce.text();
                                                String picUrl = JsoupUtils.getAttr(xiangce.select("a"), "href", "");
                                                if (Strings.isNullOrEmpty(picUrl)) {
                                                    continue;
                                                }
                                                try {
                                                    Document doc2 = ProxyTest.getPage("http://www.dianping.com" + picUrl, "http://www.dianping.com/");
                                                    String tempUrl = JsoupUtils.getAttr(doc2.select("#top > div.trans-nav.detapages > div > ul > li:nth-child(3) > strong > a"), "href", "");
                                                    if (!Strings.isNullOrEmpty(tempUrl)) {
                                                        String[] pp = tempUrl.split("/");
                                                        String[] pp2 = shopUrl.split("/");
                                                        String id = pp[pp.length - 1];
                                                        String id2 = pp2[pp2.length - 1];
                                                        String picsJson = Jsoup.connect(String.format("http://www.dianping.com/ajax/json/shoppic/find?type=productAlbum&albumId=%s&shopId=%s&shopType=55&full=0&href=1&firstPos=0&count=50", id, id2))
                                                                .ignoreContentType(true)
                                                                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                                                                .header("Accept-Encoding", "gzip, deflate")
                                                                .header("Accept-Language", "zh-CN,zh;q=0.8")
                                                                .userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:50.0) Gecko/20100101 Firefox/50.0")
                                                                .method(Connection.Method.GET).timeout(30000).execute().body();
                                                        picMap.put(name, picsJson);
                                                    }
                                                } catch (Exception ex) {
                                                    //
                                                    ex.printStackTrace();
                                                }
                                            }
                                            data.put("photo_album", picMap);
                                        }
                                        //////////////////////////////////////
                                        //评论
                                        Elements pinglunKeys = doc.select("#comment > div.comment-condition.J-comment-condition > div.content > span");
                                        data.put("comment_keys", JsoupUtils.getTexts(pinglunKeys));
                                        //北京评论
//                                        Elements pingluns = doc.select("#comment > ul.comment-list.J-list > li.comment-item");
                                        //其他地区评论（日本、墨尔本）
                                        Elements pingluns = doc.select("#comment > ul.comment-list.J-list > div.comment-list > ul > li.comment-item");
                                        List<Map<String, Object>> commentList = Lists.newArrayList();
                                        for (Element pinlun : pingluns) {
                                            Map<String, Object> comment = Maps.newHashMap();
                                            comment.put("user_url", JsoupUtils.getAttr(pinlun.select("a.avatar.J-avatar"), "href", ""));
                                            comment.put("user_id", JsoupUtils.getAttr(pinlun.select("a.avatar.J-avatar"), "data-user-id", ""));
                                            comment.put("user_name", JsoupUtils.getText(pinlun.select("p.user-info > a.name"), ""));
                                            comment.put("user_rank", JsoupUtils.getAttr(pinlun.select("p.user-info > span.user-rank-rst"), "title", ""));
                                            comment.put("comment_rank", JsoupUtils.getAttr(pinlun.select("div.content > p.shop-info > span.sml-rank-stars"), "class", ""));
                                            String desc = getText(pinlun.select("div.content > div.info.J-info-all.Hide"), "");
                                            if (Strings.isNullOrEmpty(desc)) {
                                                desc = getText(pinlun.select("div.content > p.desc"), "");
                                            }
                                            comment.put("comment_desc", desc);
                                            comment.put("comment_photos", JsoupUtils.getAttrs(pinlun.select("div.content > div.photos > div > a > img"), "data-lazyload"));
                                            comment.put("comment_time", getText(pinlun.select("div.content > div.misc-info > span.time"), ""));
                                            commentList.add(comment);
                                        }
                                        data.put("comment_list", commentList);
                                        isSuccess = true;
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("ERROR:" + e.getMessage());
                                    }
                                }
                                if (isSuccess && data != null) {
                                    //成功
                                    writer.println(GsonUtils.GSON.toJson(data));
                                } else {
                                    //失败
                                    error.println(shopUrl);
                                }
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
