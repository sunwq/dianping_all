package com.ucdat.dp.spider.utils;

import com.google.common.base.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Random;

/**
 * Jsoup工具类
 * @author liyan
 * @version 1.0
 * @since JDK1.8
 */
public class JsoupUtils {

    //User Agent
    private static final String[] USER_AGENTS = new String[]{
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36"
            ,"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0"
            ,"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36"
            ,"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Maxthon/4.9.4.2000 Chrome/39.0.2146.0 Safari/537.36"
    };

    //随机产生器
    private static final Random RANDOM = new Random();

    /**
     * GET请求
     * @param url URL
     * @return 网页
     * @throws Exception 异常
     */
    public static Document get(String url) throws Exception{
        return Jsoup.connect(url)
                .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Accept-Encoding","gzip, deflate, sdch")
                .header("Accept-Language","zh-CN,zh;q=0.8")
                .referrer(url)
                .userAgent(USER_AGENTS[RANDOM.nextInt(USER_AGENTS.length)])
                .timeout(30000)
                .get();
    }


    public static String getText(Element ele,String defaultStr){
        if(ele != null){
            return ele.text();
        }else {
            return defaultStr;
        }
    }

    public static String getText(Elements eles,String defaultStr){
        if(eles != null){
            try{
                return eles.first().text();
            }catch (Exception ex){
                return defaultStr;
            }
        }else {
            return defaultStr;
        }
    }

    public static String[] getTexts(Elements eles){
        if(eles != null){
            try{
                return eles.stream()
                        .map(x->x.text())
                        .filter(x->!Strings.isNullOrEmpty(x))
                        .toArray(String[]::new);
            }catch (Exception ex){
                return new String[]{};
            }
        }else {
            return new String[]{};
        }
    }

    public static String getAttr(Element ele, String attr, String defaultStr){
        if(ele != null){
            return ele.attr(attr);
        }else {
            return defaultStr;
        }
    }

    public static String getAttr(Elements eles, String attr, String defaultStr){
        if(eles != null){
            try{
                return eles.first().attr(attr);
            }catch (Exception ex){
                return defaultStr;
            }
        }else {
            return defaultStr;
        }
    }

    public static String[] getAttrs(Elements eles,String attr){
        if(eles != null){
            try{
                return eles.stream()
                        .map(x->x.attr(attr))
                        .filter(x->!Strings.isNullOrEmpty(x))
                        .toArray(String[]::new);
            }catch (Exception ex){
                return new String[]{};
            }
        }else {
            return new String[]{};
        }
    }

}
