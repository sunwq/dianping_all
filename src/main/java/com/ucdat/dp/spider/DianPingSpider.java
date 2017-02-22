package com.ucdat.dp.spider;

import com.google.common.collect.Sets;
import com.ucdat.dp.spider.core.GetShopInfoMultiThread;
import com.ucdat.dp.spider.core.GetShopInfoTool;
import com.ucdat.dp.spider.core.SearchTaskMultiThread;
import com.ucdat.dp.spider.core.SearchTaskTool;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 主类
 *
 * @author liyan
 * @version 1.0
 * @since JDK1.8
 */
public class DianPingSpider {
    //    public static String root_path = "/data/japan/dongjing/";
    public static String root_path = "/data/china/";
    //    public static String root_path = "D:/data/test/";
//    public static String root_path = "/data/beijing/";
//    public static String root_path = "D:/data/beijing/";
    public static String city_name = "chengdu";

    /**
     * 主函数
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            switch (args[0]) {
                case "runtask":
                    runMain(1);
                    break;
                case "searchfast":
                    runFast(2);
                    break;
                case "research":
                    runFast(3);
                    break;
                case "runall":
                    runAll();
                    break;
                case "task":
                    runTask(args[1],1);break;
                case "search":
                    runTask(args[1],2);break;
                case "re":
                    runTask(args[1],3);break;
                default:
                    break;
            }
        }
        //运行
//        runMain(1);
//        runMain(2);
        //测试
//        testUnit();
//        runFast(2);
    }

    public static void runTask(String city, Integer flag) throws IOException {
        String city_path = root_path + city + "/";
        File root_filepath = new File(city_path);
        if (!root_filepath.exists()) {
            root_filepath.mkdir();
        }
        if (flag == 1) {
            SearchTaskTool taskTool = new SearchTaskTool(city);
            Set<String> ss1 = Sets.newHashSet();
            taskTool.searchTask(city_path + "meishi_success_task.json", city_path + "meishi_error_task.json", ss1);
        } else if (flag == 2) {
            GetShopInfoMultiThread infoTool = new GetShopInfoMultiThread(5);
            Set<String> ss2 = Sets.newHashSet();
            infoTool.getShopInfo(city_path + "meishi_success_task.json", city_path + "meishi_success_task_ex.json", city_path + "meishi_err_task_ex.json", ss2);
        } else if (flag == 3) {
            GetShopInfoMultiThread infoTool = new GetShopInfoMultiThread(3);
            Set<String> ss2 = Sets.newHashSet();
            infoTool.getShopInfo(city_path + "meishi_err_task_ex.json", city_path + "meishi_success_task_ex1.json", city_path + "meishi_err_task_ex1.json", ss2);
        }
    }

    public static void runAll() throws IOException {
        String citys[] = {"chongsheng", "daban", "dongjing", "fushishan", "jingdu", "mingguwu"};
        for (String city : citys) {
            String city_path = root_path + city + "/";
            System.out.println(city_path);
            File root_filepath = new File(city_path);
            if (!root_filepath.exists()) {
                root_filepath.mkdir();
            }
            //SearchTask
            SearchTaskTool taskTool = new SearchTaskTool(city);
            Set<String> ss1 = Sets.newHashSet();
            taskTool.searchTask(city_path + "meishi_success_task.json", city_path + "meishi_error_task.json", ss1);
        }
    }

    public static void runFast(Integer flag) throws IOException {
        if (flag == 1) {
            SearchTaskMultiThread taskTool = new SearchTaskMultiThread(city_name, 3);
            Set<String> ss1 = Sets.newHashSet();
            taskTool.searchTask(root_path + "meishi_success_task.json", root_path + "meishi_error_task.json", ss1);
        } else if (flag == 2) {
            GetShopInfoMultiThread infoTool = new GetShopInfoMultiThread(5);
            Set<String> ss2 = Sets.newHashSet();
            infoTool.getShopInfo(root_path + "meishi_success_task.json", root_path + "meishi_success_task_ex.json", root_path + "meishi_err_task_ex.json", ss2);
        } else if (flag == 3) {
            GetShopInfoMultiThread infoTool = new GetShopInfoMultiThread(3);
            Set<String> ss2 = Sets.newHashSet();
            infoTool.getShopInfo(root_path + "meishi_err_task_ex.json", root_path + "meishi_success_task_ex1.json", root_path + "meishi_err_task_ex1.json", ss2);
        }
    }

    public static void runMain(Integer flag) throws IOException {
        if (flag == 1) {
            SearchTaskTool taskTool = new SearchTaskTool(city_name);
//            List<String> s1 = IOUtils.readLines(DianPingSpider.class.getClassLoader().getResourceAsStream("success1.txt"), Charset.forName("UTF-8"));
            Set<String> ss1 = Sets.newHashSet();
//          ss1.addAll(s1);
            taskTool.searchTask(root_path + "meishi_success_task.json", root_path + "meishi_error_task.json", ss1);
        } else if (flag == 2) {
            GetShopInfoTool infoTool = new GetShopInfoTool();
//            List<String> s2 = IOUtils.readLines(DianPingSpider.class.getClassLoader().getResourceAsStream("success2.txt"), Charset.forName("UTF-8"));
            Set<String> ss2 = Sets.newHashSet();
            //ss2.addAll(s2);
            infoTool.getShopInfo(root_path + "meishi_success_task.json", root_path + "meishi_success_task_ex.json", root_path + "meishi_err_task_ex.json", ss2);
        }
    }

    public static void testUnit() throws IOException {

    }
}
