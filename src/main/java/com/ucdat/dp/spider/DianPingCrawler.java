package com.ucdat.dp.spider;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.ucdat.dp.spider.model.SearchCondition;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by liyan on 16-12-14.
 */
public class DianPingCrawler {
    private static final Gson GSON = new Gson();
    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) " +
            "Chrome/54.0.2840.90 Safari/537.36";

    private static SearchCondition elementToSearchCondition(String parent, Element ele){
        SearchCondition condition = new SearchCondition();
        condition.setName(ele.text());
        condition.setParent(parent);
        condition.setUrl(ele.attr("href"));
        String[] temp = ele.attr("href").split("/");
        condition.setUrlId(temp[temp.length-1]);
        return condition;
    }

    private static void getClassfy(String searchUrl,String outFile){
        try(PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                FileUtils.openOutputStream(new File(outFile)), Charsets.UTF_8),true)){
            Document doc = Jsoup.connect(searchUrl)
                    .userAgent(USER_AGENT)
                    .referrer("http://www.baidu.com")
                    .timeout(30000).get();
            Element classfy = doc.getElementById("classfy");
            Elements c1s = classfy.getElementsByTag("a");
            for(Element ele:c1s){
                if(!ele.text().equals("不限")){
                    SearchCondition condition = elementToSearchCondition(null,ele);
                    System.out.println("==>"+condition.getName());
                    writer.println(GSON.toJson(condition));
                }
                doc = Jsoup.connect("http://www.dianping.com"+ele.attr("href"))
                        .userAgent(USER_AGENT)
                        .referrer("http://www.baidu.com")
                        .timeout(30000).get();
                Element classfySub = doc.getElementById("classfy-sub");
                if(classfySub == null){
                    continue;
                }
                Elements c2s = classfySub.getElementsByTag("a");
                if(c2s == null){
                    continue;
                }
                for(Element ele2:c2s){
                    if(!ele2.text().equals("不限")){
                        SearchCondition condition = elementToSearchCondition(ele.text(),ele2);
                        System.out.println("==>"+condition.getName());
                        writer.println(GSON.toJson(condition));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getRegion(String searchUrl,String outFile){
        try(PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                FileUtils.openOutputStream(new File(outFile)), Charsets.UTF_8),true)){
            Document doc = Jsoup.connect(searchUrl)
                    .userAgent(USER_AGENT)
                    .referrer("http://www.baidu.com")
                    .timeout(30000).get();
            Element classfy = doc.getElementById("region-nav");
            Elements c1s = classfy.getElementsByTag("a");
            for(Element ele:c1s){
                if(!ele.text().equals("不限")){
                    SearchCondition condition = elementToSearchCondition(null,ele);
                    System.out.println("==>"+condition.getName());
                    writer.println(GSON.toJson(condition));
                }
                doc = Jsoup.connect("http://www.dianping.com"+ele.attr("href"))
                        .userAgent(USER_AGENT)
                        .referrer("http://www.baidu.com")
                        .timeout(30000).get();
                Element classfySub = doc.getElementById("region-nav-sub");
                if(classfySub == null){
                    continue;
                }
                Elements c2s = classfySub.getElementsByTag("a");
                if(c2s == null){
                    continue;
                }
                for(Element ele2:c2s){
                    if(!ele2.text().equals("不限")){
                        SearchCondition condition = elementToSearchCondition(ele.text(),ele2);
                        System.out.println("==>"+condition.getName());
                        writer.println(GSON.toJson(condition));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        String city_url = "http://www.dianping.com/search/category/2372/10";
//        String city_name = "dongjing";
        String city_url = "http://www.dianping.com/search/category/8/10";
        String city_name = "chengdu";
        getClassfy(city_url,"E:/data/china/classfy_"+city_name+".txt");
        getRegion(city_url,"E:/data/china/region_"+city_name+".txt");
    }

}
