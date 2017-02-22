package com.ucdat.dp.spider.core;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ucdat.dp.spider.model.Category;
import com.ucdat.dp.spider.model.Task;
import com.ucdat.dp.spider.utils.GsonUtils;
import com.ucdat.dp.spider.utils.JsoupUtils;
import com.ucdat.dp.spider.utils.ProxyTest;
import org.apache.commons.io.IOUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by liyan on 16-12-6.
 */
public class SearchTaskMultiThread {
    static final int THREAD_NUM = 3;
    ExecutorService pool;
    String baseUrl = "http://www.dianping.com";    ///日本东京
    String classfy_path = "japan/classfy_";
    String region_path = "japan/region_";
//            String baseUrl = "http://www.dianping.com/search/category/2315/10/";    //日本
//            String baseUrl = "http://www.dianping.com/search/category/2322/10/";    //墨尔本
//    String baseUrl = "http://www.dianping.com/search/category/2/10/";    //北京

    public SearchTaskMultiThread(String city, Integer thread_num) {
        pool = Executors.newFixedThreadPool(thread_num);
        classfy_path += city + ".txt";
        region_path += city + ".txt";
        System.out.println(classfy_path);
        System.out.println(region_path);
    }

    public void searchTask(String dataOutFile, String errOutFile, Set<String> successed) throws IOException {
        Set<Category> classfys = Stream.of(
                IOUtils.readLines(SearchTaskMultiThread.class.getClassLoader().getResourceAsStream(classfy_path), Charsets.UTF_8)
        ).filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .filter(x -> !Strings.isNullOrEmpty(x))
                .map(x -> GsonUtils.GSON.fromJson(x, Category.class))
                .collect(Collectors.toSet());
        Set<Category> regions = Stream.of(
                IOUtils.readLines(SearchTaskMultiThread.class.getClassLoader().getResourceAsStream(region_path), Charsets.UTF_8)
        ).filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .filter(x -> !Strings.isNullOrEmpty(x))
                .map(x -> GsonUtils.GSON.fromJson(x, Category.class))
                .collect(Collectors.toSet());
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(dataOutFile), Charsets.UTF_8), true);
             PrintWriter error = new PrintWriter(new OutputStreamWriter(new FileOutputStream(errOutFile), Charsets.UTF_8), true)) {
//            int count = 0;
            for (Category classfy : classfys) {
                for (Category region : regions) {
                    pool.execute(new ShopTaskRunner(writer, error, classfy, region));
                }
            }
            pool.shutdown();
//            pool.awaitTermination(10, TimeUnit.DAYS);
        }
    }

    class ShopTaskRunner implements Runnable {
        PrintWriter writer;
        PrintWriter error;
        Category classfy;
        Category region;

        public ShopTaskRunner(PrintWriter writer, PrintWriter error, Category classfy, Category region) {
            this.writer = writer;
            this.error = error;
            this.classfy = classfy;
            this.region = region;
        }

        @Override
        public void run() {
            crawl();
        }

        private void crawl() {
            for (int i = 1; i <= 50; i++) {
                Task task = new Task();
                task.setClassfy(classfy);
                task.setRegion(region);
                task.setPage(i);
//                task.setCode(classfy.getUrlId() + region.getUrlId() + "p" + i);
                task.setUrl(baseUrl+classfy.getUrl() + region.getUrlId() + "p" + i);
//                    if (successed.contains(task.getCode())) {
//                        continue;
//                    }
                System.out.println("=====>" + task);
                boolean is404 = false;
                boolean isSuccess = false;
                for (int j = 0; j < 3; j++) {
                    //尝试3次
                    try {
                        Thread.sleep(200);
                        Document doc = ProxyTest.getPage(task.getUrl(), "http://www.dianping.com/");
                        Elements list = doc.select("#shop-all-list > ul > li");
                        List<Map<String, Object>> tempData = Lists.newArrayList();
                        for (Element ele : list) {
                            Map<String, Object> data = Maps.newHashMap();
                            //店铺名称
                            data.put("shop_name", JsoupUtils.getText(ele.select("div.txt > div.tit > a > h4"), ""));
                            //均价
                            data.put("mean_price", JsoupUtils.getText(ele.select("div.txt > div.comment > a.mean-price"), ""));
                            //标签和地址
                            data.put("tag_addr", JsoupUtils.getText(ele.select("div.txt > div.tag-addr"), ""));
                            //评论总分数
                            data.put("comment_score", JsoupUtils.getText(ele.select("div.txt > span"), ""));
                            //店铺图片链接
                            data.put("img_url", JsoupUtils.getAttr(ele.select("div.pic > a > img"), "data-src", ""));
                            //店铺链接
                            data.put("shop_url", JsoupUtils.getAttr(ele.select("div.pic > a"), "href", ""));
                            //店铺得分
                            data.put("shop_score", JsoupUtils.getAttr(ele.select("div.txt > div.comment > span"), "title", ""));
                            //评论链接
                            data.put("comment_url", JsoupUtils.getAttr(ele.select("div.txt > div.comment > a.review-num"), "href", ""));
                            //评论数
                            data.put("comment_count", JsoupUtils.getText(ele.select("div.txt > div.comment > a.review-num"), ""));
                            //免费WiFI
                            data.put("has_freewifi", JsoupUtils.getText(ele.select("div.svr-info"), "").contains("免费WiFi"));
                            //免费停车
                            data.put("has_freestop", JsoupUtils.getText(ele.select("div.svr-info"), "").contains("免费停车"));
                            //在线订座
                            data.put("has_onlinebooking", JsoupUtils.getText(ele.select("div.svr-info"), "").contains("在线订座"));
                            //分类
                            data.put("classfy_parent", classfy.getParent());
                            data.put("classfy", classfy.getName());
                            //区域
                            data.put("region_parent", region.getParent());
                            data.put("region", region.getName());

                            tempData.add(data);

                        }
                        task.setShopInfo(tempData);
                        isSuccess = true;
                        break;
                    } catch (Exception e) {
                        if (e instanceof HttpStatusException) {
                            HttpStatusException ex = (HttpStatusException) e;
                            if (ex.getStatusCode() == 404) {
                                //404
                                is404 = true;
                                isSuccess = true;
                                break;
                            }
                        }
                        if (e instanceof FileNotFoundException) {
                            is404 = true;
                            isSuccess = true;
                            break;
                        }
                        System.out.println("ERROR:" + e.getMessage());
                    }
                }
                if (isSuccess) {
                    //成功
                    writer.println(GsonUtils.GSON.toJson(task));
                } else {
                    //失败
                    error.println(GsonUtils.GSON.toJson(task));
                }
                if (is404) {
                    //当前页面不存在，跳过后续页面
                    break;
                }
            }
        }

    }


}
