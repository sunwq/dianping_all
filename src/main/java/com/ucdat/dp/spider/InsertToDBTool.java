package com.ucdat.dp.spider;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.stmt.DoubleMaxStatementCache;
import com.ucdat.dp.spider.model.Comment;
import com.ucdat.dp.spider.model.ShopInfo;
import com.ucdat.dp.spider.utils.GsonUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by liyan on 16-12-6.
 */
public class InsertToDBTool {
    private static final String SQL1 = "INSERT INTO `meishi_info_japan`(" +
            "`shop_id`," +
            "`shop_name`," +
            "`shop_country`," +
            "`shop_city`," +
            "`shop_region`," +
            "`shop_address`," +
            "`shop_lng`," +
            "`shop_lat`," +
            "`shop_tel`," +
            "`tag_name`," +
            "`mean_price`," +
            "`shop_score`," +
            "`kouwei_score`," +
            "`huanjing_score`," +
            "`fuwu_score`," +
            "`shihui_score`," +
            "`shop_time`," +
            "`shop_feature`," +
            "`food_items`," +
            "`atmosphere`," +
            "`img_url`," +
            "`shop_url`," +
            "`photo_urls`," +
            "`description`," +
            "`datasource`," +
            "`lnglat_source`" +
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static String getDiffCount(String type1,String type2){
        String a1 = getDiffCount1(type1);
        String a2 = getDiffCount1(type2);
        if("中式".equals(a1)){
            if("中式".equals(a2)){
                return a1;
            }else {
                return a2;
            }
        }else {
            return a1;
        }
    }
    private static String getDiffCount1(String type){
        if("咖啡厅".equals(type)||"俄罗斯菜".equals(type)||"西餐".equals(type)){
            return "西式";
        }
        if("日本菜".equals(type)){
            return "日式";
        }
        if("韩国料理".equals(type)){
            return "韩式";
        }
        if("东南亚菜".equals(type)){
            return "东南亚式";
        }
        if("清真菜".equals(type)){
            return "清真";
        }
        return "中式";
    }

    public static Integer booleanToInteger(Boolean b){
        if(b== null){
            return 0;
        }
        if(b){
            return 1;
        }
        return 0;
    }

    private static final Gson GSON = new Gson();
    public static String toJson(Object obj){
        if(obj == null){
            return null;
        }
        return GSON.toJson(obj);
    }

