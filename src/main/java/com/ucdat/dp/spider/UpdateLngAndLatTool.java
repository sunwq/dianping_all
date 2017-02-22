package com.ucdat.dp.spider;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ucdat.dp.spider.model.LngAndLatInfo;
import com.ucdat.dp.spider.utils.LatitudeTool;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.io.FileUtils;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by liyan on 16-12-10.
 */
public class UpdateLngAndLatTool {
    private static final Gson GSON = new Gson();

    public static void getLngAndLatInfo(QueryRunner runner,String fileName) throws SQLException {
        List<Map<String,Object>> datas = runner.query("SELECT shop_id,shop_address FROM meishi_info_ex WHERE shop_address IS NOT NULL",new MapListHandler());
        for(Map<String,Object> data:datas){
            try{
                Map<String,Object> d = Maps.newHashMap();
                int id = Integer.valueOf(data.get("shop_id").toString());
                String addr = data.get("shop_address").toString();
                Map<String, String> json = LatitudeTool.getGeocoderLatitude("北京"+addr);
                System.out.println(addr+"=>");
                System.out.println("lng : "+json.get("lng"));
                System.out.println("lat : "+json.get("lat"));
                d.put("id",id);
                d.put("lng",json.get("lng"));
                d.put("lat",json.get("lat"));
                FileUtils.writeStringToFile(new File(fileName),GSON.toJson(d)+"\n", Charset.forName("UTF-8"),true);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateLngAndLatInfo(QueryRunner runner,String fileName) throws SQLException, IOException {
        List<LngAndLatInfo> datas = FileUtils.readLines(new File(fileName),Charset.forName("UTF-8")).stream()
                .filter(x->!Strings.isNullOrEmpty(x))
                .map(x->GSON.fromJson(x,LngAndLatInfo.class))
                .collect(Collectors.toList());
        for(LngAndLatInfo data:datas){
            try {
                int id = data.getId();
                double lng = Double.valueOf(data.getLng());
                double lat = Double.valueOf(data.getLat());
                runner.update("UPDATE meishi_info_ex SET shop_lng=?,shop_lat=? WHERE shop_id=?", lng,lat,id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws PropertyVetoException, SQLException, IOException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/dianping?&useUnicode=true&autoReconnect=true&failOverReadOnly=false&useSSL=false");
        dataSource.setUser("root");
        dataSource.setPassword("LiYan1993");
        QueryRunner runner = new QueryRunner(dataSource);
        getLngAndLatInfo(runner,"/data/id_lng_lat.json");
        updateLngAndLatInfo(runner,"/data/id_lng_lat.json");
    }

}