    /**
     * emoji表情替换
     *
     * @param source 原字符串
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source) {
        if(StringUtils.isNotBlank(source)){
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
        }else{
            return source;
        }
    }
    public static String getInfo(List<String> p,int index){
        if(p.size() == 3){
            if(index == 2){
                return null;
            }
            if(index == 3){
                return p.get(2);
            }
        }
        return p.get(index);
    }
    public static String getTogether(String ... args){
        StringBuffer sb = new StringBuffer();
        for(String iter:args){
            if(iter!=null && !"".equals(iter) && !"null".equals(iter))
                sb.append(iter+",");
        }
        return sb.toString();
    }
    public static Float getShopScore(String str_score){
        String score[] = {"准一星商户","一星商户","准二星商户","二星商户","准三星商户","三星商户","准四星商户","四星商户","准五星商户","五星商户",};
        for(int i=0;i<score.length;i++){
            if(score[i].equals(str_score))
                return (i+1)*0.5f;
        }
        return 3f;
    }
    private static final String SQL2 = "INSERT INTO `meishi_comment_japan`(`shop_id`,`comment_time`,`comment_score`" +
            ",`comment_info`,`user_name`,`user_rank`,`comment_photos`) VALUES (?,?,?,?,?,?,?)";
    public static void main(String[] args) throws IOException, PropertyVetoException {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test?&useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false&useSSL=false");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        QueryRunner runner = new QueryRunner(dataSource);

        //"beihaidao","chongsheng", "daban", "dongjing", "fushishan", "jingdu", "mingguwu"
        //"北海道","冲绳","大阪","东京","富士山","京都","名古屋"
        String city_names[] = {"北海道","冲绳","大阪","东京","富士山","京都","名古屋"};
        String city_paths[] = {"beihaidao","chongsheng", "daban", "dongjing", "fushishan", "jingdu", "mingguwu"};
        String city_name = "大阪";
        String city_path = "";
//        String successFile = "D:/data/japan/split/meishi_success_task_ex.json_";
        long cur_time = System.currentTimeMillis();
//        for(int file_i=1;file_i<10;file_i++){
//            String tempFile = successFile+ file_i +".json";
        for(int file_i=0;file_i<city_names.length;file_i++){
            String successFile = "D:/data/japan/"+city_paths[file_i]+"/meishi_success_task_ex.json";
            String tempFile = successFile;
            System.out.println("解析文件:"+ tempFile);
            System.out.println("消耗时间："+(System.currentTimeMillis() - cur_time)/1000+"s");
            cur_time = System.currentTimeMillis();
        HashMap<String, ShopInfo> infos = FileUtils.readLines(new File(tempFile), Charset.forName("UTF-8")).stream()
                .filter(x -> !Strings.isNullOrEmpty(x))
                .filter(x -> !"null".equalsIgnoreCase(x))
                .map(x -> GsonUtils.GSON.fromJson(x, ShopInfo.class))
                .filter(Objects::nonNull)
                .filter(x -> !Strings.isNullOrEmpty(x.getShop_name()))
                .filter(x -> !Strings.isNullOrEmpty(x.getShop_url()))
                .map(x -> {
                    String[] temp = x.getShop_url().split("/");
                    HashMap<String, ShopInfo> ret = Maps.newHashMap();
                    ret.put(temp[temp.length - 1].trim(), x);
                    return ret;
                })
                .reduce(new HashMap<>(), (a, b) -> {
                    a.putAll(b);
                    return a;
                });

            int finalFile_i = file_i;
            Object[][] datas = infos.values().stream()
                .map(x -> {
                    String[] temp = x.getShop_url().split("/");
                    String id = temp[temp.length - 1].trim();
                    String comment_score = x.getComment_score();
                    String[] scores = new String[]{"0","0","0"};
                    if(!Strings.isNullOrEmpty(comment_score)){
                        String[] pp = comment_score.split("\\s+");
                        if(pp.length == 3){
                            scores[0] = pp[0].substring("口味".length());
                            scores[1] = pp[1].substring("环境".length());
                            scores[2] = pp[2].substring("服务".length());
                        }
                    }
                    String shop_country = "日本";
                    String shop_city = city_names[finalFile_i];
//                    String shop_city = x.getRegion_parent();
                    String shop_region = x.getRegion();
                    if(shop_city == null || "".equals(shop_city))
                    {
                        shop_city = shop_region;
                    }
                    String shop_maintype = x.getClassfy_parent();
                    String shop_subtype = x.getClassfy();
                    if(x.getShop_time()!= null && x.getShop_time().length()>255){
                        x.setShop_time(x.getShop_time().substring(0,255));
                    }
                    if(x.getClassfy_parent() == null){
                        shop_maintype = x.getClassfy();
                        shop_subtype = null;
                    }
                    return new Object[]{
                            Integer.valueOf(id),  //shop_id
                            x.getShop_name(),            //shop_name
                            shop_country,       //shop_country
                            shop_city, //`shop_city` varchar(255) DEFAULT NULL,
                            shop_region, //`shop_region` varchar(255) DEFAULT NULL,
                            MoreObjects.firstNonNull(x.getShop_address(),"").replace("地址：","").trim(), //`shop_address` text,
                            Double.parseDouble("".equals(x.getShop_lng())?"0":x.getShop_lng()), //`shop_lng` decimal(10,5) DEFAULT NULL,
                            Double.parseDouble("".equals(x.getShop_lat())?"0":x.getShop_lat()), //`shop_lat` decimal(10,5) DEFAULT NULL,
                            MoreObjects.firstNonNull(x.getShop_tel(),"").replace("电话：","").trim(), //`shop_tel` varchar(255) DEFAULT NULL,
                            getTogether(shop_maintype,shop_subtype,getDiffCount(shop_maintype,shop_subtype)), //`tag_name`,
                            MoreObjects.firstNonNull(x.getMean_price(),"").replace("人均","").replace("￥","").trim(), //`mean_price` varchar(255) DEFAULT NULL,
                            getShopScore(x.getShop_score()),    //shop_score
                            Float.parseFloat(scores[0]), //`kouwei_score` varchar(255) DEFAULT NULL,
                            Float.parseFloat(scores[1]), //`huanjing_score` varchar(255) DEFAULT NULL,
                            Float.parseFloat(scores[2]), //`fuwu_score` varchar(255) DEFAULT NULL,
                            Float.parseFloat("0"),       //shihui_score
                            MoreObjects.firstNonNull(x.getShop_time(),"").replace("修改","").trim(), //`shop_time` varchar(255) DEFAULT NULL,
                            getTogether(x.getHas_tuan()?"团购":"",x.getHas_waimai()?"外卖":"",x.getHas_ka()?"会员卡":"",x.getHas_freewifi()?"Wifi":"",x.getHas_freestop()?"停车场":"",x.getHas_onlinebooking()?"在线订购":""),    //shop_feature
                            toJson(x.getCai_titles()),   //food_items
                            "",    //atmosphere
                            x.getImg_url(),    //img_url
                            x.getShop_url(),    //shop_url
                            toJson(x.getPhoto_album()),    //photo_urls
                            x.getShop_summary(), //`description` text,
                            "大众点评",    //datasource
                            "谷歌地图",     //lnglat_source
                            x.getComment_list()
                    };
                }).toArray(Object[][]::new);
        for(Object[] data:datas){
//            System.out.println(data.length);
            if(data.length == 27){
                List<Comment> comments = (List<Comment>)data[26];
                Object[] pp = new Object[26];
                for(int i=0;i<pp.length;i++){
                    pp[i]=data[i];
                }
                try {
//                    System.out.println("更新数据库");
                    runner.update(SQL1,pp);
                    for(Comment comment:comments){
                        String user_rank = MoreObjects.firstNonNull(comment.getUser_rank(),"").toString().trim();
                        if(user_rank.endsWith("50")){
                            comment.setComment_rank("50");
                        }else if(user_rank.endsWith("40")){
                            comment.setComment_rank("40");
                        }else if(user_rank.endsWith("30")){
                            comment.setComment_rank("30");
                        }else if(user_rank.endsWith("20")){
                            comment.setComment_rank("20");
                        }else if(user_rank.endsWith("10")){
                            comment.setComment_rank("10");
                        }else if(user_rank.length() > "sml-rank-stars sml-str".length()){
                            comment.setComment_rank(user_rank.substring("sml-rank-stars sml-str".length()));
                        }
                        String comment_desc = MoreObjects.firstNonNull(comment.getComment_desc(),"").toString().trim();
                        if(comment_desc.endsWith("收起")){
                            comment_desc =comment_desc.split("收起")[0];
                        }
                        comment.setComment_desc(comment_desc);
                        String comment_time = MoreObjects.firstNonNull(comment.getComment_time(),"").toString().trim();
                        comment_time = comment_time.split("更新于")[0].trim();
                        if(comment_time.isEmpty()){
                            comment.setComment_time("2016");
                        }else if(comment_time.length() < 8){
                            comment.setComment_time("2016-" + comment_time);
                        }else{
                            comment.setComment_time("20"+comment_time);
                        }
                        runner.update(SQL2,pp[0],comment.getComment_time()
                                ,comment.getComment_rank()
                                ,filterEmoji(comment.getComment_desc())
//                                ,Integer.valueOf(MoreObjects.firstNonNull(comment.getUser_id(),"-1"))
                                ,comment.getUser_name()
                                ,comment.getUser_rank()
                                ,toJson(comment.getComment_photos()));
                    }
                } catch (SQLException e) {
//                    e.printStackTrace();
                }
            }
        }

        }

        /*
        Type DATA_TYPE = new TypeToken<Map<String, Object>>() {
        }.getType();
        List<MutablePair<String, Map<String, Object>>> success1 = FileUtils.readLines(new File(successFile), Charset.forName("UTF-8")).stream()
                .filter(x -> !Strings.isNullOrEmpty(x))
                .filter(x -> !"null".equalsIgnoreCase(x))
                .map(x -> GsonUtils.GSON.<Map<String, Object>>fromJson(x, DATA_TYPE))
                .map(x -> {
                    List<String> id = Stream.of(x.get("shop_url"))
                            .filter(Objects::nonNull)
                            .map(Object::toString)
                            .map(y -> y.split("/"))
                            .map(y -> y[y.length - 1])
                            .filter(y -> !Strings.isNullOrEmpty(y))
                            .collect(Collectors.toList());
                    if (id == null || id.isEmpty()) {
                        return null;
                    }
                    MutablePair<String, Map<String, Object>> entry = new MutablePair<String, Map<String, Object>>();
                    entry.setLeft(id.get(0));
                    entry.setRight(x);
                    return entry;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        HashMap<String, Map<String, Object>> data = success1.stream()
                .map(x -> {
                    HashMap<String, Map<String, Object>> ret = Maps.newHashMap();
                    ret.put(x.getLeft(), x.getRight());
                    return ret;
                })
                .reduce(new HashMap<String, Map<String, Object>>(), (a, b) -> {
                    a.putAll(b);
                    return a;
                });
        for (String key : data.keySet()) {
            Map<String, Object> temp = data.get(key);
            toObjects(key,temp);
        }
        */

    }

}
